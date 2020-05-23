package ru.otus.atmdept;

public class ATMDeptMemento {

    private final ATMDept state;

    ATMDeptMemento(ATMDept state) {
        this.state = new ATMDept(state);
    }

    ATMDept getState() {
        return state;
    }
}
