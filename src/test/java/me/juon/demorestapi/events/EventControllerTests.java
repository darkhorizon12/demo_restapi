package me.juon.demorestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.juon.demorestapi.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Kim Juon
 */
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(EventController.class)
public class EventControllerTests {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("정상적으로 이벤트를 생성하는 테스트")
    void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("spring")
                .description("Rest api developement")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 4, 12, 13, 22))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 4, 13, 14, 00))
                .beginEventDateTime(LocalDateTime.of(2023, 4, 28, 00, 0))
                .endEventDateTime(LocalDateTime.of(2023, 4, 30, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 2번")
                .build();
//        Mockito.when(eventRepository.save(event)).thenReturn(event);    // 목빈의 해당 메서드 이벤트가 발생할 때, 임의의 객체 리턴

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    void createEvent_나쁜요청() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("spring")
                .description("Rest api developement")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 4, 12, 13, 22))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 4, 13, 14, 00))
                .beginEventDateTime(LocalDateTime.of(2023, 4, 28, 00, 0))
                .endEventDateTime(LocalDateTime.of(2023, 4, 30, 0, 0))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 2번")
                .free(true)
                .offline(false)
                .eventStatus(EventStatus.PUBLISHED)
                .build();
//        Mockito.when(eventRepository.save(event)).thenReturn(event);    // 목빈의 해당 메서드 이벤트가 발생할 때, 임의의 객체 리턴

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaTypes.HAL_JSON)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    void 나쁜요청_빈_입력값() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(eventDto))
                )
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("잘못된 입력값이 들어왔을 때 BadRequest 결과")
    void 나쁜요청_잘못된_입력값() throws Exception {
        EventDto event = EventDto.builder()
                .name("spring")
                .description("Rest api developement")
                .beginEnrollmentDateTime(LocalDateTime.of(2023, 4, 12, 13, 22))
                .closeEnrollmentDateTime(LocalDateTime.of(2023, 4, 8, 14, 00))
                .beginEventDateTime(LocalDateTime.of(2023, 4, 28, 00, 0))
                .endEventDateTime(LocalDateTime.of(2023, 4, 10, 0, 0))
                .basePrice(10_000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 2번")
                .build();
//        Mockito.when(eventRepository.save(event)).thenReturn(event);    // 목빈의 해당 메서드 이벤트가 발생할 때, 임의의 객체 리턴

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(event))
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
//                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
//                .andExpect(jsonPath("$[0].rejectedValue").exists())
        ;

    }
}
