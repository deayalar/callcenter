package com.almundo.callcenter.model.employees;

import com.almundo.callcenter.model.requests.Dispatchable;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

public class Supervisor extends AbstractEmployee {

    public Supervisor(int id) {
        this.id = id;
    }

    public void attend(Dispatchable dispatchable) {
        try {
            this.busy = true;
            logger.info(MessageFormat.format("Supervisor {0} attending call {1} with duration {2} - {3}", this.id, dispatchable.getId(), dispatchable.getDuration(),  Thread.currentThread().getName()));
            TimeUnit.SECONDS.sleep(dispatchable.getDuration());
        }catch (InterruptedException e) {
            throw new IllegalStateException("Interrupted Call", e);
        }finally {
            this.busy = false;
            dispatchable.setAttended(true);
            logger.info(MessageFormat.format("Supervisor {0} is free", this.id));
        }
    }
}
