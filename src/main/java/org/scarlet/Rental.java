package org.scarlet;

import java.time.LocalDate;

public class Rental {
    private final Movie movie;
    private int daysRented = 0;
    private final LocalDate rentedOn;

    public Rental(Movie movie, LocalDate rentedOn) {
        this.movie = movie;
        this.daysRented = 0;
        this.rentedOn = rentedOn;
    }

    void returnMovie(LocalDate returnedOn) {
        movie.changeState(Movie.RETURNED);
        daysRented = (int) (returnedOn.toEpochDay() - rentedOn.toEpochDay());
    }

    public int getDaysRented() {
        return daysRented;
    }

    public Movie getMovie() {
        return movie;
    }
}

