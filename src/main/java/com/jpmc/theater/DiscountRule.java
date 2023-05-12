package com.jpmc.theater;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class DiscountRule {

    private final String name;
    Function<Showing, Optional<Double>> calcDiscount;
    /**
     * @param name customer name
     * @param calcDiscount lambda for calculating discount based on showing
     */
    public DiscountRule(String name, Function<Showing, Optional<Double>> calcDiscount) {
            this.name = name;
            this.calcDiscount = calcDiscount;
    }

    public String getName() {
        return name;
    }
    public Optional<Double> getDiscount(Showing showing) {
        return calcDiscount.apply(showing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountRule)) return false;
        DiscountRule discountRule = (DiscountRule) o;
        return Objects.equals(name, discountRule.name) && Objects.equals(calcDiscount, discountRule.calcDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, calcDiscount);
    }

    @Override
    public String toString() {
        return "name: " + name;
    }
    public static DiscountRule specialMoveRule = new DiscountRule("20% off -- Special movie",  s -> s.getMovie().isSpecial() ? Optional.of(s.getMovie().getTicketPrice() * 0.2) : Optional.empty());
    public static DiscountRule firstOfDayRule = new DiscountRule("2$ off -- 1st movie of the day",  s -> s.getSequenceOfTheDay() == 1 ? Optional.of(3d) : Optional.empty());
    public static DiscountRule secondOfDayRule = new DiscountRule("1$ off -- 2nd movie of the day",  s -> s.getSequenceOfTheDay() == 2 ? Optional.of(2d) : Optional.empty());
    public static DiscountRule fourToElevenRule = new DiscountRule("25% off -- between 4pm and 11pm",  s -> { var h = s.getStartTime().getHour(); return h >= 16 && h < 22 ? Optional.of(s.getMovie().getTicketPrice() * 0.25) : Optional.empty();});
    public static DiscountRule seventhOfMonthRule = new DiscountRule("1$ off -- 7th day of month",  s -> s.getStartTime().getDayOfMonth() == 7 ? Optional.of(1d) : Optional.empty());
    public static List<DiscountRule> rules = List.of(
            specialMoveRule,
            firstOfDayRule,
            secondOfDayRule,
            fourToElevenRule,
            seventhOfMonthRule
    );
}