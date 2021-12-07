create table REPOSITORY_FORK_TESTS
(
    REPOSITORY_FORK_TEST_ID int auto_increment,
    REPOSITORY_FORK_ID int not null,
    REPOSITORY_FORK_TEST_CLASS_NAME character(2048) not null,
    REPOSITORY_FORK_TEST_CASES_TOTAL int not null,
    REPOSITORY_FORK_TEST_CASES_SUCCESS int not null,
    constraint REPOSITORY_FORK_TESTS_PK
        primary key (REPOSITORY_FORK_TEST_ID)
);

