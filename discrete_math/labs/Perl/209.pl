while (<>) {
    s/\(.+?\)/\(\)/g;
    print;
}
