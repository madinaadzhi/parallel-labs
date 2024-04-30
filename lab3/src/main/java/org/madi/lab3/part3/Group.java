package org.madi.lab3.part3;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Group {
    private final String name;
    public HashMap<Integer, List<String>> list = new HashMap<>();

    public Group(String name, int size) {
        this.name = name;
        for (int i = 0; i < size; i++) {
            this.list.put(i + 1, new ArrayList<>());
        }
    }

}
