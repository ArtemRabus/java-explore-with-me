package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "hits")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String app;
    String uri;
    String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
}
