#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
if [ "$#" -ne 0 ]; then
  echo 'This command takes no arguments.' >&2
  exit 2
fi
javac -version >/dev/null 2>&1 || { echo "A JDK with javac is required." >&2; exit 2; }
java support/Session.java start
