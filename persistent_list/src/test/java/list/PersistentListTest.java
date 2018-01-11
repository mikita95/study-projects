package list;

import list.fatcopy.FatCopyPersistentList;
import list.slow.SlowPersistentList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class PersistentListTest {
    private PersistentListInterface<Integer> persistentList;

    public PersistentListTest(PersistentListInterface<Integer> persistentList){
        this.persistentList = persistentList;
    }

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {new FatCopyPersistentList<>()},
                {new SlowPersistentList<>()}};

        return Arrays.asList(data);
    }

    @Before
    public void beforeTest() {
        persistentList.clear();
    }

    @Test
    public void add() {
        Random random = new Random();
        List<Integer> validationList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(1000);
            persistentList.add(i, value);
            validationList.add(value);
        }
        for (int i = 0; i < persistentList.getListVersion(); i++) {
            Assert.assertArrayEquals(
                    validationList.subList(0, i + 1).toArray(),
                    getListByIterator(persistentList.getListHead(i)).toArray());
        }
    }

    @Test
    public void replace() {
        Random random = new Random();
        List<Integer> validationList1 = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(1000);
            persistentList.add(i, value);
            validationList1.add(value);
        }
        List<Integer> validationList2 = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(1000);
            persistentList.replace(i, value);
            validationList2.add(value);
        }
        for (int i = persistentList.getListVersion() / 2; i < persistentList.getListVersion(); i++) {
            Assert.assertArrayEquals(
                    Stream.concat(
                            validationList2.subList(0, 1 + i - persistentList.getListVersion() / 2).stream(),
                            validationList1.subList(1 + i - persistentList.getListVersion() / 2, validationList1.size()).stream()
                    )
                            .toArray(),
                    getListByIterator(persistentList.getListHead(i)).toArray());
        }
    }

    private List<Integer> getListByIterator(PersistentListIterator<Integer> head) {
        List<Integer> result = new ArrayList<>();

        if (head != null) {
            result.add(head.getValue());
            while (head.hasNext()) {
                head.next();
                result.add(head.getValue());
            }
        }
        return result;
    }
}