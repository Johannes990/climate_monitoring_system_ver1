--liquibase formatted sql

--changeset your.name:1 labels:example-label context:example-context
--comment: example comment
create table test_person (
    id int primary key identity(1, 1) not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE person;

--changeset your.name:2 labels:example-label context:example-context
--comment: example comment
create table test_company (
    id int primary key identity(1, 1) not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE company;

--changeset other.dev:3 labels:example-label context:example-context
--comment: example comment
--alter table person add column country varchar(2)
--rollback ALTER TABLE person DROP COLUMN country;

--changeset your.name:4
DROP TABLE name_table;
DROP TABLE test_company;
DROP TABLE test_person;

--changeset your.name:5
DROP TABLE person;
DROP TABLE company;

