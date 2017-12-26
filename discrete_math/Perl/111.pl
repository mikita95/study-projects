while (<>) {
    printf if /^(1(01*0)*1|0)*$/;
}
