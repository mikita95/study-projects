public class Sum {
    public static void main(String[] args) {
	int sum = 0;
	for (int i = 0; i < args.length; i++) {
	    String[] s = args[i].split("\\s+");
	    for (int j = 0; j < s.length; j++) {
		try {
			sum += Integer.parseInt(s[j]);
		    }
		catch (NumberFormatException e) {
		}
            }
	}
	System.out.println(sum);
    }
}