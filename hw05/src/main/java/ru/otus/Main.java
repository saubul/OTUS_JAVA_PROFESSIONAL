package ru.otus;

import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        Box box = new Box();
        Iterator<String> smallFirstIterator = box.getSmallFirstIterator();
        while (smallFirstIterator.hasNext()) {
            System.out.print(smallFirstIterator.next() + " ");
        }
        System.out.println();
        Iterator<String> colorFirstIterator = box.getColorFirstIterator();
        while (colorFirstIterator.hasNext()) {
            System.out.print(colorFirstIterator.next() + " ");
        }

    }

}
