package org.scarlet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {
    private Customer customer = new Customer("John Doe");
    private final Movie movie = new Movie("Fugitive", Movie.REGULAR);
    private final Movie newMovie = new Movie("Witch III", Movie.NEW_RELEASE);

    @Test
    @DisplayName("a video should increase rental count by one")
    void test1() {
        // Arrange (Given)
        // Act (When)
        movie.rentFor(customer, LocalDate.now());

        // Assert (Then)
        assertEquals(1, customer.getRentals().size());
        assertEquals(1, movie.getState());
    }

    @Test
    @DisplayName("customer name should not be null")
    void test2() {
        assertThrows(IllegalArgumentException.class, () ->
                customer = new Customer(null)
        );
    }

    @Test
    @DisplayName("movie return should change its status as returned")
    void test3() {
        // Arrange (Given)
        movie.rentFor(customer, LocalDate.now());

        // Act (When)
        customer.returnMovie(movie.getTitle(), LocalDate.now().plusDays(1));

        // Assert (Then)
        assertEquals(2, movie.getState());
    }

    @Test
    @DisplayName("statement for one regular rental for two days")
    public void test4() throws Exception {
        // Arrange (Given)
        String expected = "Rental Record for " + customer.getName() + "\n";
        expected += "\t" + movie.getTitle() + "\t" + 2.0 + "\n";
        expected += "Amount owed is " + 2.0 + "\n";
        expected += "You earned " + 1 + " frequent renter points";

        movie.rentFor(customer, LocalDate.now());
        customer.returnMovie(movie.getTitle(), LocalDate.now().plusDays(2));

        // Act (When)
        String actual = customer.statement();

        // Assert (Then)
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("statement for one new release rental for three days")
    public void test5() {
        // Arrange (Given)
        String expected = "Rental Record for " + customer.getName() + "\n";
        expected += "\t" + newMovie.getTitle()+ "\t" + 9.0 + "\n";
        expected += "Amount owed is " + 9.0 + "\n";
        expected += "You earned " + 2 + " frequent renter points";

        newMovie.rentFor(customer, LocalDate.now());
        customer.returnMovie(newMovie.getTitle(), LocalDate.now().plusDays(3));

        // Act (When)
        String actual = customer.statement();

        // Assert (Then)
        assertEquals(expected, actual);
    }
}

