package com.jpmc.theater;

import java.nio.file.OpenOption;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Showing {
    private final Movie movie;
    private final int sequenceOfTheDay;
    private final LocalDateTime showStartTime;

    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getStartTime() {
        return showStartTime;
    }

    public boolean isSequence(int sequence) {
        return this.sequenceOfTheDay == sequence;
    }

    public Pair<Double, Optional<DiscountRule>> getMovieFee(List<DiscountRule> discountRules) {
        return calculateDiscount(discountRules).map( d -> new Pair<>(movie.getTicketPrice() - d.getLeft(), Optional.of(d.getRight())))
                .orElse(new Pair<>(movie.getTicketPrice(), Optional.empty()));
    }

    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    public Optional<Pair<Double, DiscountRule>> calculateDiscount(List<DiscountRule> discountRules) {
        return discountRules.stream().map( r -> new Pair<>(r, r.getDiscount(this)))
                .filter( p -> p.getRight().isPresent())
                .max(Comparator.comparing( p -> p.getRight().get()))
                .map( p -> new Pair<>(p.getRight().get(), p.getLeft()));
    }
}
