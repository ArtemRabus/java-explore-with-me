package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.enums.Status;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    @Query("SELECT COUNT(r.event) FROM Request r " +
            "WHERE r.event.id = ?1 AND r.status = ?2")
    Long findConfirmedRequests(Long eventId, Status confirmed);

    List<Request> findAllByRequesterId(Long id);

    List<Request> findAllByEvent_IdIs(Long eventId);

    List<Request> findAllByEventIdAndStatus(Long id, Status status);

    @Query("select r from Request r join Event e on e.id=r.event.id where e.initiator.id = ?1 and r.event.id = ?2")
    List<Request> findAllByRequesterIdAndEventId(Long userId, Long eventId);

    @Query("SELECT r from Request r where r.id = ?1 and r.requester.id = ?2")
    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    @Query("SELECT r FROM Request r " +
            "WHERE r.event.id = ?1 AND r.id IN ?2")
    List<Request> findRequestsForUpdate(Long eventId, List<Long> requestIds);
}
