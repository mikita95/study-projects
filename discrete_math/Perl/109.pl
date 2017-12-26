while (<>) {
    printf if /^([^\s](.)*[^\s]|[^\s]?)$/;
}
