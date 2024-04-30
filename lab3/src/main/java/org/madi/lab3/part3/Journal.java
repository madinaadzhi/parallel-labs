package org.madi.lab3.part3;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Journal {
    public HashMap<String, Group> groups = new HashMap<>();
    public Journal() {
        this.groups.put(new Group("ІP-13", 10).getName(), new Group("ІP-13", 25));
        this.groups.put(new Group("ІP-14", 15).getName(), new Group("ІP-14", 19));
        this.groups.put(new Group("ІP-15", 20).getName(), new Group("ІP-15", 20));
    }

    public void addMark(String group, Integer student, String mark) {
        synchronized (this.groups.get(group).list.get(student)) {
            this.groups.get(group).list.get(student).add(mark);
        }
    }

    public void print() {
        List<String> groups = this.groups.keySet().stream().sorted().collect(Collectors.toList());
        for (int j = 0; j < groups.size(); j++) {
            String group = groups.get(j);
            System.out.printf("Group %6s\n", group);
            List<Integer> students = this.groups.get(group).list.keySet().stream().sorted().collect(Collectors.toList());
            for (int k = 0; k < students.size(); k++) {
                Integer student = students.get(k);
                System.out.printf("Student %3s", student);
                List<String> marks = this.groups.get(group).list.get(student);
                for (int i = 0; i < marks.size(); i++) {
                    String mark = marks.get(i);
                    System.out.printf("%25s", mark);
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
