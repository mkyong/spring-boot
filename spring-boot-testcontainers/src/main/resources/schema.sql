create table if not exists books (
    id bigserial not null,
    name varchar not null,
    isbn varchar not null,
    primary key (id),
    UNIQUE (isbn)
);