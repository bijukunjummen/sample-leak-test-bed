#!/bin/bash

sleep 5  # wait for java process to start

echo "The app home dir [$HOME]"
JAVA_HOME=$HOME/.java-buildpack/open_jdk_jre
LOOP_WAIT=30  # how long to wait in seconds

# pick the pid of our Java process
echo "Searching for the Java process..."
ps axf
PID=$(ps axf | grep "java" | grep -v grep | awk '{print $1}')
echo "Monitor script running, looking for pid [$PID]"

# take baseline
$JAVA_HOME/bin/jcmd "$PID" VM.native_memory baseline

# loop forever and take diffs
while [ 1 == 1 ]; do
    $JAVA_HOME/bin/jcmd "$PID" VM.native_memory summary.diff
    top -b -n 1
    sleep "$LOOP_WAIT"
done