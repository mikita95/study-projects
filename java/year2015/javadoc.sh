#!/bin/bash

rm -Rf doc
mkdir doc

src_files=/tmp/src_files.txt

find src -type f -name '*.java' > "$src_files"

javadoc -linkoffline http://java.sun.com/javase/7/docs/api/ http://java.sun.com/javase/7/docs/api/ -private -d doc "@$src_files"

rm -f "$src_files" 
