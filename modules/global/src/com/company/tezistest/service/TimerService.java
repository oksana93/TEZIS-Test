package com.company.tezistest.service;

import java.text.ParseException;
import java.util.UUID;

public interface TimerService {
    String NAME = "tezistest_TimerService";

    public void updateTimerByCardId(UUID cardId) throws ParseException;

//    public void updateTimerByAssignment(Assignment assignment);
}
