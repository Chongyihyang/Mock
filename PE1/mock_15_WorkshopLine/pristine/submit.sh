#!/usr/bin/env bash
set -euo pipefail
cd -- "$(dirname -- "${BASH_SOURCE[0]}")"
if [[ $# != 1 || ! "$1" =~ ^task[12]$ ]]; then
  echo 'Usage: bash submit.sh task1|task2' >&2
  exit 2
fi
task=$1
if [[ "$task" == task1 ]]; then
  java -cp test.jar Decrypt
else
  java -cp test.jar TestSupport task2
fi
snapshot=$(mktemp -d "./.${task}-snapshot.XXXXXX")
trap 'rm -rf -- "$snapshot"' EXIT
shopt -s nullglob
sources=(./*.java)
cp -- "${sources[@]}" "$snapshot/"
backup=''
if [[ -e "$task" ]]; then
  backup=$(mktemp -d "./${task}.previous.XXXXXX")
  rmdir -- "$backup"
  mv -- "$task" "$backup"
fi
if ! mv -- "$snapshot" "$task"; then
  if [[ -n "$backup" ]]; then mv -- "$backup" "$task"; fi
  exit 1
fi
trap - EXIT
echo "Submission saved in $task/"
if [[ -n "$backup" ]]; then echo "Previous snapshot retained in $backup/"; fi
