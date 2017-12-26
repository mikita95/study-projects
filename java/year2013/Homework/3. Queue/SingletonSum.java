public class SingletonSum {
    public static void main(String[] args) {
        ArrayQueueSingleton queue = new ArrayQueueSingleton();
        int sum = 0;
	for (int i = 0; i < args.length; i++) {
	    String[] s = args[i].split("\\s+");
	    for (int j = 0; j < s.length; j++) {
	        try {
	            queue.enqueue(Integer.parseInt(s[j]));
	        }
                catch (NumberFormatException e) {
                }
	    }
        }
        while (!queue.isEmpty())
        {
            sum += (Integer)queue.dequeue();
        }
        System.out.println(sum);
    }
}