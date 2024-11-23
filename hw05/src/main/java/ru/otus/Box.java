package ru.otus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Box {

    private final Map<Color, Matryoshka> matryoshkaMap;

    public Box() {
        matryoshkaMap = Map.of(
                Color.RED, new Matryoshka(Color.RED),
                Color.GREEN, new Matryoshka(Color.GREEN),
                Color.BLUE, new Matryoshka(Color.BLUE),
                Color.MAGENTA, new Matryoshka(Color.MAGENTA)
        );
    }


    public Iterator<String> getSmallFirstIterator() {
        return new Iterator<>() {
            int cursor = 0;

            Color color = Color.values()[0];

            @Override
            public boolean hasNext() {
                return cursor != 10;
            }

            @Override
            public String next() {
                String result = matryoshkaMap.get(color).getItem(cursor);
                if (color.ordinal() == Color.values().length - 1) {
                    color = Color.values()[0];
                    cursor++;
                } else {
                    color = Color.values()[color.ordinal() + 1];
                }
                return result;
            }
        };
    }

    public Iterator<String> getColorFirstIterator() {
        return new Iterator<>() {
            int cursor = 0;

            Color color = Color.values()[0];

            @Override
            public boolean hasNext() {
                return !(cursor == 10 && color.ordinal() == Color.values().length - 1);
            }

            @Override
            public String next() {
                String result = matryoshkaMap.get(color).getItem(cursor);
                cursor++;
                if (cursor == 10 && color.ordinal() != Color.values().length - 1) {
                    cursor = 0;
                    color = Color.values()[color.ordinal() + 1];
                }
                return result;
            }
        };
    }

}
