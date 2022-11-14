package red.com.pwh.service;

import org.springframework.beans.factory.annotation.Autowired;
import red.com.pwh.dao.DailyDAOInterface;
import red.com.pwh.dao.HourlyDAOInterface;
import red.com.pwh.processing.ConditionsInterface;

public class PHService {

    private DailyDAOInterface dailyDAO;

    private HourlyDAOInterface hourlyDAO;

    private ConditionsInterface conditions;

    @Autowired
    public void setDailyDAO(DailyDAOInterface dailyDAO) {
        this.dailyDAO = dailyDAO;
    }

    @Autowired
    public void setHourlyDAO(HourlyDAOInterface hourlyDAO) {
        this.hourlyDAO = hourlyDAO;
    }

    @Autowired
    public void setConditions(ConditionsInterface conditions) {
        this.conditions = conditions;
    }


}
