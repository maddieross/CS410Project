/*CREATE DATABASE rental;*/
USE rental;

CREATE TABLE model (
	model_id int not null auto_increment,
    name varchar(50) not null,
    make varchar(50) not null,
    engine_size int not null,
    year varchar(4) not null,
    primary key (model_id)
);

CREATE TABLE car (
	plate_number varchar(10) not null,
    num_miles int not null,
    status varchar(10) not null,    
    model int not null,
    primary key (plate_number),
    foreign key (model) REFERENCES model(model_id),
    CHECK (status='Rented' OR status='Available' OR status='Servicing')
);


CREATE TABLE economy (
	plate_number varchar(10) not null,
	fee int not null,
    foreign key (plate_number) references car(plate_number)
);

CREATE TABLE compact (
	plate_number varchar(10) not null,
	fee int not null,
    foreign key (plate_number) references car(plate_number)
);

CREATE TABLE midsize (
	plate_number varchar(10) not null,
	fee int not null,
    foreign key (plate_number) references car(plate_number)
);

CREATE TABLE fullsize (
	plate_number varchar(10) not null,
	fee int not null,
    foreign key (plate_number) references car(plate_number)
);

CREATE TABLE premium (
	plate_number varchar(10) not null,
	fee int not null,
    foreign key (plate_number) references car(plate_number)
);

CREATE TABLE luxury (
	plate_number varchar(10) not null,
	fee int not null,
    foreign key (plate_number) references car(plate_number)
);

CREATE TABLE client (
	code int not null auto_increment,
    name varchar(50) not null,
    dl varchar(25) not null,
    phone varchar(15) not null,
    primary key (code)
);

CREATE TABLE rent (
	plate varchar(10) not null,
    code int not null,
    start date not null,
    end date not null,
    num_miles int,
    fee_type varchar(10) not null,
    foreign key (plate) REFERENCES car(plate_number),
    foreign key (code) references client(code),
    CHECK (datediff(start, end) <> 0)
);

