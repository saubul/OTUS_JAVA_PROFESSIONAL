package ru.otus;

import java.util.Set;

public class ItemServiceProxy implements IService<Item> {

    private final IService<Item> itemService;

    public ItemServiceProxy(IService<Item> itemService) {
        this.itemService = itemService;
    }

    @Override
    public void insert(int count) {
        printTransactionBegin();
        itemService.insert(count);
        printTransactionEnd();
    }

    @Override
    public void updateAll() {
        printTransactionBegin();
        itemService.updateAll();
        printTransactionEnd();
    }

    @Override
    public void clear() {
        itemService.clear();
    }

    @Override
    public Set<Item> readAll() {
        return itemService.readAll();
    }

    private void printTransactionBegin() {
        System.out.println("Transaction begin: ");
    }

    private void printTransactionEnd() {
        System.out.println("Transaction end\n");
    }

}
