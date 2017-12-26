public class ArrayQueueSingleton {
    //inv: size >= 0
    private static int head = 0;
    private static int tail = 0;
    private static int size = 0;
    private static Object[] elements = new Object[10];

    // post: size == size' + 1 && elements[tail'] == o
    public static void enqueue (Object o)
    {
        ensureCapacity(tail + 1);
	elements[tail] = o;
        tail++;
	size++;
    }

    // post: elements.length == elements.length' * 2 && head = 0; tail = head + size
    private static void ensureCapacity(int capacity)
    {
        if (tail >= elements.length) {
	    Object[] e = new Object[2 * capacity];
            
            for (int i = head; i < tail; i++) {
	        e[i - head] = elements[i];
            }
	    elements = e;
	    head = 0;
	    tail = size;
	}
    }

        //post: result == size
        public static int size() {
	    return size;
	}
	
        //post: result == size == 0
	public static boolean isEmpty() {
            return size == 0;
	}
        
        // pre: size > 0
	// post: size == size' - 1  && result == elements[head'] && elements[head'] == null
        public static Object dequeue() {
             assert size > 0;
             Object result = elements[head];
	     elements[head]  = null;
	     head++;
	     size--;
	     return result; 
        }

        // pre: size > 0
	// peek: result == elements[head]
        public static Object peek() {
	    assert size > 0;
	    return elements[head];
	}
    
}