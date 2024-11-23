package ru.otus;

import java.util.Set;

public class ItemDao {

    private final ItemDataSource itemDataSource;

    public ItemDao() {
        this.itemDataSource = ItemDataSource.getInstance();
    }

    public Item create(Item item) {
        return itemDataSource.getConnection().create(item);
    }

    public Set<Item> read() {
        return itemDataSource.getConnection().read();
    }

    public Item update(Item oldItem, Item newItem) {
        return itemDataSource.getConnection().update(oldItem, newItem);
    }

    public Item delete(Item item) {
        return itemDataSource.getConnection().delete(item);
    }

    public void clear() {
        itemDataSource.getConnection().clear();
    }

}
