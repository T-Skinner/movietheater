package com.jpmc.theater;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.w3c.dom.DOMImplementationSource;

import javax.swing.text.html.Option;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Theater {


    private final List<Showing> schedule;

    private final List<DiscountRule> discountRules;

    public Theater(List<Showing> schedule, List<DiscountRule> discountRules) {
        this.schedule = schedule;
        this.discountRules = discountRules;
    }

    public List<DiscountRule> getDiscountRules() {
        return this.discountRules;
    }
    public List<Showing> getSchedule() {
        return this.schedule;
    }

    public double getMovieFee(int sequence) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return showing.getMovieFee(this.discountRules).getLeft();
    }
    public Optional<DiscountRule> getMovieDiscountRule(int sequence) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return showing.getMovieFee(this.discountRules).getRight();
    }
    public Reservation reserve(Customer customer, int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("not able to find any showing for given sequence " + sequence);
        }
        return new Reservation(customer, this, showing, howManyTickets);
    }

    public void printSchedule() {
        System.out.println("===================================================");
        schedule.forEach(s -> {
                    var feeDiscountPair = s.getMovieFee(discountRules);
                    System.out.println(s.getSequenceOfTheDay() + ": " + s.getStartTime()
                            + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime())
                            + " $" + feeDiscountPair.getLeft() + feeDiscountPair.getRight().map( dr -> " discount: " + dr.getName()).orElse(""));
                }
        );
        System.out.println("===================================================");
    }

    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    public void printScheduleJson() {
        var mapper = new ObjectMapper()
                        .enable(SerializationFeature.INDENT_OUTPUT)
                        .registerModule(new JavaTimeModule());

        try {
            var json = mapper.writeValueAsString(this.schedule);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static Theater createExampleInstance(List<DiscountRule> discounts) {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, true);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, false);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, false);
        LocalDate date = LocalDate.now();
       var schedule = List.of(
                new Showing(turningRed, 1, LocalDateTime.of(date, LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(date, LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(date, LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(date, LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(date, LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(date, LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(date, LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(date, LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(date, LocalTime.of(23, 0)))
        );
        Theater theater = new Theater(schedule, discounts);
        return theater;
    }
    public static Theater createExampleInstanceWithRules() {
        return createExampleInstance(DiscountRule.rules);
    }

    public static Theater createExampleInstanceNoRules() {
        return createExampleInstance(List.of());
    }
    public static void main(String[] args) {
        Theater theater = createExampleInstanceWithRules();
        theater.printScheduleJson();
        theater.printSchedule();
    }
}
