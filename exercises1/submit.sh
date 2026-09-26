#!/bin/bash

set -o nounset
set -o pipefail

# -----------------------------
# UI helpers
# -----------------------------
hr() { echo "----------------------------------------------------------------------------------"; }
banner() { hr; echo "$1"; hr; }
ok() { echo "✅ $1"; }
warn() { echo "⚠️  $1"; }
err() { echo "❌ $1"; }

confirm_yes_no() {
  local prompt="$1"
  read -p "$prompt (y/n)? " -n 1 -r
  echo
  [[ $REPLY =~ ^[Yy]$ ]]
}

# -----------------------------
# Argument + task validation
# -----------------------------
if [ $# -ne 1 ]; then
  echo "Usage: submit.sh task#"
  echo "# is the task number (e.g., submit.sh task1)"
  exit 1
fi

TASK="$1"
case "$TASK" in
  task1|task2) ;;
  *) err "Unknown task: $TASK"; exit 1 ;;
esac

# -----------------------------
# Submission folder utilities
# -----------------------------
# Returns:
#   0 = packaging allowed (dir created/fresh)
#   1 = packaging skipped (student chose not to overwrite existing folder)
prepare_task_dir_maybe() {
  local dir="$1"

  if [ -d "$dir" ]; then
    if confirm_yes_no "This will delete previous submission for '$dir'. Overwrite"; then
      rm -rf "$dir"
      mkdir -p "$dir"
      return 0
    else
      warn "Kept existing '$dir' folder. Skipping packaging (no files copied)."
      return 1
    fi
  fi

  mkdir -p "$dir"
  return 0
}

copy_java_files() {
  local dir="$1"
  shopt -s nullglob
  local java_files=( *.java )
  if [ ${#java_files[@]} -eq 0 ]; then
    warn "No .java files found to submit (folder created anyway)."
    return 0
  fi
  cp "${java_files[@]}" "$dir/"
}

# -----------------------------
# Build utilities
# -----------------------------
ensure_test_jar() {
  [[ -f test.jar ]] || { err "test.jar not found."; return 1; }
  return 0
}

# Attempt compilation, but NEVER blocks submission (only warns).
compile_all_warn_only() {
  banner "BUILD: compiling (won't block submission)"

  rm -f *.class || true

  shopt -s nullglob
  local java_files=( *.java )
  if [ ${#java_files[@]} -eq 0 ]; then
    warn "No .java files to compile."
    return 1
  fi

  set +o errexit
  javac *.java
  local status=$?
  set -o errexit

  if [[ "$status" -ne 0 ]]; then
    warn "Compilation FAILED (you may still submit)."
    return 1
  fi

  ok "Compilation succeeded."
  return 0
}

# -----------------------------
# Check runners (non-blocking)
# -----------------------------
# Returns:
#   0 = ran and passed
#   1 = ran but failed / couldn't execute / jar missing
run_java_check() {
  local label="$1"      # e.g. Test1
  local test_class="$2" # e.g. Test1

  if ! ensure_test_jar; then
    err "$label skipped (missing test.jar)."
    return 1
  fi

  echo "Running $label..."
  set +o errexit
  local out
  out=$(java -cp test.jar:. "$test_class" 2>&1)
  local status=$?
  set -o errexit

  echo "$out"

  if [[ "$status" -ne 0 ]]; then
    err "$label could not be executed (java exited $status)."
    return 1
  fi

  if echo "$out" | grep -qi "failed"; then
    err "$label reported failures."
    return 1
  fi

  ok "$label passed."
  return 0
}

# Returns:
#   0 = script succeeded
#   1 = script failed
run_script_check() {
  local label="$1"
  shift

  echo "Running $label..."
  set +o errexit
  "$@"
  local status=$?
  set -o errexit

  if [[ "$status" -ne 0 ]]; then
    err "$label failed (exit $status)."
    return 1
  fi

  ok "$label succeeded."
  return 0
}

# Interactive Decrypt: only run when we decide to (does not capture output)
run_decrypt_interactive() {
  banner "Decrypt: task2.md"
  if ! ensure_test_jar; then
    err "Cannot run Decrypt (missing test.jar)."
    return 1
  fi

  java -cp test.jar:. Decrypt
  local status=$?

  hr
  if [[ "$status" -ne 0 ]]; then
    err "Decrypt exited with status $status."
    return 1
  fi

  ok "Decrypt finished. task2.md created."
  return 0
}

# -----------------------------
# Main flow
# -----------------------------
banner "SUBMIT: $TASK"

# 1) Compile attempt (warn only)
compile_all_warn_only || true

# 2) Run checks, but allow submission even if they fail (with confirmation)
banner "CHECKS: running required checks (can still submit if they fail)"

ALL_PASSED=1  # 1 = true, 0 = false

if [[ "$TASK" == "task1" ]]; then
  run_java_check "Test1" "Test1" || ALL_PASSED=0
  run_java_check "Test2" "Test2" || ALL_PASSED=0
  run_java_check "Test3" "Test3" || ALL_PASSED=0
  run_script_check "bash test.sh task1" bash test.sh task1 || ALL_PASSED=0
elif [[ "$TASK" == "task2" ]]; then
  run_script_check "bash test.sh task2" bash test.sh task2 || ALL_PASSED=0
fi

if [[ "$ALL_PASSED" -eq 1 ]]; then
  ok "All checks passed."
else
  warn "Some checks FAILED."
  if ! confirm_yes_no "Checks failed. Continue and submit anyway"; then
    echo "Submission cancelled."
    # Even if cancelled, if task1 and all passed? not the case here.
    exit 1
  fi
  warn "Continuing submission despite failed checks."
fi

# 3) Package submission (optional overwrite)
banner "PACKAGING: submission folder"
PACKAGED=0
if prepare_task_dir_maybe "$TASK"; then
  copy_java_files "$TASK"
  ok "Submission folder '$TASK' packaged."
  PACKAGED=1
else
  warn "Packaging skipped by student choice."
fi

# 4) Decrypt rule:
# - Only for task1
# - Only if ALL checks passed
# - Should run even if packaging was skipped (your earlier requirement)
if [[ "$TASK" == "task1" && "$ALL_PASSED" -eq 1 ]]; then
  run_decrypt_interactive || true
else
  if [[ "$TASK" == "task1" ]]; then
    warn "Skipping Decrypt because not all task1 checks passed."
  fi
fi

banner "DONE"
if [[ "$PACKAGED" -eq 1 ]]; then
  ok "Submission for '$TASK' completed (packaged)."
else
  ok "Run completed. (Packaging was skipped.)"
fi