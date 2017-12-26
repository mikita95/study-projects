#!/bin/bash
export CLASSPATH=out/production/Java2015/

rmic -d $CLASSPATH ru.ifmo.ctddev.markovnikov.bank.RemotePerson ru.ifmo.ctddev.markovnikov.bank.BankImpl
