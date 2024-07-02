CREATE TABLE users (
id              uuid not null,
username        varchar(255) PRIMARY KEY not null,
password        varchar(255) not null,
role            varchar(255) not null,
created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);