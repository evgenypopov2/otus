package ru.otus;

import ru.otus.atmdept.ATMDept;

public class ATMDeptRunner {

    public static void main(String[] args) {

        ATMDept atmDept = new ATMDept();
        atmDept.randomLoadATMs();

        System.out.println("============== Original state ===============");
        atmDept.getATMStates().forEach(System.out::println);
        System.out.println(atmDept.getRemainedSum());

        atmDept.saveState();

        System.out.println("============== Start work ===============");
        atmDept.randomWork(500);

        System.out.println("============== After some work ===============");
        atmDept.getATMStates().forEach(System.out::println);
        System.out.println(atmDept.getRemainedSum());

        atmDept = atmDept.restoreState();

        System.out.println("============== After restore state ===============");
        atmDept.getATMStates().forEach(System.out::println);
        System.out.println(atmDept.getRemainedSum());
    }

}
