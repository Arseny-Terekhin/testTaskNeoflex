package com.example.calcSpringNeoFlex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationPayCountService {

    @Value("${vacation.month.avg.days}")
    double avgMonthDays;

    private List<LocalDate> holidays ;

    @Autowired
    private void setHolidays(@Value("${list.holidays}") String stringListHolidays){
        this.holidays = Arrays.stream(stringListHolidays.split(","))
                .map(LocalDate :: parse)
                .collect(Collectors.toList());
    }

    public int calculateWorkDays(LocalDate startDate, int countVacationDays){
        int workDays = 0;
        LocalDate date = startDate;

        for (int i = 0; i <  countVacationDays; i++) {
            if (isWorkdays(date)) {
                workDays++;
            }
            date = date.plusDays(1);
        }
        return workDays;
    }

    public double calculateVacationPay(double amountOfSalary, int countDays, LocalDate startDate) {
        if (startDate != null) {
            int workDays = calculateWorkDays(startDate, countDays);
            return amountOfSalary / avgMonthDays * workDays;
        } else {
            return amountOfSalary / avgMonthDays * countDays;
        }
    }

    private boolean isWorkdays(LocalDate date){
        return !(date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || holidays.contains(date));
    }
}
