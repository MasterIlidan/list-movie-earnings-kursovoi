create table user_Table (
    id int8 generated by default as identity primary key,
    username varchar(40),
    password varchar(100),
    email varchar(60) unique
);

drop table user_Table;

drop table role;

drop table user_roles;

drop table movies;

create table role (
    role_Name varchar(20) primary key
);

create table movies_actors (
    movie VARCHAR REFERENCES MOVIES(id),
    Actor VARCHAR REFERENCES ACTORS(id)
);

insert into role values ('READ_ONLY');
insert into role values ('USER');

insert into currencies values (DEFAULT, 'USD');

select * from user_roles;

select * from movies;

select * from user_Table;

create table user_roles (
    id int8 references user_Table,
    role_Name varchar references role
);



create table movies (
    id int8 generated by default as identity primary key,
    movie_Name varchar(50),
    release_Date date,
    earnings float8,
    currency varchar references currencies,
    add_by varchar references user_Table(email) not null
);

create table currencies (
    id int8 GENERATED by default as identity primary key,
    currency varchar(10)
);
ALTER TABLE currencies add column id int8 generated by default as identity primary key;
ALTER TABLE currencies ALTER COLUMN CURRENCY VARCHAR(10);


create table actors (
    id int8 generated by default as identity primary key,
    firstname varchar(50),
    lastname varchar(50),
    birthday date,
    Description varchar(1000)
);