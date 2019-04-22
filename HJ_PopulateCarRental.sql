/* ECONOMY */
INSERT INTO model values (0, 'Mirage', 'Mitsubishi', '1.2', '2019');
INSERT INTO model values (0, 'Fit', 'Honda', '1.5', '2019');
INSERT INTO model values (0, 'Soul', 'Kia', '1.6', '2018');

/* COMPACT */
INSERT INTO model values (0, 'Versa', 'Nissan', '1.6', '2019');
INSERT INTO model values (0, 'Accent', 'Hyundai', '1.6', '2019');
INSERT INTO model values (0, 'Sentra', 'Nissan', '1.8', '2018');

/* MID SIZE */
INSERT INTO model values (0, 'Fiesta', 'Ford', '1.6', '2019');
INSERT INTO model values (0, 'Focus', 'Ford', '1.0', '2018');
INSERT INTO model values (0, 'Edge', 'Ford', '2.0', '2019');

/* FULL SIZE */
INSERT INTO model values (0, 'Escape', 'Ford', '2.0', '2019');
INSERT INTO model values (0, 'Elantra', 'Hyundai', '2.0', '2019');
INSERT INTO model values (0, 'Civic', 'Honda', '2.0', '2019');

/* PREMIUM */
INSERT INTO model values (0, 'Maxima', 'Nissan', '3.5', '2018');
INSERT INTO model values (0, 'Mazda3', 'Mazda', '2.5', '2018');
INSERT INTO model values (0, 'Camry', 'Toyota', '2.5', '2018');

/* LUXURY CARS */
INSERT INTO model values (0, '3 Series', 'BMW', '2.0', '2019');
INSERT INTO model values (0, 'A4', 'Audi', '2.0', '2019');
INSERT INTO model values (0, 'CTS', 'Cadillac', '2.0', '2019');
INSERT INTO model values (0, 'Model S', 'Tesla', '0', '2019');
INSERT INTO model values (0, 'Mustang', 'Ford', '2.3', '2018');


/*CARS ADDED TO POOL */
INSERT INTO car values ('1A12345', 15000, 'Available', 1);
INSERT INTO car values ('1A54321', 15000, 'Available', 2);
INSERT INTO car values ('1AB4123', 15000, 'Available', 3);
INSERT INTO car values ('1AB4321', 15000, 'Available', 4);
INSERT INTO car values ('1A3E914', 15000, 'Available', 5);
INSERT INTO car values ('1A9S931', 15000, 'Available', 6);
INSERT INTO car values ('1A02935', 15000, 'Available', 7);
INSERT INTO car values ('1AD0193', 15000, 'Available', 8);
INSERT INTO car values ('1AN0392', 15000, 'Available', 9);
INSERT INTO car values ('1AS1834', 15000, 'Available', 10);
INSERT INTO car values ('1AP8492', 15000, 'Rented', 11);
INSERT INTO car values ('1AW9301', 15000, 'Servicing', 12);
INSERT INTO car values ('1A492D1', 15000, 'Available', 13);
INSERT INTO car values ('1ACS910', 15000, 'Available', 14);
INSERT INTO car values ('1AOE934', 15000, 'Available', 15);
INSERT INTO car values ('1AR940S2', 15000, 'Available', 16);
INSERT INTO car values ('1AN3V91', 15000, 'Available', 17);
INSERT INTO car values ('1APW961', 15000, 'Available', 18);
INSERT INTO car values ('1AW9320', 15000, 'Available', 19);
INSERT INTO car values ('1A9402S', 15000, 'Available', 20);

/* ADD CARS TO CORRECT CATEGORIES*/
INSERT INTO economy values ('1AN0392', 50);
INSERT INTO economy values ('1AS1834', 50);
INSERT INTO economy values ('1AOE934', 50);

INSERT INTO compact values ('1AP8492', 65);
INSERT INTO compact values ('1AW9301', 65);
INSERT INTO compact values ('1AR940S2', 65);

INSERT INTO midsize values ('1A12345', 75);
INSERT INTO midsize values ('1A54321', 75);
INSERT INTO midsize values ('1AB4123', 75);

INSERT INTO fullsize values ('1AB4321', 100);
INSERT INTO fullsize values ('1A492D1', 100);
INSERT INTO fullsize values ('1ACS910', 100);

INSERT INTO premium values ('1AN3V91', 150);
INSERT INTO premium values ('1APW961', 150);
INSERT INTO premium values ('1AW9320', 150);

INSERT INTO luxury values ('1A3E914', 200);
INSERT INTO luxury values ('1A9S931', 200);
INSERT INTO luxury values ('1A02935', 200);
INSERT INTO luxury values ('1AD0193', 200);
INSERT INTO luxury values ('1A9402S', 200);

/* ADD SOME CLIENTS */
INSERT INTO client values (0, 'John Doe', 'RD829518E', '2087461832');
INSERT INTO client values (0, 'Jane Doe', 'CS748195D', '2081234567');
INSERT INTO client values (0, 'John Smith', 'LS857281P', '2089369023');
INSERT INTO client values (0, 'Jane Johnson', 'SP395287V', '2089492132');

/* ADD SAMPLE RENTALS */
INSERT INTO rent values ('1A02935', 1, '2019-04-21', '2019-04-21', 1000, 'economy');
INSERT INTO rent values ('1AP8492', 4, '2019-04-21', '2019-04-26', 1500, 'premium');



