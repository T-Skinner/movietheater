package com.jpmc.theater;

import java.time.Duration;
import java.util.Objects;

public class Movie {
    private static final int MOVIE_CODE_SPECIAL = 1;

    private final String title;
    private final Duration runningTime;
    private final double ticketPrice;
    private final boolean isSpecial;

    public Movie(String title, Duration runningTime, double ticketPrice, boolean isSpecial) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.isSpecial = isSpecial;
    }

    public String getTitle() {
        return title;
    }

    public boolean isSpecial() {
        return isSpecial;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(isSpecial, movie.isSpecial);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, runningTime, ticketPrice, isSpecial);
    }
}