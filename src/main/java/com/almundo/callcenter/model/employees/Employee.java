package com.almundo.callcenter.model.employees;

import com.almundo.callcenter.model.requests.Dispatchable;

public interface Employee {

    boolean isBusy();
    void setBusy(boolean busy);
    int getId();
    void attend(Dispatchable dispatchable);

}
