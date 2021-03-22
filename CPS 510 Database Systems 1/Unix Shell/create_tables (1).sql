CREATE TABLE member(
    user_name VARCHAR(30) NOT NULL UNIQUE,
    user_password VARCHAR(30) NOT NULL UNIQUE,
    user_id NUMBER,
    user_type CHAR(15),
    CONSTRAINTS user_pk PRIMARY KEY(user_id)
    );
    
CREATE TABLE movie(
        movie_id NUMBER, 
        title VARCHAR(100) NOT NULL UNIQUE,
        genre VARCHAR(20) NOT NULL,
        subtitle VARCHAR(20) NOT NULL,
        synopsis VARCHAR(1000) NOT NULL,
        CONSTRAINTS movie_pk PRIMARY KEY(movie_id));
        
CREATE TABLE customer(
        customer_id NUMBER,
        payment_info VARCHAR(30),
        CONSTRAINTS customer_fk FOREIGN KEY(customer_id) REFERENCES member(user_id)
            );
            
CREATE TABLE manager(
        manager_id NUMBER,
        manager_discount VARCHAR(20),
        CONSTRAINTS manager_fk FOREIGN KEY(manager_id) REFERENCES member(user_id)
        );
        
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
    );
    
CREATE TABLE rating(
        rating_id NUMBER,
        movie_id NUMBER,
        user_id NUMBER NOT NULL,
        comments VARCHAR(100),
        rating_out_of_10 NUMBER,
        CONSTRAINTS rating_pk PRIMARY KEY(rating_id),
        CONSTRAINTS rating_user_fk FOREIGN KEY(user_id) REFERENCES member(user_id),
        CONSTRAINTS rating_movies_fk FOREIGN KEY(movie_id) REFERENCES movie(movie_id)
        );
        
CREATE TABLE purchase(
        purchase_id NUMBER,
        transaction_id NUMBER,
        CONSTRAINTS purchase_pk PRIMARY KEY(purchase_id),
        CONSTRAINTS p_transaction_fk FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id));
        
CREATE TABLE rental(
        rental_id NUMBER,
        rental_time VARCHAR(20),
        transaction_id NUMBER,
        CONSTRAINTS rental_pk PRIMARY KEY(rental_id),
        CONSTRAINTS r_transaction_fk FOREIGN KEY(transaction_id) REFERENCES transaction(transaction_id));
