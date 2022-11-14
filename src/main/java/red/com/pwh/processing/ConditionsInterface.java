package red.com.pwh.processing;

import red.com.pwh.dao.DailyDAO;
import red.com.pwh.dao.DailyDAOInterface;
import red.com.pwh.dao.HourlyDAOInterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ConditionsInterface {


    LocalTime get_morning_golden_hour(LocalTime sunrise);

    LocalTime get_evening_golden_hour(LocalTime sunset);

    LocalTime get_evening_blue_hour_start(LocalTime sunset);

    LocalTime get_morning_blue_hour_start(LocalTime sunrise);

    LocalTime get_evening_blue_hour_end(LocalTime sunset);

    LocalTime get_morning_blue_hour_end(LocalTime sunrise);

    LocalDate best_date_overall(HourlyDAOInterface hourlyDAOInterface);


    LocalDate best_date_morning(HourlyDAOInterface hourlyDAOInterface, LocalTime sunrise);

    LocalDate best_date_evening(HourlyDAOInterface hourlyDAOInterface, LocalTime sunset);

    LocalDate best_date_early_night(HourlyDAOInterface hourlyDAOInterface, LocalTime sunset);

    LocalDate best_date_late_night(HourlyDAOInterface hourlyDAOInterface, LocalTime sunrise);
}
