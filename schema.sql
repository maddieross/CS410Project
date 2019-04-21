DROP database IF EXISTS Project1;
create database Project1;
use Project1;

Drop table if exists Model;
CREATE TABLE Model (
    "unique_id" INT NOT NULL,
    "name" VARCHAR(24) NOT NULL,
    "make" VARCHAR(24) NOT NULL,
    "engine_size" VARCHAR(10) NOT NULL,
    "year" INT NOT NULL,
    PRIMARY KEY("unique_id")
)

Drop table if exists Car;
CREATE TABLE Car (
    "plate_num" VARCHAR(8) NOT NULL,
    "num_miles" INT NOT NULL,
    "status" VARCHAR(9) NOT NULL CHECK ("status" IN ('rented', 'available', 'servicing')),
    "model_unique_id" INT NOT NULL,
    PRIMARY KEY("plate_num"),
    FOREIGN KEY("model_unique_id") REFERENCES "Model"("unique_id")
);

Drop table if exists Client;
CREATE TABLE Client (
    "code" INT NOT NULL,
    "name" VARCHAR(30) NOT NULL,
    "license_num" VARCHAR(8) NOT NULL,
    "phone_num" INT NOT NULL,
    PRIMARY KEY("code")
);

Drop table if exists Rent;
CREATE TABLE Rent (
    "plate_num" VARCHAR(8) NOT NULL,
    "code" INT NOT NULL,
    "start" DATE NOT NULL,
    "end" DATE NOT NULL,
    "fee" INT NOT NULL,
    "num_miles" INT NOT NULL,
    
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num"),
    FOREIGN KEY("code") REFERENCES "Client"("code")
);

Drop table if exists Economy;
CREATE TABLE Economy (
    "plate_num" VARCHAR(8) NOT NULL,
    "fee" INT NOT NULL,
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num")
);

Drop table if exists Compact;
CREATE TABLE Compact (
    "plate_num" VARCHAR(8) NOT NULL,
    "fee" INT NOT NULL,
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num")
);

Drop table if exists MidSize;
CREATE TABLE MidSize (
    "plate_num" VARCHAR(8) NOT NULL,
    "fee" INT NOT NULL,
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num")
);

Drop table if exists FullSize;
CREATE TABLE FullSize (
    "plate_num" VARCHAR(8) NOT NULL,
    "fee" INT NOT NULL,
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num")
);

Drop table if exists Premium;
CREATE TABLE Premium (
    "plate_num" VARCHAR(8) NOT NULL,
    "fee" INT NOT NULL,
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num")
);

Drop table if exists Luxury;
CREATE TABLE Luxury (
    "plate_num" VARCHAR(8) NOT NULL,
    "fee" INT NOT NULL,
    FOREIGN KEY("plate_num") REFERENCES "Car"("plate_num")
);


