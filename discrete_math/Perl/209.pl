while (<>) {
    s/\(.+?\)/\(\)/g;
    print;
}