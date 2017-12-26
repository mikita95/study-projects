@links = ();
while (($line = <STDIN>)) {
    $line =~ s/^\s*//;
    $line =~ s/\s*$//;
    while ($line =~ /<a ([^>]*)href="(?#link starts here->)((ftp|https?):\/\/)?(?#path to answer->)(\w+.*?)(?#useless->)(:|\/|")([^>]*)>/g) {
        #print $1 . " " . $2 . " " . $3 . " " . $4 . "\n";
        my %h = map { $_ => 1 } @links;
        unless(exists($h{$4 . "\n"})) {
            push(@links, $4 . "\n");
        }
    }
}
@links = sort(@links);
print @links;
