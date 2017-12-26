my $s = "";
my $flag = 0;
while (($line = <STDIN>)) {
    $line =~ s/^(\s)*//;
    $line =~ s/(\s)*$//;
    $line =~ s/ +/ /g;
    $line =~ s/<.*?>//g;
    if ($line ne "") {
        $s = $s . $line . "\n";
        $flag = 1;
    }
    if ($line eq "" && $flag == 1) {
        $s = $s . "\n";
        $flag = 2;
    }
}
$s =~ s/(\s)*$//;
print $s;
