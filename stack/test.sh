#!/bin/sh
# Run from any directory; only compile the tests needed for the selected part.
set -eu
cd "$(dirname "$0")"
if [ "$#" -gt 1 ]; then
  echo 'Usage: sh test.sh [1|2|3|all]' >&2
  exit 2
fi
case "${1:-all}" in
  1|2|3) groups="$1" ;;
  all) groups='1 2 3' ;;
  *) echo 'Usage: sh test.sh [1|2|3|all]' >&2; exit 2 ;;
esac
for part in $groups; do
  build="out/part$part"
  mkdir -p "$build"
  javac -Xlint:rawtypes -Xlint:unchecked -Werror -d "$build" \
    Stack.java ArrayStack.java TestSupport.java "Test$part.java"
  java -cp "$build" "Test$part"
done
