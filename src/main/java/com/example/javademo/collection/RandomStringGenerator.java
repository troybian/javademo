package com.example.javademo.collection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RandomStringGenerator<T> implements Iterable<T> {

    private final List<T> list;

    public RandomStringGenerator(List<T> list) {
        this.list = list;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public T next() {
                return list.get((int) (list.size() * Math.random()));
            }
        };
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("Array", "xx");
        RandomStringGenerator<String> rList = new RandomStringGenerator<>(list);


    }
}
