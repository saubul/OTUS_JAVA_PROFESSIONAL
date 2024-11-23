package ru.otus;

public class Main {

    public static void main(String[] args) {

        IService<Item> itemService = new ItemService();
        itemService.insert(3);
        itemService.updateAll();

        itemService.clear();
        System.out.println();

        ItemServiceProxy itemServiceProxy = new ItemServiceProxy(itemService);
        itemServiceProxy.insert(3);
        itemServiceProxy.updateAll();
    }

}
