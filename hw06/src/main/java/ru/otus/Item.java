package ru.otus;

import java.math.BigDecimal;

public final class Item {

    private final Long id;
    private final String title;
    private final BigDecimal price;

    private Item(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.price = builder.price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static final class Builder {

        private Long id;
        private String title;
        private BigDecimal price;

        public Builder(){}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Item build() {
            return new Item(this);
        }

    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
