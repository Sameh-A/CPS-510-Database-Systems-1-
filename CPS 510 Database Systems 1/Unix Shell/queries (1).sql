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