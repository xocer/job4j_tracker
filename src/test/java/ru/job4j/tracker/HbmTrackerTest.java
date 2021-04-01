package ru.job4j.tracker;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HbmTrackerTest {

    @Test
    public void whenAdd() {
        HbmTracker hbmTracker = new HbmTracker();
        Item item = new Item("Learn Hibernate",
                "test",
                new Timestamp(1459510232000L));
        hbmTracker.add(item);
        List<Item> list = hbmTracker.findAll();
        assertEquals(1, list.size());
        assertEquals(item, list.get(0));
    }

    @Test
    public void whenReplace() {
        HbmTracker hbmTracker = new HbmTracker();
        Item item = new Item("Learn Hibernate",
                "test",
                new Timestamp(1459510232000L));

        Item newItem = new Item("test update",
                "white test",
                new Timestamp(1459510232000L));
        newItem.setId(1);

        hbmTracker.add(item);

        item.setName("test update");
        item.setDescription("white test");
        hbmTracker.replace("1", item);

        List<Item> list = hbmTracker.findAll();
        assertEquals(1, list.size());
        assertEquals(newItem, list.get(0));
    }

    @Test
    public void whenDelete() {
        HbmTracker hbmTracker = new HbmTracker();
        Item item1 = new Item("Learn Hibernate",
                "test1",
                new Timestamp(1459510232000L));
        Item item2 = new Item("Learn java",
                "test2",
                new Timestamp(1459510232000L));
        Item item3 = new Item("Learn Spring",
                "test3",
                new Timestamp(1459510232000L));
        Item item4 = new Item("Learn Sql",
                "test4",
                new Timestamp(1459510232000L));

        hbmTracker.add(item1);
        hbmTracker.add(item2);
        hbmTracker.add(item3);
        hbmTracker.add(item4);

        hbmTracker.delete("1");
        hbmTracker.delete("4");

        List<Item> list = hbmTracker.findAll();

        assertEquals(2, list.size());
        assertEquals(item2, list.get(0));
        assertEquals(item3, list.get(1));
    }

    @Test
    public void whenFindAll() {
        HbmTracker hbmTracker = new HbmTracker();
        Item item1 = new Item("Learn Hibernate",
                "test1",
                new Timestamp(1459510232000L));
        Item item2 = new Item("Learn java",
                "test2",
                new Timestamp(1459510232000L));
        Item item3 = new Item("Learn Spring",
                "test3",
                new Timestamp(1459510232000L));
        Item item4 = new Item("Learn Sql",
                "test4",
                new Timestamp(1459510232000L));

        hbmTracker.add(item1);
        hbmTracker.add(item2);
        hbmTracker.add(item3);
        hbmTracker.add(item4);

        assertEquals(4, hbmTracker.findAll().size());
    }

    @Test
    public void whenFindByName() {
        HbmTracker hbmTracker = new HbmTracker();
        Item item1 = new Item("Learn Hibernate",
                "test1",
                new Timestamp(1459510232000L));
        Item item2 = new Item("Learn java",
                "test2",
                new Timestamp(1459510232000L));

        hbmTracker.add(item1);
        hbmTracker.add(item2);

        List<Item> result = hbmTracker.findByName("Learn java");

        assertEquals(item2, result.get(0));
        assertNotEquals(item1, result.get(0));
    }

    @Test
    public void whenFindById() {
        HbmTracker hbmTracker = new HbmTracker();
        Item item1 = new Item("Learn Hibernate",
                "test1",
                new Timestamp(1459510232000L));
        Item item2 = new Item("Learn java",
                "test2",
                new Timestamp(1459510232000L));

        hbmTracker.add(item1);
        hbmTracker.add(item2);

        final Item byId = hbmTracker.findById(1);
        assertEquals(item1, byId);
    }
}