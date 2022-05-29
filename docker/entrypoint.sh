#!/bin/sh
set -e

CMD="java -Xmx128m -Xmx128m -jar app.jar"
echo starting app with cmd:

echo $CMD

$CMD