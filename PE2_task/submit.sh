#!/bin/bash

set -o nounset
set -o errexit
set -o pipefail

if [ $# -ne 1 ]; then
  echo "Usage: submit.sh q1|q2|q3"
  exit 1
fi

TASK="$1"

# Only allow known tasks
case "$TASK" in
  q1|q2|q3) ;;
  *)
    echo "❗️ Unknown task: $TASK"
    exit 1
    ;;
esac

confirm_or_cancel() {
  local prompt="$1"
  read -p "$prompt (y/n)? " -n 1 -r
  echo
  if [[ ! $REPLY =~ ^[Yy]$ ]]; then
    echo "Submission cancelled."
    exit 0
  fi
}

prepare_task_dir() {
  local dir="$1"
  if [ -d "$dir" ]; then
    confirm_or_cancel "This will delete previous submission for '$dir'. Are you sure"
    rm -rf "$dir"
  fi
  mkdir -p "$dir"
}

copy_java_files() {
  local dir="$1"
  local found=0

  while IFS= read -r -d '' file; do
    found=1
    local rel="${file#./}"
    local subdir
    subdir="$(dirname "$rel")"
    mkdir -p "$dir/$subdir"
    cp "$file" "$dir/$subdir/"
  done < <(find . -maxdepth 1 -type f -name "*.java" -print0)

  if [ "$found" -eq 0 ]; then
    echo "⚠️  No matching */*.java files found to submit."
    return 0
  fi
}

compile_all() {
  echo "Removing all class files.."
  find . -type f -name "*.class" -delete

  echo "Compiling all java files.."
  mapfile -d '' java_files < <(find . -maxdepth 2 -type f -name "*.java" ! -path "./pristine/*" ! -path "./q1/*" ! -path "./q2/*" ! -path "./q3/*" -print0)

  if [ "${#java_files[@]}" -eq 0 ]; then
    echo "⚠️  No matching */*.java files found to compile."
    return 0
  fi

  set +o errexit
  javac "${java_files[@]}"
  local status=$?
  set -o errexit

  if [[ "$status" -ne 0 ]]; then
    confirm_or_cancel "❗️ Your code cannot be compiled. Please fix the compilation error before submitting. Do you want to continue your submission"
  else
    echo "✅ Compilation succeeded."
  fi
}

run_tests() {
  local task_label="$1"
  local test_class="$2"

  [[ -f test.jar ]] || { echo "❌ test.jar not found."; exit 1; }

  echo "Running $task_label tests.."
  set +o errexit
  TEST_OUT=$(java -cp test.jar:. "$test_class" 2>&1)
  TEST_STATUS=$?
  set -o errexit

  echo "$TEST_OUT"

  if [[ "$TEST_STATUS" -ne 0 ]]; then
    echo "❌ $task_label tests could not be executed ($test_class failed to run)."
    exit 1
  fi

  if echo "$TEST_OUT" | grep -qi "failed"; then
    read -p "❗️ $task_label tests failed. Do you want to continue submission anyway (y/n)? " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
      echo "Submission cancelled."
      exit 1
    else
      echo "⚠️ Continuing submission despite failed tests."
    fi
  else
    echo "✅ All $task_label tests passed."
  fi
}

echo "Submitting $TASK.."

# 1) Compile + tests first
compile_all

case "$TASK" in
  q1) run_tests "q1" "Test1" ;;
  q2) run_tests "q2" "Test2" ;;
  q3) run_tests "q3" "Test3" ;;
esac

# 2) Prepare submission folder and copy files
prepare_task_dir "$TASK"
copy_java_files "$TASK"

echo "✅ Submission for '$TASK' successfully completed."