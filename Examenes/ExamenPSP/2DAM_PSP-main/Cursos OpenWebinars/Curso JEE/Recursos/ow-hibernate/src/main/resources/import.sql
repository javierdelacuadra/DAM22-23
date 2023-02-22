INSERT INTO employees(full_name, biography, married, age) values ('Employee 1', 'Lorem ipsum dolor', true, 20);
INSERT INTO employees(full_name, biography, married, age) values ('Employee 2', 'Lorem ipsum dolor', true, 30);
-- @Type(type = "yes_no")
-- INSERT INTO employees(full_name, biography, married) values ('Employee 1', 'Lorem ipsum dolor', 'N');
-- INSERT INTO employees(full_name, biography, married) values ('Employee 2', 'Lorem ipsum dolor', 'Y');
-- @Type(type = "true_false")
-- INSERT INTO employees(full_name, biography, married) values ('Employee 1', 'Lorem ipsum dolor', 'T');
-- INSERT INTO employees(full_name, biography, married) values ('Employee 2', 'Lorem ipsum dolor', 'F');
-- @Enumerated(EnumType.STRING)
INSERT INTO employees(full_name, biography, married, `type`, age) values ('Employee EJEMPLO', 'Lorem ipsum dolor', true, 'SENIOR', 25);
-- one to one
INSERT INTO directions (city, country, postal_code, street) VALUES('Madrid', 'Spain', '28018', 'Avenida independencia');
INSERT INTO employees(full_name, biography, married, id_direction) values ('Employee con Direccion', 'Lorem ipsum dolor', true, 1);
-- many to one
INSERT INTO companies (capital, cif, name, start_date) VALUES(3500, 'B2345', 'Cosmic developments', '2020-12-30');
INSERT INTO directions (city, country, postal_code, street) VALUES('Madrid', 'Spain', '28018', 'Avenida independencia2');
INSERT INTO employees(full_name, biography, married, id_company, id_direction, age) values ('Employee1 con Empresa', 'Lorem ipsum dolor', true, 1, 2, 18);
-- one to many: añado un empleado mas para verificar que one company tiene many empleados:
INSERT INTO employees(full_name, biography, married, id_company) values ('Employee2 con Empresa', 'Lorem ipsum dolor', true, 1);
-- many to many:
INSERT INTO employees(full_name, biography, married) values ('Employee1 Many To Many', 'Lorem ipsum dolor', true);
INSERT INTO employees(full_name, biography, married) values ('Employee2 Many To Many', 'Lorem ipsum dolor', true);
INSERT INTO projects (prefix, title) VALUES('PRJ1', 'Project Many to many 1');
INSERT INTO projects (prefix, title) VALUES('PRJ2', 'Project Many to many 2');
INSERT INTO projects (prefix, title) VALUES('PRJ2', 'Project Many to many 3');
-- project 1
INSERT INTO employees_projects (id_employee, id_project) VALUES(7, 1);
INSERT INTO employees_projects (id_employee, id_project) VALUES(8, 1);
-- project 2
INSERT INTO employees_projects (id_employee, id_project) VALUES(7, 2);
INSERT INTO employees_projects (id_employee, id_project) VALUES(8, 2);
-- project 3
INSERT INTO employees_projects (id_employee, id_project) VALUES(7, 3);