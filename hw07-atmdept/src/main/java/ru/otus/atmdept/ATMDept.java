package ru.otus.atmdept;

import ru.otus.atm.ATM;
import ru.otus.banknote.MoneyPack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.atm.ATM.CELLS_CAPACITY;

public class ATMDept {

    public static final int ATM_DEFAULT_COUNT = 10;
    public static final int ATM_STANDARD_LOAD = CELLS_CAPACITY * 3 / 4;

    private List<ATM> atmList = new ArrayList<>();
    private final ATMDeptOriginator originator = new ATMDeptOriginator();

    public ATMDept() {
        this(ATM_DEFAULT_COUNT);
    }

    public ATMDept(int atmNumber) {
        for (int i = 0; i < atmNumber; i++) {
            ATM atm = new ATM(i + 1);
            atm.setListener(this::atmMaintenanceListener);
            atmList.add(atm);
        }
    }

    public ATMDept(ATMDept original) { // for memento
        atmList = original.atmList.stream().map(ATM::new).collect(Collectors.toList());
    }

    public void loadATMs() {
        atmList.forEach(atm -> atm.load(ATM_STANDARD_LOAD));
    }

    public void randomLoadATMs() {
        atmList.forEach(atm -> atm.load(MoneyPack.makeRandomInt(ATM_STANDARD_LOAD)));
    }

    public int getRemainedSum() {
        return atmList.stream().map(ATM::getRemainedSum).reduce(Integer::sum).orElse(0);
    }

    public List<String> getATMStates() {
        return atmList.stream().map(ATM::getCellsState).collect(Collectors.toList());
    }

    public void saveState() {
        originator.saveState(this);
    }

    public ATMDept restoreState() {
        return originator.restoreState();
    }

    public void randomWork(int workCycleNumber) {
        for (int i = 0; i < workCycleNumber; i++) {
            atmList.forEach(ATM::randomWorkEvent);
        }
    }

    private void atmMaintenanceListener(ATM atm) {
        System.out.println("ATM " + atm.getAtmNumber() + " maintenance reload");
        atm.load(ATM_STANDARD_LOAD); // recharge ATM
    }
}
