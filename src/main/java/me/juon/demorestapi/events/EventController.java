package me.juon.demorestapi.events;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @author Kim Juon
 */
@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventValidator eventValidator;

    @PostMapping()
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        eventValidator.validate(eventDto,  errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        Event savedEvent = this.eventRepository.save(event);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(savedEvent.getId());
        URI uri = selfLinkBuilder.toUri();
        EntityModel<Event> entityModel = EntityModel.of(savedEvent);
        entityModel.add(linkTo(EventController.class).withRel("query-events"));
        entityModel.add(linkTo(EventController.class).withRel("update-event"));
        entityModel.add(linkTo(EventController.class).slash(savedEvent.getId()).withSelfRel());
        return ResponseEntity.created(uri).body(entityModel);
    }
}
