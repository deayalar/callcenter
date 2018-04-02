package com.almundo.callcenter.model.requests;

import com.almundo.callcenter.model.employees.Employee;

public interface Dispatchable {

     int getDuration();

     Employee getEmployeeOnCharge();

     void assign(Employee employeeOnCharge);

     int getId();

     boolean isAttended();

     void setAttended(boolean attended);
}
