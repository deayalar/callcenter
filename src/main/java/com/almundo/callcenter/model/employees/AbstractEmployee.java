package com.almundo.callcenter.model.employees;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEmployee implements Employee{

    protected static final Logger logger = LoggerFactory.getLogger(Employee.class);

    protected int id;
    protected boolean busy = false;

    public int getId() {
        return id;
    }

    public boolean isBusy() {
        return busy;
    }

    @Override
    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public String toString() {
        return getId() + " " + isBusy();
    }
}