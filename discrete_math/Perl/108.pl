while (<>) {
    printf if /\([^)(]*\w[^)(]*\)/;
}