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
            "WHERE r.event.id = :eventId AND r.status = :confirmed")
    Long findConfirmedRequests(Long eventId, Status confirmed);

    List<Request> findAllByRequesterId(Long id);

    List<Request> findAllByEvent_IdIs(Long eventId);

    List<Request> findAllByEventIdAndStatus(Long id, Status status);

    @Query("select r from Request r join Event e on e.id=r.event.id where e.initiator.id = :userId and r.event.id = :eventId")
    List<Request> findAllByRequesterIdAndEventId(Long userId, Long eventId);

    @Query("SELECT r from Request r where r.id = :requestId and r.requester.id = :userId")
    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    @Query("SELECT r FROM Request r " +
            "WHERE r.event.id = :eventId AND r.id IN :requestIds")
    List<Request> findRequestsForUpdate(Long eventId, List<Long> requestIds);
}
