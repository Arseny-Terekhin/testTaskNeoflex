package com.example.calcSpringNeoFlex;

import com.example.calcSpringNeoFlex.service.VacationPayCountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalcSpringNeoFlexApplicationTests {

    @Autowired
    private VacationPayCountService vacationPayCountService;

    @Test
        //тест на вычисление рабочих дней, когда отпуск задевает все праздничные дни 1 января - 8 января
    void testCalculateWorksDays_Test1() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        int vacationsDays = 5;

        int expectedWorkDays = 0;
        int actualWorkDays = vacationPayCountService.calculateWorkDays(startDate, vacationsDays);

        assertEquals(expectedWorkDays, actualWorkDays);
    }

    @Test
        //тест когда отпуск затрагивает несколько праздничных/выходных дней
    void testCalculateWorksDays_Test2() {
        LocalDate startDate = LocalDate.of(2025, 1, 8);
        int vacationsDays = 7;

        int expectedWorkDays = 4;
        int actualWorkDays = vacationPayCountService.calculateWorkDays(startDate, vacationsDays);

        assertEquals(expectedWorkDays, actualWorkDays);
    }

    @Test
        //Расчет отпускных, когда 0 рабочих дней
    void testCalculateVacationPay_Test1() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        int vacationsDays = 5;
        double amountOfSalary = 150000;

        double expectedVacationPay = 0;
        double actualVacationPay = vacationPayCountService.calculateVacationPay(amountOfSalary, vacationsDays, startDate);

        assertEquals(expectedVacationPay, actualVacationPay);
    }

    @Test
        //Расчет отпускных, когда 4 рабочих дня
    void testCalculateVacationPay_Test2() {
        LocalDate startDate = LocalDate.of(2025, 1, 8);
        int vacationsDays = 7;
        double amountOfSalary = 150000;

        double expectedVacationPay = 20477.8;
        double actualVacationPay = vacationPayCountService.calculateVacationPay(amountOfSalary, vacationsDays, startDate);

        assertEquals(expectedVacationPay, actualVacationPay, 0.1);
    }

    @Test
        //Расчет отпускных, когда не указанна начальная дата при 5 дневном отпуске
    void testCalculateVacationPay_Test3() {
        LocalDate startDate = null;
        int vacationsDays = 5;
        double amountOfSalary = 150000;

        double expectedVacationPay = 25597.2;
        double actualVacationPay = vacationPayCountService.calculateVacationPay(amountOfSalary, vacationsDays, startDate);

        assertEquals(expectedVacationPay, actualVacationPay, 0.1);
    }

}
