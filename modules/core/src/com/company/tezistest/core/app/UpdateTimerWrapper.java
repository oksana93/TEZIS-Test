package com.company.tezistest.core.app;

import javax.inject.Inject;

@SuppressWarnings("unused")
public class UpdateTimerWrapper {

    @Inject
    protected UpdateTimer updateTimer;

    protected String procCode;
    protected Integer processDueDate;
    protected String timeUnitForProcessDueDate;
    protected String moduleName;

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcessDueDate(Integer processDueDate) {
        this.processDueDate = processDueDate;
    }

    public Integer getProcessDueDate() {
        return processDueDate;
    }

    public void setTimeUnitForProcessDueDate(String timeUnitForProcessDueDate) {
        this.timeUnitForProcessDueDate = timeUnitForProcessDueDate;
    }

    public String getTimeUnitForProcessDueDate() {
        return timeUnitForProcessDueDate;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void cancelProcByCard() {
        updateTimer.cancelProcByCard(procCode, processDueDate, timeUnitForProcessDueDate, moduleName);
    }
}
