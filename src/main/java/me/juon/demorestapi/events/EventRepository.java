package me.juon.demorestapi.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Kim Juon
 */
public interface EventRepository extends JpaRepository<Event, Integer> {
}
