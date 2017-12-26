#!/bin/bash
export CLASSPATH=out/production/Java2015/
rmiregistry &
java -cp $CLASSPATH ru.ifmo.ctddev.markovnikov.bank.Server
