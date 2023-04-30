package me.juon.demorestapi.events;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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

    @ParameterizedTest
    @CsvSource({
        "0, 0, true",
        "100, 0, false"
    })
    void testFree(int basePrice, int maxPrice, boolean isFree) {
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();

        event.update();

        assertThat(event.isFree()).isEqualTo(isFree);
    }

    @ParameterizedTest
    @MethodSource("parametersForTestOffline")
    @DisplayName("장소가 지정되면 offline 변수를 false로")
    void testOffline(String location, boolean isOffline) {
        Event event = Event.builder()
                .location(location)
                .build();

        event.update();

        assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    private static Stream<Arguments> parametersForTestOffline() {
        return Stream.of(
                Arguments.of("강남역", true),
                Arguments.of(null, false),
                Arguments.of("", false)
        );
    }
}