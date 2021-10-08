create table category_employee (category_id bigint, employee_id bigint not null, primary key (employee_id));
alter table category_employee add constraint FKtiim1uoqixtmsahoxtq140vhh foreign key (category_id) references employee_category;
alter table category_employee add constraint FKg0m0rck6cd1fe4o4icg0alvwo foreign key (employee_id) references employee;