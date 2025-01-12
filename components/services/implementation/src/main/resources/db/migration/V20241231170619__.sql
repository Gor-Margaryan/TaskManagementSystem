CREATE TABLE jirasd.user
(
    id           UUID primary key not null,
    name      varchar(64)      not null,
    surname         varchar(64)      not null,
    email             varchar(64)      not null unique,
    password          varchar(64)      not null

);
CREATE TABLE jirasd.task
(
    id           UUID primary key not null,
    creator      varchar(64)      not null,
    performerid         UUID  not null  ,
    status             varchar(64)      not null ,
    priority          varchar(64)      not null,
    name   varchar(64) not null ,
    description varchar(1024) not null ,
    comment varchar(256)

);