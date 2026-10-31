#!/bin/sh
set -eu
cd "$(dirname "$0")"
javac -Xlint:unchecked -Xlint:rawtypes Test2.java
work=$(mktemp -d)
trap 'rm -rf "$work"' EXIT HUP INT TERM
for src in type-tests/*.java.txt; do
  cp "$src" "$work/Invalid.java"
  if javac -cp . -d "$work" "$work/Invalid.java" >"$work/log" 2>&1; then
    echo "FAIL: $src unexpectedly compiled" >&2
    exit 1
  fi
  echo "PASS: $src rejected"
done
