public class ADTSum {
    public static void main(String[] args) {
        ArrayQueueADT queue = new ArrayQueueADT();
        int sum = 0;
	for (int i = 0; i < args.length; i++) {
	    String[] s = args[i].split("\\s+");
	    for (int j = 0; j < s.length; j++) {
	        try {
	            ArrayQueueADT.enqueue(queue, Integer.parseInt(s[j]));
	        }
                catch (NumberFormatException e) {
                }
	    }
        }
        while (!ArrayQueueADT.isEmpty(queue))
        {
            sum += (Integer)ArrayQueueADT.dequeue(queue);
        }
        System.out.println(sum);
    }
}