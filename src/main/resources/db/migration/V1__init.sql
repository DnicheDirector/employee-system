create sequence hibernate_sequence start with 1 increment by 1;

create table employee (
    id bigint not null,
    name varchar(255) not null,
    employee_category_id bigint,
    primary key (id)
);

create table employee_category (
    id bigint not null,
    name varchar(255) not null,
    primary key (id)
);

alter table employee add constraint CATEGORY_FK
    foreign key (employee_category_id) references employee_category;