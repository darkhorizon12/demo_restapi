package me.juon.demorestapi.events;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

/**
 * @author Kim Juon
 */
@Builder
@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Event {
    @Id @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        this.free = (this.basePrice == 0 && this.maxPrice == 0) ? true : false;
        this.offline = ObjectUtils.isEmpty(this.location) ? false : true;
    }
}
