package com.almundo.callcenter.dispatcher.impl;

import com.almundo.callcenter.Constants;
import com.almundo.callcenter.ResponsabilityChain;
import com.almundo.callcenter.model.employees.Employee;
import com.almundo.callcenter.model.requests.Dispatchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Dispatcher {

    protected static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);

    private static Dispatcher instance;
    private static ResponsabilityChain chain;
    private ExecutorService executorService;

    private Dispatcher(){}

    public static Dispatcher getInstance() {
        if(instance == null) {
            instance = new Dispatcher();
            chain = new ResponsabilityChain();
        }
        return instance;
    }

    private void dispatchCall(Dispatchable dispatchable, ConcurrentMap<String, ConcurrentMap<Integer, Employee>> staff) {
        //get free employee
        Employee freeEmployee = getFreeEmployee(staff, ResponsabilityChain.FIRST_ROLE_IN_CHAIN);
        //assign call
        dispatchable.assign(freeEmployee);
        //process call
        freeEmployee.attend(dispatchable);
    }

    synchronized private Employee getFreeEmployee(ConcurrentMap<String, ConcurrentMap<Integer, Employee>> staff, String role) {
        ConcurrentMap<Integer, Employee> employeesByRole = staff.get(role);
        Map.Entry<Integer, Employee> entry = employeesByRole.entrySet().stream()
                .filter(e -> !e.getValue().isBusy())
                .findFirst()
                .orElse(null);
        if(entry != null) {
            return entry.getValue();
        }else{
            String nextRoleInChain = chain.getNextRoleInChain(role);
            logger.info(MessageFormat.format("There are not free {0}(s)", role));
            return getFreeEmployee(staff, nextRoleInChain);
        }
    }

    public void receiveCalls(List<Dispatchable> calls, ConcurrentMap<String, ConcurrentMap<Integer, Employee>> staff) throws InterruptedException {
        executorService = Executors.newFixedThreadPool(Constants.DEFAULT_CAPACITY);
        for(Dispatchable call: calls) {
            executorService.execute(() -> dispatchCall(call, staff));
        }
        executorService.shutdown();
        executorService.awaitTermination(30, TimeUnit.SECONDS);
    }

    public void setCapacity(int capacity) {
        if(executorService == null) {
            executorService = Executors.newFixedThreadPool(capacity);
        }else {
            throw new IllegalStateException("Dispatcher capacity already set");
        }
    }
}