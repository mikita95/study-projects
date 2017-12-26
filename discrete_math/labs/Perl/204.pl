while (<>) {
    s/(\w+)(\W*)(\w+)/$+$2$1/;
    print;
}
