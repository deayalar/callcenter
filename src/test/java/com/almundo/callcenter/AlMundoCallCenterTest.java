package com.almundo.callcenter;

import com.almundo.callcenter.model.requests.Call;
import com.almundo.callcenter.model.requests.Dispatchable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class AlMundoCallCenterTest {

    private static final int CONCURRENT_CALLS = 10;
    Map<String, String> map = new ConcurrentHashMap();
    AlMundoCallCenter alMundoCallCenter;

    @Before
    public void setup() {
        int numberOfOperators = 5;
        int numberOfSupervisors= 3;
        int numberOfDirectors = 2;
        alMundoCallCenter = new AlMundoCallCenter(numberOfOperators, numberOfSupervisors, numberOfDirectors);
        System.out.println(alMundoCallCenter.getStaff());
        System.out.println("NUMBER OF CALLS ->" + CONCURRENT_CALLS);
    }

    @Test
    public void populateStaffMap() {
        System.out.println(alMundoCallCenter.getStaff());
    }

    @Test
    public void performConcurrentCalls() {
        try {
            List<Dispatchable> calls = new ArrayList<>();
            for(int i = 0; i < CONCURRENT_CALLS; i++) {
                Dispatchable call = new Call(i);
                calls.add(call);
            }
            alMundoCallCenter.getDispatcher().receiveCalls(calls, alMundoCallCenter.getStaff());
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}