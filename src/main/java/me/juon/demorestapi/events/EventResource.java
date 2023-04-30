package me.juon.demorestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;

/**
 * @author Kim Juon
 */
@Getter
public class EventResource extends EntityModel<Event> {
    @JsonUnwrapped
    private Event event;

    public EventResource(Event event) {
        this.event = event;
    }
}
