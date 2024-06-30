#!/bin/bash

set -e

BASE_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Use our .githooks directory instead of default directory .git/hooks
git config core.hooksPath .githooks

# Make pre-commit executable
chmod +x $BASE_DIR/pre-commit