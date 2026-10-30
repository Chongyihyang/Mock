#!/bin/bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
if [ "$#" -ne 0 ]; then
  echo 'This command takes no arguments.' >&2
  exit 2
fi
java support/Session.java status
