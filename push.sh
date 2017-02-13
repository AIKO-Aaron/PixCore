#!/bin/bash

cd "$(DIRNAME "$0")"
echo "Message to commit: "
read -r msg
git add *
git commit -a -m "$msg"
git push repo