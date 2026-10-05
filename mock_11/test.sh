#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
if [ "$#" -ne 1 ]; then
  echo 'Usage: bash test.sh task1|task2' >&2; exit 2
fi
case "$1" in
  task1) groups=(Test1 Test2 Test3 Test4) ;;
  task2)
    if [ ! -f .task2-unlocked ]; then
      echo 'Task 2 is locked. Run bash unlock.sh at minute 50.' >&2; exit 2
    fi
    groups=(Test1 Test2 Test3 Test4 Test5 Test6 Test7) ;;
  *) echo 'Usage: bash test.sh task1|task2' >&2; exit 2 ;;
esac
if [ ! -f test.jar ]; then
  echo 'Missing test.jar: extract the complete package.' >&2; exit 2
fi
build=$(mktemp -d)
trap 'rm -rf -- "$build"' EXIT
javac -Xlint:unchecked -Xlint:rawtypes -d "$build" ./*.java
for group in "${groups[@]}"; do
  java -cp "test.jar:$build" "$group"
done
