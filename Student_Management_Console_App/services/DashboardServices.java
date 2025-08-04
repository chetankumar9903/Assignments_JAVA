package com.aurionpro.services;

import java.util.List;

import com.aurionpro.dao.DashboardDao;
import com.aurionpro.model.Dashboard;

public class DashboardServices {
    private DashboardDao dashboardDAO;

    public DashboardServices() {
        this.dashboardDAO = new DashboardDao();
    }

    public List<Dashboard> getDashboardEntries() {
        return dashboardDAO.getDashboardData();
    }
}
