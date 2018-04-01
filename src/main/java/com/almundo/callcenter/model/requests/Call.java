package com.almundo.callcenter.model.requests;

import com.almundo.callcenter.model.employees.Employee;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Call implements Dispatchable{

    private final static int MINIMUM_CALL_DURATION = 5;
    private final static int MAXIMUM_CALL_DURATION = 10;

    private int duration;
    private Employee employeeOnCharge;
    private int id;

    public Call(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public Employee getEmployeeOnCharge() {
        return employeeOnCharge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void assign(Employee employeeOnCharge) {
        employeeOnCharge.setBusy(true);
        this.duration = ThreadLocalRandom.current().nextInt(MINIMUM_CALL_DURATION, MAXIMUM_CALL_DURATION + 1);
        this.employeeOnCharge = employeeOnCharge;
    }
}