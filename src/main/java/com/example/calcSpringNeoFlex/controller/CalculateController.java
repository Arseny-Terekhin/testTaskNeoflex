package com.example.calcSpringNeoFlex.controller;

import com.example.calcSpringNeoFlex.service.VacationPayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CalculateController {

    @Autowired
    VacationPayCountService payCountService;

    @Value("${vacation.month.avg.days}")
    double avgMonthDays;

    @GetMapping("/calculate")
    public String calculateVacation(@RequestParam(name =  "amount_of_salary") double amountOfSalary,
                                    @RequestParam(name =  "count_vacation_days") int countVacationDays,
                                    @RequestParam(required = false, name = "vacation_start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationStartDate){
        double vacationPay = 0.0;

        if (vacationStartDate != null) {
            long workDays = payCountService.calculateWorkDays(vacationStartDate, countVacationDays);
            vacationPay = amountOfSalary / avgMonthDays * workDays;
        } else {
            vacationPay = amountOfSalary / avgMonthDays * countVacationDays;
        }

        return String.format("%.2f", vacationPay);
    }

}
