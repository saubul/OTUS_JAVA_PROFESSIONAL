package ru.otus;

public class ItemDataSource implements IDataSource<Item> {

    private static volatile ItemDataSource INSTANCE;
    private final Connection<Item> connection;
    private ItemDataSource(Connection<Item> connection) {
        this.connection = connection;
    }

    public static ItemDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (ItemDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ItemDataSource(new Connection<>(new ItemDataBase()));
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Connection<Item> getConnection() {
        return this.connection;
    }

}
