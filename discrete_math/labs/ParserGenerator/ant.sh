#!/bin/sh
java -Xmx500M -cp "/usr/local/lib/antlr-4.5.3-complete.jar:$CLASSPATH" org.antlr.v4.Tool -visitor src/main/antlr/Grammar.g4
