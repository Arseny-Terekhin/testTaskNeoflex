package com.example.calcSpringNeoFlex.controller;

import com.example.calcSpringNeoFlex.service.VacationPayCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class CalculateController {

    private final VacationPayCountService payCountService;

    @Autowired
    public CalculateController(VacationPayCountService payCountService) {
        this.payCountService = payCountService;
    }


    @GetMapping("/calculate")
    public String calculateVacation(@RequestParam(name = "amount_of_salary") double amountOfSalary,
                                    @RequestParam(name = "count_vacation_days") int countVacationDays,
                                    @RequestParam(required = false, name = "vacation_start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate vacationStartDate) {
        if (countVacationDays <= 0) {
            return "кольичество дней не может быть отрицательным";
        }
        if (amountOfSalary <= 0) {
            return "Заплата не может быть отрицательной";
        }

        return String.format("%.2f", payCountService.calculateVacationPay(amountOfSalary, countVacationDays, vacationStartDate));
    }
}
