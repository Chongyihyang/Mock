#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
if [ "$#" -ne 1 ]; then
  echo 'Usage: bash test.sh task1|task2' >&2
  exit 2
fi
case "$1" in
  task1) groups=(Test1 Test2 Test3) ;;
  task2) groups=(Test1 Test2 Test3 Test4 Test5 Test6) ;;
  *) echo 'Usage: bash test.sh task1|task2' >&2; exit 2 ;;
esac
if [ ! -f test.jar ]; then
  echo 'Missing test.jar: extract the complete package.' >&2
  exit 2
fi
# Compiling sources into a fresh directory avoids stale class files.
build=$(mktemp -d)
trap 'rm -rf -- "$build"' EXIT
javac -Xlint:unchecked -Xlint:rawtypes -d "$build" ./*.java
for group in "${groups[@]}"; do
  java -cp "test.jar:$build" "$group"
done
