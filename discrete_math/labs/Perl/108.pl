while (<>) {
    printf if /\([^)(]*\w[^)(]*\)/;
}
