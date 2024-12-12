package org.scarlet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Customer {
    private final String name;
    private final List<Rental> rentals = new ArrayList<>();

    public Customer(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

    public void addRental(Rental arg) {
        rentals.add(arg);
    }

    public List<Rental> getRentals() {
        return Collections.unmodifiableList(rentals);
    }

    public String getName() {
        return name;
    }

    void returnMovie(String title, LocalDate returnedOn) {
        rentals.stream().filter(rental -> rental.getMovie().getTitle().equals(title))
                .findFirst().ifPresentOrElse(
                        rental -> rental.returnMovie(returnedOn)
                ,
                () -> {
                    throw new IllegalArgumentException("No movie to return");
                }
        );
    }

    public String statement() {
        double totalAmount = 0;
        int frequentRentalPoints = 0;
        String result = "Rental Record for " + getName() + "\n";

        for (Rental rental : rentals) {
            double thisAmount = 0;

            switch (rental.getMovie().getPriceCode()) {
                case Movie.REGULAR:
                    thisAmount += 2;
                    if (rental.getDaysRented() > 2)
                        thisAmount += (rental.getDaysRented() - 2) * 1.5;
                    break;
                case Movie.NEW_RELEASE:
                    thisAmount += rental.getDaysRented() * 3;
                    break;
                case Movie.CHILDREN:
                    thisAmount += 1.5;
                    if (rental.getDaysRented() > 3)
                        thisAmount += (rental.getDaysRented() - 3) * 1.5;
                    break;
            }

            // add frequent renter points
            frequentRentalPoints++;

            // add bonus for a two-day new release rental
            if ((rental.getMovie().getPriceCode() == Movie.NEW_RELEASE)
                    &&
                    rental.getDaysRented() > 1) frequentRentalPoints++;

            //show figures
            result += "\t" + rental.getMovie().getTitle() + "\t" + thisAmount + "\n";

            totalAmount += thisAmount;
        }

        // add footer lines
        result += "Amount owed is " + totalAmount + "\n";
        result += "You earned " + frequentRentalPoints + " frequent renter points";

        return result;
    }
}



