package me.juon.demorestapi.events;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Kim Juon
 */
class EventTest {
    @Test
    void builder() {
        Event event = Event.builder().build();
        assertThat(event).isNotNull();
    }

    @Test
    void javaBean() {
        Event event = new Event();
        assertThat(event).isNotNull();
    }
}