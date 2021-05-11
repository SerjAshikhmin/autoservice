package com.senla.courses.autoservice.service.comparators.master;

import com.senla.courses.autoservice.model.domain.Master;

import java.util.Comparator;

public class MasterByNameComparator implements Comparator<Master> {

    private static MasterByNameComparator instance;

    private MasterByNameComparator() {}

    public static MasterByNameComparator getInstance() {
        if (instance == null) {
            instance = new MasterByNameComparator();
        }
        return instance;
    }

    @Override
    public int compare(Master master, Master t1) {
        return master.getName().compareTo(t1.getName());
    }
}
