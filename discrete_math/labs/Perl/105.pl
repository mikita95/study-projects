while (<>) {
    printf if /(x|y|z)(.){5,17}(x|y|z)/;
}
