package com.example.calcSpringNeoFlex.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class VacationPayCountService {

    public long calculateWorkDays(LocalDate startDate, int countVacationDays){
        long workDays = 0;
        LocalDate date = startDate;

        for (int i = 0; i <  countVacationDays; i++) {
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workDays++;
            }
            date = date.plusDays(1);
        }
        return workDays;
    }
}
