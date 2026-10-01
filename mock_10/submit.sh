#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
if [ "$#" -ne 1 ]; then
  echo 'Usage: bash submit.sh task1|task2' >&2
  exit 2
fi
case "$1" in
  task1|task2) task="$1" ;;
  *) echo 'Usage: bash submit.sh task1|task2' >&2; exit 2 ;;
esac
# A failed check is reported but does not prevent saving your current work.
if bash test.sh "$task"; then
  echo "Checks passed for $task."
else
  echo "Checks did not pass for $task; saving the current attempt anyway."
fi
snapshot=$(mktemp -d ".snapshot-$task.XXXXXX")
trap 'if [ -d "$snapshot" ]; then rm -rf -- "$snapshot"; fi' EXIT
cp -- ./*.java "$snapshot/"
if [ -e "$task" ]; then
  backup="$task.previous.$(date +%Y%m%d-%H%M%S).$$"
  mv -- "$task" "$backup"
  echo "Previous snapshot preserved as $backup."
fi
mv -- "$snapshot" "$task"
echo "Saved Java sources in $task/. This is a local snapshot, not a GitHub upload."
