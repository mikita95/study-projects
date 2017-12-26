public class ArrayQueue {
    // inv: size >= 0
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private Object[] elements = new Object[200000];

    // post: size == size' + 1 && elements[head'] == element
    public void enqueue (Object o)
    {
        ensureCapacity(tail + 1);
	elements[tail++] = o;
	size++;
    }

    // post: elements.length == elements.length' * 2 && head = 0 && tail = head + size
    private void ensureCapacity(int capacity)
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

        // post: result == size
        public int size() {
	    return size;
	}
	
        // post: result == size == 0
	public boolean isEmpty() {
            return size == 0;
	}
        
        // pre: size > 0
	// post: size == size' - 1 && result == elements[head'] && elements[head'] == null
        public Object dequeue() {
             assert size > 0;
             Object result = elements[head];
             if (head > elements.length) {
	         head = 0;
	     }
	     elements[head]  = null;
	     head++;
	     size--;
	     return result; 
        }

        // pre: size > 0
	// post: result == elements[head]
        public Object peek() {
	    assert size > 0;
	    return elements[head];
	}
    
}