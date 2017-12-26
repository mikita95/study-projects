public class MyMin {
    public static int min(ArrayQueue q) {
        assert q.size() > 0;
        int min = (Integer)q.dequeue();
        q.enqueue(min);
        int a;
        for (int i = 0; i < q.size(); i++)
        {
            a = (Integer)q.dequeue();
            q.enqueue(a);
            if (min > a)
                min = a;
        }
        return min;
    }

    public static void main(String[] args) {
   
        ArrayQueue q = new ArrayQueue();
	for (int i = 0; i < args.length; i++) {
	    String[] s = args[i].split("\\s+");
	    for (int j = 0; j < s.length; j++) {
	        try {
	            q.enqueue(Integer.parseInt(s[j]));
	        }
                catch (NumberFormatException e) {
                }
	    }
        }
        
        System.out.println(min(q));
        System.out.println(min(q));
    }
}