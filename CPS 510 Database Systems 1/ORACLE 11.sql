 CREATE TABLE TESTJDBC (NAME varchar(8), NUM NUMBER);
            INSERT INTO TESTJDBC VALUES ('ALIS', 67);
             INSERT INTO TESTJDBC VALUES ('BOB', 345);
            INSERT INTO TESTJDBC VALUES ('loser',685);
           INSERT INTO TESTJDBC VALUES ('loser',685);
 INSERT INTO TESTJDBC VALUES ('losrhema',685);
 INSERT INTO TESTJDBC VALUES ('losrhema',685);
 INSERT INTO TESTJDBC VALUES ('losrhema',685);
 INSERT INTO TESTJDBC VALUES ('losrhema',685);
 COMMIT;
DROP TABLE TESTJDBC;



CREATE TABLE member(
    user_name VARCHAR(30) NOT NULL UNIQUE,
    user_password VARCHAR(30) NOT NULL UNIQUE,
    user_id NUMBER,
    CONSTRAINTS user_pk PRIMARY KEY(user_id)
    )
    
DROP TABLE member;

ALTER TABLE member ADD(
        user_type CHAR(15))

SELECT * FROM member
WHERE user_type = 'Customer';

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
VALUES('Harold','32323232',1000,'Manager');
INSERT INTO member
VALUES('Mark', 's987h', 2000,'Customer');
INSERT INTO member
VALUES('Abel', 'send495', 3000,'Customer');

/** VIEW 1 **/
CREATE VIEW member_public AS
SELECT user_name, user_id, user_type
FROM member;
SELECT * FROM member_public

DROP VIEW member_public;



CREATE TABLE movie(
        movie_id NUMBER, 
        title VARCHAR(100) NOT NULL UNIQUE,
        genre VARCHAR(20) NOT NULL,
        subtitle VARCHAR(20) NOT NULL,
        synopsis VARCHAR(120) NOT NULL,
        CONSTRAINTS movie_pk PRIMARY KEY(movie_id))

    
DROP TABLE movie;

SELECT* FROM movie
WHERE subtitle = 'English'
AND movie_id > 3;

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
VALUES( 6, 'Avengers: Endgame', 'Horror', 'English', 'Agroup of super heroes save the world');
INSERT INTO movie
VALUES( 7, 'Tenet', 'Thriller', 'French', 'A secret agent embarks on a dangerous, time-bending mission to prevent the start of World War III.');
INSERT INTO movie
VALUES( 8, 'Inception', 'Thriller', 'English', 'a professional thief who steals information by infiltrating the subconscious of his targets.');
INSERT INTO movie
VALUES( 9, 'Onward', 'Family', 'English', 'Teenage elf brothers Ian and Barley embark on a magical quest to spend one more day with their late father');


/**VIEW 2**/
CREATE VIEW movie_public AS
SELECT title, genre 
FROM movie;
SELECT * FROM movie_public

DROP VIEW movie_public;


CREATE TABLE customer(
        customer_id NUMBER,
        payment_info VARCHAR(30),
        CONSTRAINTS customer_fk FOREIGN KEY(customer_id) REFERENCES member(user_id)
            )
        
DROP TABLE customer;


SELECT * FROM customer
WHERE payment_info = 'DEBIT' OR
payment_info = 'PAYPAL';


INSERT INTO customer
VALUES(6731,'CREDIT');

INSERT INTO customer
VALUES(2431, 'DEBIT');

INSERT INTO customer
VALUES(9235, 'DEBIT');

INSERT INTO customer
VALUES(6985, 'PAYPAL');

INSERT INTO customer
VALUES(2000, 'DEBIT');

INSERT INTO customer
VALUES(3000, 'PAYPAL');




CREATE TABLE manager(
        manager_id NUMBER,
        manager_discount VARCHAR(20),
        CONSTRAINTS manager_fk FOREIGN KEY(manager_id) REFERENCES member(user_id)
        )
        
        
DROP TABLE manager;

SELECT * FROM manager
where manager_discount != '25%';

INSERT INTO manager
VALUES(2431, '20%');
INSERT INTO manager
VALUES(4523, '25%');
INSERT INTO manager
VALUES(1000, '10%');



CREATE TABLE transaction(
    movie_id NUMBER,
    user_id NUMBER NOT NULL,
    transaction_id NUMBER NOT NULL,
    amount NUMBER NOT NULL,
    manager_discount VARCHAR(20),
    date_paid VARCHAR(30) NOT NULL,
    CONSTRAINTS transaction_pk PRIMARY KEY(transaction_id),
    CONSTRAINTS users_fk FOREIGN KEY(user_id) REFERENCES member(user_id),
    CONSTRAINTS movie_fk FOREIGN KEY(movie_id) REFERENCES movie(movie_id)
    )
    
    
DROP TABLE transaction;

SELECT* FROM transaction
Where amount > 10
ORDER BY amount DESC;

INSERT INTO transaction
VALUES(3,6731,1,7.50,'no discount','OCT.2nd, 2020');

INSERT INTO transaction
VALUES(3,2431,2,11.25,'20%','OCT.4th, 2020');

INSERT INTO transaction
VALUES(1,3732,3,10.58,'No discount','Sept.14th, 2020');

INSERT INTO transaction
VALUES(2,6731,4,15.92,'No discount','AUG.30th, 2020');

INSERT INTO transaction
VALUES(5,3732,5,9.54,'No discount','SEPT.25th, 2020');

INSERT INTO transaction
VALUES(4,6985,6,12.31,'No discount','OCT.8th, 2020');

INSERT INTO transaction
VALUES(8,1000,7,11.25,'10%','OCT.18th, 2020');

INSERT INTO transaction
VALUES(9,4523,8,11.25,'25%','NOV.4th, 2020');

/**VIEW 3**/

CREATE VIEW transaction_public AS
SELECT user_id, date_paid
FROM transaction;
SELECT * FROM transaction_public

DROP VIEW transaction_public;

/***/





CREATE TABLE rating(
        rating_id NUMBER,
        movie_id NUMBER,
        user_id NUMBER NOT NULL,
        comments VARCHAR(100),
        rating_out_of_10 NUMBER,
        CONSTRAINTS rating_pk PRIMARY KEY(rating_id),
        CONSTRAINTS rating_user_fk FOREIGN KEY(user_id) REFERENCES member(user_id),
        CONSTRAINTS rating_movies_fk FOREIGN KEY(movie_id) REFERENCES movie(movie_id)
        )



DROP TABLE rating;

SELECT * FROM rating
where rating_out_of_10 >= 7.5
ORDER BY rating_out_of_10 ASC;

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

INSERT INTO rating
VALUES(6, 1, 2000,'very bad movie!', 1.2);

INSERT INTO rating
VALUES(7, 2, 3000,'very long movie, almost fell asleep!', 1.2);

INSERT INTO rating
VALUES(8, 8, 2000,'mediocre at best!', 6.0);

INSERT INTO rating
VALUES(9, 4, 3000,'really scary movie!', 9.5);

/**VIEW 4**/
CREATE VIEW rating_hidden AS
SELECT movie_id, rating_out_of_10, comments
FROM rating;
SELECT * FROM rating_hidden

DROP VIEW rating_hidden;

/***/


CREATE TABLE purchase(
        purchase_id NUMBER,
        transaction_id NUMBER,
        CONSTRAINTS purchase_pk PRIMARY KEY(purchase_id),
        CONSTRAINTS p_transaction_fk FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id))



DROP TABLE purchase;

SELECT * FROM purchase
where transaction_id > 3;

INSERT INTO purchase
VALUES(1,3);

INSERT INTO purchase
VALUES(2,4);


CREATE TABLE rental(
        rental_id NUMBER,
        rental_time VARCHAR(20),
        transaction_id NUMBER,
        CONSTRAINTS rental_pk PRIMARY KEY(rental_id),
        CONSTRAINTS r_transaction_fk FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id))



DROP TABLE rental;

SELECT * FROM rental
WHERE rental_time = '14 days'
AND transaction_id > 4;

INSERT INTO rental
VALUES(1,'14 days',2);

INSERT INTO rental
VALUES(2,'1 week',1);

INSERT INTO rental
VALUES(3,'14 days',5);

INSERT INTO rental
VALUES(4,'10 days',6);

INSERT INTO rental
VALUES(5,'14 days',7);

INSERT INTO rental
VALUES(6,'1 week',8);



/** join queries**/

SELECT rental.rental_id, transaction.date_paid, rental.rental_time
from rental, transaction
WHERE rental.transaction_id = transaction.transaction_id
and rental.rental_id >=3 
ORDER BY transaction.date_paid DESC; 

SELECT member.user_name, member.user_type, transaction.amount, transaction.date_paid
from member, transaction
WHERE member.user_id = transaction.user_id
and amount < 13
ORDER BY amount ASC;


SELECT member.user_name, rating.comments, rating.rating_out_of_10, movie.title
from member, movie, rating
WHERE member.user_id = rating.user_id
and rating.movie_id = movie.movie_id
and movie.subtitle = 'English'
ORDER BY movie.title ASC;




/** advance queries **/

SELECT 'The count for transaction made is', COUNT(transaction.amount)
FROM transaction;

(SELECT movie.title, movie.genre FROM movie)
MINUS 
(SELECT movie.title, movie.genre FROM movie
where subtitle = 'English');


(SELECT* from transaction
where transaction.amount < 10)
UNION
SELECT* FROM transaction 
WHERE transaction.manager_discount = 'No discount'; 


SELECT transaction_id, date_paid, amount
FROM transaction
WHERE EXISTS
(SELECT movie_id
FROM movie
where movie.movie_id = transaction.movie_id
and movie.subtitle = 'English');

SELECT transaction_id, AVG(amount) from transaction
GROUP BY transaction_id  
HAVING AVG(transaction.amount) > (SELECT AVG(amount) FROM transaction);

DROP TABLE movie;
DROP TABLE member;
DROP TABLE customer;
DROP TABLE manager;
DROP TABLE transaction;
DROP TABLE rating;
DROP TABLE purchase;
DROP TABLE rental;