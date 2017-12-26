#!/bin/sh

init="java -cp artifacts/HelloUDPTest.jar:lib/hamcrest-core-1.3.jar:lib/junit-4.11.jar:lib/quickcheck-0.6.jar:out/production/Java2015/ info.kgeorgiy.java.advanced.hello.Tester client ru.ifmo.ctddev.markovnikov.helloudp.HelloUDPClient"
init=$init" "$1
$init
