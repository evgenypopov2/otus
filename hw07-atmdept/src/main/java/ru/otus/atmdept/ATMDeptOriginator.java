package ru.otus.atmdept;

import java.util.ArrayDeque;
import java.util.Deque;

public class ATMDeptOriginator {
    private final Deque<ATMDeptMemento> stack = new ArrayDeque<>();

    public void saveState(ATMDept state) {
        stack.push(new ATMDeptMemento(state));
    }

    public ATMDept restoreState() {
        return stack.pop().getState();
    }
}
