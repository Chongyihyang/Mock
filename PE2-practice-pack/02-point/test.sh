#!/bin/sh
set -eu
cd "$(dirname "$0")"
if [ "$#" -eq 0 ]; then
  for n in 1; do sh "$0" "$n"; done
else
  case "$1" in 1) ;; *) echo "Invalid test number" >&2; exit 2;; esac
  find . -name '*.class' -delete
  javac -Xlint:unchecked -Xlint:rawtypes "Test$1.java"
  java -Duser.language=en -Duser.country=SG "Test$1"
fi
