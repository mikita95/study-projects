while (<>) {
    printf if /\b(\w+)\g1\b/
}
