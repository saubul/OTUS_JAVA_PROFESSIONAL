package ru.otus;

import java.util.HashSet;
import java.util.Set;

public class ItemDataBase implements LocalDataBase<Item> {

    private Set<Item> itemSet;

    public ItemDataBase() {
        this.itemSet = new HashSet<>();
    }

    @Override
    public Set<Item> getSet() {
        return this.itemSet;
    }
}
