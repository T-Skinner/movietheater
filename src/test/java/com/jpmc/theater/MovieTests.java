package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MovieTests {
    @Test
    void movieGetters() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, true);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.now()));
        assertEquals(12.5, spiderMan.getTicketPrice());
        assertEquals(Duration.ofMinutes(90), spiderMan.getRunningTime());
        assertTrue(spiderMan.isSpecial());
        assertEquals("Spider-Man: No Way Home", spiderMan.getTitle());
    }
}
