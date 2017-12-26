while (<>) {
    s/(\w)\g1/$+/g;;
    print;
}
