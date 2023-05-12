package com.jpmc.theater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTests {

    @Test
    @DisplayName("Customer fee should be calculated correctly")
    void totalFeeForCustomer() {
        Theater theater = Theater.createExampleInstanceWithRules();
        Customer john = new Customer("John Doe", "id-12345");
        Reservation reservation = theater.reserve(john, 2, 5);
        assertEquals(reservation.totalFee(), 50);
    }


    @Test
    @DisplayName("Printing movie schedule should work")
    void printMovieSchedule() {
        Theater theater = Theater.createExampleInstanceWithRules();
        theater.printSchedule();
    }

    @Test
    @DisplayName("Printing movie schedule in JSON should work")
    void printMovieJsonSchedule() {
        Theater theater = Theater.createExampleInstanceWithRules();
        theater.printScheduleJson();
    }

    @Test
    @DisplayName("Only the max discount rule should be applied")
    void testTwoDiscounts() {
        List<DiscountRule> discounts = List.of(
                DiscountRule.specialMoveRule,
                DiscountRule.firstOfDayRule
        );
        LocalDate date = LocalDate.now();
        var schedule = List.of(
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 50, true),
                        1, LocalDateTime.of(date, LocalTime.of(9, 0)))
        );
        Theater theater = new Theater(schedule, discounts);
        assertEquals(40, theater.getMovieFee(1));
    }

    @Test
    @DisplayName("special movie discount rule should work")
    void testDiscountSpecial() {
        List<DiscountRule> discounts = List.of(
                DiscountRule.specialMoveRule
        );
        LocalDate date = LocalDate.now();
       var schedule = List.of(
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 50, true),
                        1, LocalDateTime.of(date, LocalTime.of(9, 0)))
       );
        Theater theater = new Theater(schedule, discounts);
        assertEquals(40, theater.getMovieFee(1));
    }

    @Test
    @DisplayName("4 to 11 discount rule should work")
    void testDiscountFourToEleven() {
        List<DiscountRule> discounts = List.of(
                DiscountRule.fourToElevenRule
        );
        LocalDate date = LocalDate.now();
        var schedule = List.of(
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 100, false),
                        1, LocalDateTime.of(date, LocalTime.of(12 + 5, 0))),
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 100, false),
                2, LocalDateTime.of(date, LocalTime.of(3, 0)))
        );
        Theater theater = new Theater(schedule, discounts);
        assertEquals(75, theater.getMovieFee(1));
        assertEquals(100, theater.getMovieFee(2));
    }

    @Test
    @DisplayName("2nd movie of day discount should work")
    void testDiscountSecondOfDay() {
        List<DiscountRule> discounts = List.of(
                DiscountRule.secondOfDayRule
        );
        LocalDate date = LocalDate.now();
        var schedule = List.of(
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 100, false),
                        1, LocalDateTime.of(date, LocalTime.of(12 + 5, 0))),
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 100, false),
                        2, LocalDateTime.of(date, LocalTime.of(3, 0)))
        );
        Theater theater = new Theater(schedule, discounts);
        assertEquals(100, theater.getMovieFee(1));
        assertEquals(98, theater.getMovieFee(2));
    }

    @Test
    @DisplayName("7th of month discount should work")
    void testDiscountSeventhOfMonth() {
        List<DiscountRule> discounts = List.of(
                DiscountRule.seventhOfMonthRule
        );
        LocalDate date7 = LocalDate.of(2022, 5, 7);
        LocalDate date5 = LocalDate.of(2022, 5, 5);
        var schedule = List.of(
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 100, false),
                        1, LocalDateTime.of(date7, LocalTime.of(12 + 5, 0))),
                new Showing(new Movie("Turning Red", Duration.ofMinutes(85), 100, false),
                        2, LocalDateTime.of(date5, LocalTime.of(3, 0)))
        );
        Theater theater = new Theater(schedule, discounts);
        assertEquals(99, theater.getMovieFee(1));
        assertEquals(100, theater.getMovieFee(2));
    }

}
