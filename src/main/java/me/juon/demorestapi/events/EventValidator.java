package me.juon.demorestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

/**
 * @author Kim Juon
 */
@Component
public class EventValidator {
    public void validate(EventDto eventDto, Errors errors) {
        if (eventDto.getMaxPrice() != 0 && (eventDto.getBasePrice() > eventDto.getMaxPrice())) {
            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong!");
            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong!");
        }

        LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
        if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
                endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
                endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong!");
        }

    }
}
