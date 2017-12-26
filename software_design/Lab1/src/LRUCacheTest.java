import org.junit.Assert;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by nikita on 11.09.16.
 */
public class LRUCacheTest {
    private Random random = new Random();
    private final int MAX_CAPACITY = 1000;
    private Map<Integer, String> goodLRUCache = new LinkedHashMap<Integer, String>(MAX_CAPACITY, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
            return size() > MAX_CAPACITY;
        }
    };

    @org.junit.Test
    public void testGet() throws Exception {
        LRUCache<Integer, String> lruCache = new LRUCache<>(MAX_CAPACITY);
        goodLRUCache.clear();
        lruCache.get(1);
        for (int i = 0; i < MAX_CAPACITY * 2; i++) {
            lruCache.set(i, "value " + i);
            goodLRUCache.put(i, "value " + i);
        }
        for (int i = 0; i < MAX_CAPACITY * 2; i++) {
            int key = random.nextInt(MAX_CAPACITY * 2);
            String a = lruCache.get(key);
            String b = goodLRUCache.get(key);
            Assert.assertEquals(a, b);
        }
    }

    @org.junit.Test
    public void testSize() throws Exception {
        LRUCache<Integer, String> lruCache = new LRUCache<>(MAX_CAPACITY);
        goodLRUCache.clear();
        for (int i = 0; i < MAX_CAPACITY * 2; i++) {
            lruCache.set(i, "value " + i);
            goodLRUCache.put(i, "value " + i);
            Assert.assertEquals(lruCache.size(), goodLRUCache.size());
        }
    }

    @org.junit.Test
    public void testSet() throws Exception {
        LRUCache<Integer, String> lruCache = new LRUCache<>(MAX_CAPACITY);
        goodLRUCache.clear();
        for (int k = 0; k < MAX_CAPACITY; k++) {
            for (int i = 0; i < MAX_CAPACITY * 2; i++) {
                lruCache.set(i, "value " + i);
                goodLRUCache.put(i, "value " + i);
                int key = random.nextInt(MAX_CAPACITY * 2);
                String a = lruCache.get(key);
                String b = goodLRUCache.get(key);
                Assert.assertEquals(a, b);
            }
        }
    }

    @org.junit.Test
    public void testRemove() throws Exception {
        LRUCache<Integer, String> lruCache = new LRUCache<>(MAX_CAPACITY);
        goodLRUCache.clear();
        for (int i = 0; i < MAX_CAPACITY * 2; i++) {
            lruCache.set(i, "value " + i);
            goodLRUCache.put(i, "value " + i);
            int key = random.nextInt(MAX_CAPACITY * 2);
            String a = lruCache.get(key);
            String b = goodLRUCache.get(key);
            Assert.assertEquals(a, b);
        }
        for (int i = 0; i < MAX_CAPACITY * 2; i++) {
            int key = random.nextInt(MAX_CAPACITY * 2);
            lruCache.remove(key);
            goodLRUCache.remove(key);
            key = random.nextInt(MAX_CAPACITY * 2);
            String a = lruCache.get(key);
            String b = goodLRUCache.get(key);
            Assert.assertEquals(a, b);
        }


    }
}