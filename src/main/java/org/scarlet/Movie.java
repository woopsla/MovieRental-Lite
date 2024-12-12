package org.scarlet;

import java.time.LocalDate;

public class Movie {
    public static final int REGULAR = 0;
    public static final int NEW_RELEASE = 1;
    public static final int CHILDREN = 2;

    public static final int AVAILABLE = 0;
    public static final int RENTED = 1;
    public static final int RETURNED = 2;

    private final String title;
    private int state = AVAILABLE;
    private int priceCode;

    public Movie(String title, int priceCode) {
        this.title = title;
        this.priceCode = priceCode;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int arg) {
        priceCode = arg;
    }

    public String getTitle (){
        return title;
    };

    public void changeState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    void rentFor(Customer customer, LocalDate rentedOn) {
        changeState(Movie.RENTED);
        Rental rental = new Rental(this, rentedOn);
        customer.addRental(rental);
    }
}
