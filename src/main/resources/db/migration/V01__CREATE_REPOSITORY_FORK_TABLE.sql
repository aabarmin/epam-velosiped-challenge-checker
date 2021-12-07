create table REPOSITORY_FORKS
(
    REPOSITORY_FORK_ID int auto_increment,
    REPOSITORY_FORK_NAME character(2048) not null,
    REPOSITORY_FORK_LAST_UPDATED datetime not null,
    constraint REPOSITORY_FORKS_PK
        primary key (REPOSITORY_FORK_ID)
);

create unique index REPOSITORY_FORKS_REPOSITORY_FORK_NAME_UINDEX
    on REPOSITORY_FORKS (REPOSITORY_FORK_NAME);