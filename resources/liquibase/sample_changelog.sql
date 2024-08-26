--liquibase formatted sql

--changeset your.name:1 labels:example-label context:example-context
--comment: example comment
create table person (
    id int primary key identity(1, 1) not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE person;

--changeset your.name:2 labels:example-label context:example-context
--comment: example comment
create table company (
    id int primary key identity(1, 1) not null,
    name varchar(50) not null,
    address1 varchar(50),
    address2 varchar(50),
    city varchar(30)
)
--rollback DROP TABLE company;

--changeset other.dev:3 labels:example-label context:example-context
--comment: example comment
--alter table person add column country nvarchar(2)
--rollback ALTER TABLE person DROP COLUMN country;

--changeset your.name:3
INSERT INTO company (name, address1, address2, city) VALUES
    ('firma 1', 'papli 32', 'polegi', 'Tallinn'),
    ('firma 2', 'raamatu 151', 'metsaaiaotsa 4', 'Paide');