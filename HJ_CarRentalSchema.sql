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
    type varchar(15) not null,
    fee int,
    primary key (plate_number),
    foreign key (model) REFERENCES model(model_id),
    CHECK (status='Rented' OR status='Available' OR status='Servicing'),
    CHECK (type='Economy' OR type='Compact' OR type='Midsize' OR type='Fullsize' OR type='Premium' OR type='Luxury')
);


CREATE TABLE client (
	code int not null auto_increment,
    name varchar(50) not null,
    dl varchar(25) not null,
    phone varchar(15) not null,
    primary key (code)
);

CREATE TABLE rent (
	rental_id int not null auto_increment,
	plate varchar(10) not null,
    code int not null,
    start date not null,
    end date not null,
    num_miles int,
    fee_type varchar(10) not null,
    primary key (rental_id),
    foreign key (plate) REFERENCES car(plate_number),
    foreign key (code) references client(code)
);

