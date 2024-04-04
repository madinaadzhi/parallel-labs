package org.madi.lab1.part3;

import lombok.Getter;

@Getter
public class Counter {
    private int cnt = 0;
    public void inc() {
        cnt++;
    }

    public void dec() {
        cnt--;
    }

    public synchronized void incSyncMethod() {
        cnt++;
    }

    public synchronized void decSyncMethod() {
        cnt--;
    }

    public void incSyncBlock() {
        synchronized (this) {
            cnt++;
        }
    }

    public void decSyncBlock() {
        synchronized (this) {
            cnt--;
        }
    }
}
