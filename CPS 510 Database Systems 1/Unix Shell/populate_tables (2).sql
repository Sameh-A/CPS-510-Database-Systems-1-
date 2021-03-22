INSERT INTO member
VALUES('Artheek', 'koolkid123', 6731,'Customer');
INSERT INTO member
VALUES('Sameh',  'manager123',3732,'Manager');
INSERT INTO member
VALUES('Colin', 'hello1234', 2431,'Customer');
INSERT INTO member
VALUES('Ord','PDFCDF123',4523,'Manager');
INSERT INTO member
VALUES('Kevin', 'qwerty128', 9235,'Customer');
INSERT INTO member
VALUES('Manraj', 'shorty148', 6985,'Customer');
INSERT INTO member
VALUES('Harold', '32323232', 1000, 'Manager');
INSERT INTO member
VALUES('Mark', 's987h', 2000, 'Customer');
INSERT INTO member
VALUES('ABel', 'send495', 3000, 'Customer');

INSERT INTO movie
VALUES( 1, 'Yugi-oh', 'Action', 'English', 'Using cards to beat opponents');
INSERT INTO movie
VALUES( 2, 'Joker', 'Thriller', 'French', 'History of Batmans popular villain');
INSERT INTO movie
VALUES( 3, 'Paper Towns', 'Romance', 'Swedish', 'Guy tries to go find his crush');
INSERT INTO movie
VALUES( 4, 'Conjuring', 'Horror', 'English', 'A ghost haunts a family requiring special exorcist');
INSERT INTO movie
VALUES( 5, 'IT', 'Horror', 'Spanish', 'Scary clown terrorizes kids in a neighborhood');
INSERT INTO movie
VALUES( 6, 'Avengers: Endgame', 'Horror', 'English', 'A group of super heroes save the world');

INSERT INTO customer
VALUES(6731,'CREDIT');

INSERT INTO customer
VALUES(2431, 'DEBIT');

INSERT INTO customer
VALUES(9235, 'DEBIT');

INSERT INTO customer
VALUES(6985, 'PAYPAL');

INSERT INTO manager
VALUES(2431, '20%');
INSERT INTO manager
VALUES(4523, '25%');
INSERT INTO manager
VALUES(1000, '10%');

INSERT INTO transaction
VALUES(3,6731,1,7.50,'no discount','OCT.2nd, 2020');

INSERT INTO transaction
VALUES(3,2431,2,11.25,'20%','OCT.4th, 2020');

INSERT INTO transaction
VALUES(1,3732,3,10.58,'No discount','Sept.14th, 2020');

INSERT INTO transaction
VALUES(2,6731,4,15.92,'No discount','August.30th, 2020');

INSERT INTO transaction
VALUES(5,3732,5,9.54,'No discount','Sept.25th, 2020');

INSERT INTO transaction
VALUES(4,6985,6,12.31,'No discount','OCT.8th, 2020');

INSERT INTO rating
VALUES(1, 2, 6731,'Really good movie!', 8.3);

INSERT INTO rating
VALUES(2, 2, 2431,'The acting was good', 7.5);

INSERT INTO rating
VALUES(3, 4, 6731,'It was pretty scary!', 6);

INSERT INTO rating
VALUES(4, 1, 3732,'AMAZING PLOT and action was great!', 9.8);

INSERT INTO rating
VALUES(5, 5, 3732,'It could have been scarier!', 5.8);

INSERT INTO purchase
VALUES(1,3);

INSERT INTO purchase
VALUES(2,4);

INSERT INTO purchase
VALUES(3,5);

INSERT INTO purchase
VALUES(4,6);

INSERT INTO rental
VALUES(1,'14 days',2);

INSERT INTO rental
VALUES(2,'1 week',1);

INSERT INTO rental
VALUES(3,'14 days',5);

INSERT INTO rental
VALUES(4,'14 days',6);
