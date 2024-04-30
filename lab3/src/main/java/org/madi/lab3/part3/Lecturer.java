package org.madi.lab3.part3;

import java.util.List;

public class Lecturer implements Runnable {
    private final String name;
    private final List<String> groups;
    private final Journal journal;
    private final int week;

    public Lecturer(String name, List<String> groups, int week, Journal journal) {
        this.name = name;
        this.groups = groups;
        this.journal = journal;
        this.week = week;
    }

    @Override
    public void run() {
        for (int i = 0; i < week; i++) {
            for (String groupName : groups) {
                for (Integer studentName : this.journal.groups.get(groupName).list.keySet()) {
                    Double mark = (double) (Math.round(100 * Math.random() * 100)) / 100;
                    journal.addMark(groupName, studentName, mark + " (" + this.name + ")");
                }
            }
        }
    }
}
