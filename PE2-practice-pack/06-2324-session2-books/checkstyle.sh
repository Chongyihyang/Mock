#!/bin/sh
set -eu
cd "$(dirname "$0")"
if [ -f checkstyle.jar ] && [ -f cs2030_checks.xml ]; then
  java -jar checkstyle.jar -c cs2030_checks.xml "$@"
else
  echo 'Official Checkstyle jar/config were not attached. Add your course copies; this is not a style check.' >&2
  exit 2
fi
