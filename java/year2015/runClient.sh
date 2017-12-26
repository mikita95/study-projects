#!/bin/bash
export CLASSPATH=out/production/Java2015/
init='java -cp out/production/Java2015/ ru.ifmo.ctddev.markovnikov.bank.Client'
init=$init" "$1
init=$init" "$2
init=$init" "$3
init=$init" "$4
init=$init" "$5
$init
