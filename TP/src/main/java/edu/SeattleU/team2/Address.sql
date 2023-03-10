// Baran Onalan, James Robinson, Swetha Gupta, Troy Ying-Chu Chen
// CPSC 5200 - Team 2
// 2/13/2023

CREATE DATABASE TeamProject;
USE TeamProject;
CREATE TABLE Address (
	ID int NOT NULL AUTO_INCREMENT,
	country varchar(255),
	recipient varchar(255),
    streetAddress varchar(255),
    postalCode varchar(255),
    city_town_locality varchar(255),
    state varchar(255),
    PRIMARY KEY (ID)
);

SELECT * FROM Address;

INSERT INTO Address (country, recipient, streetAddress, postalCode, city_town_locality, state)
VALUES ('USA', 'Baran Onalan', '555 5th Street', '55555', 'Kirkland', 'WA');

INSERT INTO Address (country, recipient, streetAddress, postalCode, city_town_locality, state)
VALUES ('USA', 'Jane Doe', '99 9th Street', '90000', 'Seattle', 'WA');

SELECT ID, country, recipient, streetAddress, postalCode, city_town_locality, state FROM Address WHERE ID = 1; 