package com.senla.courses.autoservice.service.comparators.master;

import com.senla.courses.autoservice.model.domain.Master;

import java.util.Comparator;

public class MasterByBusyComparator implements Comparator<Master> {

    private static MasterByBusyComparator instance;

    private MasterByBusyComparator() {}

    public static MasterByBusyComparator getInstance() {
        if (instance == null) {
            instance = new MasterByBusyComparator();
        }
        return instance;
    }

    @Override
    public int compare(Master master, Master t1) {
        if (master.isBusy() == t1.isBusy()) return 0;
        else
            if (master.isBusy()) return 1;
            else return -1;
    }
}
