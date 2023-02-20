DROP TABLE IF EXISTS users, categories, compilations, events, compilation_event, requests, location, likes, dislikes;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY not null
        CONSTRAINT users_pk
            PRIMARY KEY,
    name  varchar(255)                            not null,
    email varchar(512) unique                     not null,
    ratings bigint
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL
        CONSTRAINT categories_pk
            PRIMARY KEY,
    name varchar(255) unique                     not null
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL
        CONSTRAINT compilations_pk
            PRIMARY KEY,
    title  varchar(255),
    pinned boolean
);

CREATE TABLE IF NOT EXISTS location
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL
        CONSTRAINT location_pk
            PRIMARY KEY,
    lat double precision                        not null,
    lon double precision                        not null
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL
        CONSTRAINT events_pk
            PRIMARY KEY,
    annotation         varchar(2000)                           not null,
    category_id        bigint
        constraint events_categories_id_fk
            references categories,
    description        varchar(7000)                           not null,
    title              varchar(1000)                           not null,
    event_date         timestamp with time zone,
    paid               boolean                                 not null,
    location_id        bigint                                  not null
        constraint events_locations_id_fk
            references location,
    initiator_id       bigint
        constraint events_users_id_fk
            references users,
    created_on         timestamp with time zone,
    participant_limit  int,
    published_on       timestamp with time zone,
    request_moderation boolean,
    state              varchar(20)                             not null
);

CREATE TABLE IF NOT EXISTS likes
(
    user_id  bigint not null,
    event_id bigint not null,
    constraint likes_pk primary key (event_id, user_id),
    constraint likes_users_id_fk foreign key (user_id) references users (id) on delete cascade,
    constraint likes_events_id_fk foreign key (event_id) references events (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS dislikes
(
    user_id  bigint not null,
    event_id bigint not null,
    constraint dislikes_pk primary key (event_id, user_id),
    constraint dislikes_users_id_fk foreign key (user_id) references users (id) on delete cascade,
    constraint dislikes_events_id_fk foreign key (event_id) references events (id) on delete cascade
);

CREATE TABLE IF NOT EXISTS compilation_event
(
    event_id       bigint,
    compilation_id bigint,
    primary key (event_id, compilation_id),
    CONSTRAINT fk_compiled_events_compilations FOREIGN KEY (compilation_id)
        REFERENCES compilations (id) ON DELETE CASCADE,
    CONSTRAINT fk_compiled_events_events FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL
        CONSTRAINT requests_pk
            PRIMARY KEY,
    created      timestamp with time zone,
    event_id     bigint                                  not null
        constraint requests_events_id_fk
            references events,
    requester_id bigint                                  not null
        constraint requests_users_id_fk
            references users,
    status       varchar(20)                             not null
);