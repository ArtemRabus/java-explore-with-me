package ru.practicum.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.category.model.Category;
import ru.practicum.enums.State;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "annotation", nullable = false)
    String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;
    @Column(name = "created_on")
    LocalDateTime createdOn;
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    User initiator;
    @Column(name = "description")
    String description;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "event_date", nullable = false)
    LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id")
    Location location;
    @Column(name = "paid", nullable = false)
    Boolean paid;
    @Column(name = "participant_limit")
    Integer participantLimit;
    @Column(name = "published_on")
    LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    State state;
    @ManyToMany(mappedBy = "likedEvents")
    Set<User> likes;
    @ManyToMany(mappedBy = "dislikedEvents")
    Set<User> dislikes;
}
