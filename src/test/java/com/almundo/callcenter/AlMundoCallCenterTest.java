package com.almundo.callcenter;

import com.almundo.callcenter.model.requests.Call;
import com.almundo.callcenter.model.requests.Dispatchable;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlMundoCallCenterTest {

    private static final Logger logger = LoggerFactory.getLogger(AlMundoCallCenterTest.class);
    private AlMundoCallCenter alMundoCallCenter;

    private int numberOfOperators;
    private int numberOfSupervisors;
    private int numberOfDirectors;

    @Before
    public void setup() {
        numberOfOperators = 5;
        numberOfSupervisors= 3;
        numberOfDirectors = 2;
    }

    @Test
    public void populateStaffMap() {
        logger.info("POPULATE STAFF MAP -------------------------");
        alMundoCallCenter = new AlMundoCallCenter(numberOfOperators, numberOfSupervisors, numberOfDirectors);
        logger.info("Staff -> " + alMundoCallCenter.getStaff());
    }

    @Test
    public void performConcurrentCalls() {
        logger.info("PERFORM 10 CONCURRENT CALLS -------------------------");
        Integer numberOfCalls = 10;
        List<Dispatchable> calls = createCalls(numberOfCalls);
        assertNoBusyEmployees(alMundoCallCenter);
        assertAllCallsAttended(calls, numberOfCalls);
    }

    @Test
    public void receiveMoreCallsThanCapacity() {
        logger.info("PERFORM MORE CALLS THAN CAPACITY -------------------------");
        Integer numberOfCalls = 15;
        List<Dispatchable> calls = createCalls(numberOfCalls);
        assertNoBusyEmployees(alMundoCallCenter);
        assertAllCallsAttended(calls, numberOfCalls);
    }

    @Test
    public void onlyAssignOperators() {
        logger.info("PERFORM CALLS ONLY FOR OPERATORS -------------------------");
        Integer numberOfCalls = numberOfOperators;
        List<Dispatchable> calls = createCalls(numberOfCalls);
        assertNoBusyEmployees(alMundoCallCenter);
        assertAllCallsAttended(calls, numberOfCalls);
    }

    @Test
    public void onlyAssignOperatorsAndOneNextLevel() {
        logger.info("PERFORM CALLS ONLY FOR OPERATORS AND ONE FOR NEXT LEVEL-------------------------");
        Integer numberOfCalls = numberOfOperators + 1;
        List<Dispatchable> calls = createCalls(numberOfCalls);
        assertNoBusyEmployees(alMundoCallCenter);
        assertAllCallsAttended(calls, numberOfCalls);
    }

    @Test
    public void onlyAssignOperatorsAndAllNextLevel() {
        logger.info("PERFORM CALLS ONLY FOR OPERATORS AND ALL FOR NEXT LEVEL-------------------------");
        Integer numberOfCalls = numberOfOperators + numberOfSupervisors;
        List<Dispatchable> calls = createCalls(numberOfCalls);
        assertNoBusyEmployees(alMundoCallCenter);
        assertAllCallsAttended(calls, numberOfCalls);
    }

    private List<Dispatchable> createCalls(Integer numberOfCalls) {
        List<Dispatchable> calls = null;
        try {
            calls = new ArrayList<>();
            logger.info(MessageFormat.format("Number of calls -> {0}", numberOfCalls));
            alMundoCallCenter = new AlMundoCallCenter(numberOfOperators, numberOfSupervisors, numberOfDirectors);
            for(int i = 0; i < numberOfCalls; i++) {
                Dispatchable call = new Call(i);
                calls.add(call);
            }
            alMundoCallCenter.getDispatcher().receiveCalls(calls, alMundoCallCenter.getStaff());
        }catch(InterruptedException e) {
            logger.error("Error performing concurrent calls " + e);
        }finally {
            return calls;
        }

    }

    private void assertAllCallsAttended(List<Dispatchable> calls, Integer numberOfCalls) {
        long countAttendedCalls = calls.stream()
                .filter(c -> c.isAttended())
                .count();
        assertEquals(countAttendedCalls, numberOfCalls.longValue());
    }

    private void assertNoBusyEmployees(AlMundoCallCenter alMundoCallCenter) {
        long countBusyEmployees = alMundoCallCenter.getStaff().entrySet().stream()
                .filter(e -> (e.getValue().entrySet().stream()
                        .anyMatch(t -> t.getValue().isBusy()) == true))
                .count();
        assertEquals(countBusyEmployees, 0L);
    }
}