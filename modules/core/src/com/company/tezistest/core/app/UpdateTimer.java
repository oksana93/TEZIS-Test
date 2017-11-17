package com.company.tezistest.core.app;

public interface UpdateTimer {

    public final String NAME = "tezistest_UpdateTimer";

    public void cancelProcByCard(String procCode, Integer processDueDate, String timeUnitForProcessDueDate, String moduleName);
}
