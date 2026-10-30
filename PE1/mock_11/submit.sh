#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
if [ "$#" -ne 1 ]; then
  echo 'Usage: bash submit.sh task1|task2' >&2; exit 2
fi
case "$1" in
  task1) task=task1 ;;
  task2)
    task=task2
    if [ ! -f .task2-unlocked ]; then
      echo 'Task 2 is locked. Run bash unlock.sh at minute 50.' >&2; exit 2
    fi ;;
  *) echo 'Usage: bash submit.sh task1|task2' >&2; exit 2 ;;
esac
if bash test.sh "$task"; then
  echo "Checks passed for $task."
else
  echo "Checks did not pass for $task; saving the current attempt anyway."
fi
snapshot=$(mktemp -d ".snapshot-$task.XXXXXX")
trap 'if [ -d "$snapshot" ]; then rm -rf -- "$snapshot"; fi' EXIT
cp -- ./*.java "$snapshot/"
if [ -e "$task" ]; then
  backup=$(mktemp -d "$task.previous.XXXXXXXX")
  mv -- "$task" "$backup/sources"
  echo "Previous snapshot preserved in $backup/sources."
fi
mv -- "$snapshot" "$task"
echo "Saved Java sources in $task/. This is a local snapshot, not a GitHub upload."
