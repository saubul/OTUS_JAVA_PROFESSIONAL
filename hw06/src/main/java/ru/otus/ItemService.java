package ru.otus;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ItemService implements IService<Item> {

    private final ItemDao itemDao;

    public ItemService() {
        this.itemDao = new ItemDao();
    }

    @Override
    public void insert(int count) {
        for (int i = 1; i < count + 1; i++) {
            itemDao.create(
                    new Item.Builder()
                            .id((long) i)
                            .price(BigDecimal.valueOf(i * 2L - 1))
                            .title("Item" + i)
                            .build()
            );
        }
        System.out.println(this.readAll());
    }

    @Override
    public void updateAll() {
        Set<Item> itemSet = new HashSet<>(itemDao.read());
        for (Item item : itemSet) {
            itemDao.update(item,
                    new Item.Builder()
                            .id(item.getId())
                            .title(item.getTitle())
                            .price(item.getPrice().multiply(BigDecimal.valueOf(2)))
                            .build());
        }
        System.out.println(this.readAll());
    }

    @Override
    public Set<Item> readAll() {
        return itemDao.read();
    }

    @Override
    public void clear() {
        itemDao.clear();
    }

}
