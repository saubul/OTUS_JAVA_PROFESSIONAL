package ru.otus;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class Matryoshka {

    // [0] - the smallest / [9] - the biggest
    private final List<String> items;

    public Matryoshka(Color color) {
        List<String> values = new ArrayList<>();
        String colorName = color.name();
        for (int i = 0; i < 10; i++) {
            values.add(colorName + i);
        }
        this.items = values;
    }

    public String getItem(int i) {
        return items.get(i);
    }
}
