package com.almundo.callcenter;

import com.almundo.callcenter.dispatcher.impl.Dispatcher;
import com.almundo.callcenter.model.employees.Director;
import com.almundo.callcenter.model.employees.Employee;
import com.almundo.callcenter.model.employees.Operator;
import com.almundo.callcenter.model.employees.Supervisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AlMundoCallCenter {

    //Staff
    private ConcurrentMap<String, ConcurrentMap<Integer, Employee>> staff;

    //Dispatcher
    private Dispatcher dispatcher;

    public AlMundoCallCenter(int numberOfOperators,
                             int numberOfSupervisors,
                             int numberOfDirectors) {
        populateStaff(numberOfOperators, numberOfSupervisors, numberOfDirectors);
        dispatcher = Dispatcher.getInstance();
    }

    private void populateStaff(int numberOfOperators,
                               int numberOfSupervisors,
                               int numberOfDirectors) {
        staff = new ConcurrentHashMap<>();
        staff.putIfAbsent(Constants.OPERATOR_KEY, getRoleStaffMap(numberOfOperators, Constants.OPERATOR_KEY));
        staff.putIfAbsent(Constants.SUPERVISOR_KEY, getRoleStaffMap(numberOfSupervisors, Constants.SUPERVISOR_KEY));
        staff.putIfAbsent(Constants.DIRECTOR_KEY, getRoleStaffMap(numberOfDirectors, Constants.DIRECTOR_KEY));
    }

    private ConcurrentMap<Integer, Employee> getRoleStaffMap(int numberOfEmployees, String role) {
        ConcurrentMap<Integer, Employee> employees = new ConcurrentHashMap<>();
        for(int i = 1; i <= numberOfEmployees; i++) {
            Employee employee = null;
            switch(role) {
                case Constants.OPERATOR_KEY:
                    employee = new Operator(i);
                    break;
                case Constants.SUPERVISOR_KEY:
                    employee = new Supervisor(i);
                    break;
                case Constants.DIRECTOR_KEY:
                    employee = new Director(i);
                    break;
            }
            employees.putIfAbsent(i, employee);
        }
        return employees;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ConcurrentMap<String, ConcurrentMap<Integer, Employee>> getStaff() {
        return staff;
    }
}