package com.jpmc.theater;

import javax.swing.table.JTableHeader;

public class Reservation {
    private final Customer customer;
    private final Showing showing;
    private final int audienceCount;
    private final Theater theater;

    public Reservation(Customer customer, Theater theater, Showing showing, int audienceCount) {
        this.customer = customer;
        this.showing = showing;
        this.audienceCount = audienceCount;
        this.theater = theater;
    }

    public double totalFee() {
        return showing.getMovieFee(theater.getDiscountRules()).getLeft() * audienceCount;
    }
}