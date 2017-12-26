#!/bin/sh

init="java -cp artifacts/WebCrawlerTest.jar:lib/hamcrest-core-1.3.jar:lib/junit-4.11.jar:lib/quickcheck-0.6.jar:out/production/Java2015/ info.kgeorgiy.java.advanced.crawler.Tester easy ru.ifmo.ctddev.markovnikov.webcrawler.WebCrawler"
init=$init" "$1
$init
