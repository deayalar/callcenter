package com.almundo.callcenter.model.employees;

import com.almundo.callcenter.model.requests.Dispatchable;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class Director extends AbstractEmployee {

    public Director(int id) {
        this.id = id;
    }

    public void attend(Dispatchable dispatchable) {
        try {
            logger.info(MessageFormat.format("Director {0} attending call {1} with duration {2} - {3}", this.id, dispatchable.getId(), dispatchable.getDuration(),  Thread.currentThread().getName()));
            TimeUnit.SECONDS.sleep(dispatchable.getDuration());
        }catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted Call", e);
        }finally {
            this.busy = false;
            logger.info(MessageFormat.format("Director {0} is free", this.id));
        }
    }
}
