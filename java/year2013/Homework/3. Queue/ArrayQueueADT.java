public class ArrayQueueADT {
    //inv: size >= 0
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private Object[] elements = new Object[10];

    // post: size == size' + 1 && elements[tail'] == o
    public static void enqueue (ArrayQueueADT stack, Object o)
    {
        ensureCapacity(stack, stack.tail + 1);
	stack.elements[stack.tail++] = o;
	stack.size++;
    }

    // post: elements.length == elements.length' * 2 && head = 0 && tail = head + size
    private static void ensureCapacity(ArrayQueueADT stack, int capacity)
    {
        if (stack.tail >= stack.elements.length) {
	    Object[] e = new Object[2 * capacity];
            for (int i = stack.head; i < stack.tail; i++) {
		e[i - stack.head] = stack.elements[i];
            }
	    stack.elements = e;
	    stack.head = 0;
	    stack.tail = stack.size;
	}
    }
        //post: result == size
        public static int size(ArrayQueueADT stack) {
	    return stack.size;
	}
	
        //post: result == size == 0
	public static boolean isEmpty(ArrayQueueADT stack) {
            return stack.size == 0;
	}
        
        // pre: size > 0
	// post: size == size' - 1 && result == elements[head'] && elements[head'] == null
        public static Object dequeue(ArrayQueueADT stack) {
             assert stack.size > 0;
             Object result = stack.elements[stack.head];
             if (stack.head > stack.elements.length) {
	         stack.head = 0;
	     }
	     stack.elements[stack.head]  = null;
	     stack.head++;
	     stack.size--;
	     return result; 
        }

        // pre: size > 0
	// post: result == elements[head]
        public static Object peek(ArrayQueueADT stack) {
	    assert stack.size > 0;
	    return stack.elements[stack.head];
	}
    
}