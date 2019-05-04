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
INSERT INTO car values ('1A12345', 15000, 'Available', 1, 'Midsize', 75);
INSERT INTO car values ('1A54321', 15000, 'Available', 2, 'Midsize', 75);
INSERT INTO car values ('1AB4123', 15000, 'Available', 3, 'Midsize', 75);
INSERT INTO car values ('1AB4321', 15000, 'Available', 4, 'Fullsize', 100);
INSERT INTO car values ('1A3E914', 15000, 'Available', 5, 'Luxury', 200);
INSERT INTO car values ('1A9S931', 15000, 'Available', 6, 'Luxury', 200);
INSERT INTO car values ('1A02935', 15000, 'Available', 7, 'Luxury', 200);
INSERT INTO car values ('1AD0193', 15000, 'Available', 8, 'Luxury', 200);
INSERT INTO car values ('1AN0392', 15000, 'Available', 9, 'Economy', 50);
INSERT INTO car values ('1AS1834', 15000, 'Available', 10, 'Economy', 50);
INSERT INTO car values ('1AP8492', 15000, 'Rented', 11, 'Compact', 65);
INSERT INTO car values ('1AW9301', 15000, 'Servicing', 12, 'Compact', 65);
INSERT INTO car values ('1A492D1', 15000, 'Available', 13, 'Fullsize', 100);
INSERT INTO car values ('1ACS910', 15000, 'Available', 14, 'Fullsize', 100);
INSERT INTO car values ('1AOE934', 15000, 'Available', 15, 'Economy', 50);
INSERT INTO car values ('1AR90S2', 15000, 'Available', 16, 'Compact', 65);
INSERT INTO car values ('1AN3V91', 15000, 'Available', 17, 'Premium', 150);
INSERT INTO car values ('1APW961', 15000, 'Available', 18, 'Premium', 150);
INSERT INTO car values ('1AW9320', 15000, 'Available', 19, 'Premium', 150);
INSERT INTO car values ('1A9402S', 15000, 'Available', 20, 'Luxury', 200);


/* ADD SOME CLIENTS */ 
INSERT INTO client values (0, 'John Doe', 'RD829518E', '2087461832');
INSERT INTO client values (0, 'Jane Doe', 'CS748195D', '2081234567');
INSERT INTO client values (0, 'John Smith', 'LS857281P', '2089369023');
INSERT INTO client values (0, 'Jane Johnson', 'SP395287V', '2089492132');


/* ADD SAMPLE RENTALS */
INSERT INTO rent values (0, '1A02935', 1, '2019-04-21', '2019-04-21', 1000, 'luxury');
INSERT INTO rent values (0, '1AP8492', 4, '2019-04-21', '2019-04-26', 1500, 'compact');



