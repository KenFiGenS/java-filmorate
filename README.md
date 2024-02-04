# java-filmorate
Template repository for Filmorate project.

Схема базы данных:

![Filmorate](https://github.com/KenFiGenS/java-filmorate/assets/131599605/5df8ad49-940f-4a9c-b7a6-6f0b039ccfe9)


Примеры запросов к БД:
1. Топ фильмов

       SELECT f.film_name
       FROM film AS f
       WHERE film_id IN (SELECT film_id
                         FROM Likes
                         GROUP BY film_id
                         ORDER BY COUNT(user_id) DESC
                         LIMIT 10);

2. Список общих друзей

       SELECT fl.user_id
       FROM user AS u
       INNER JOIN friendship_status AS fs ON u.user_id = fs.user_id
       INNER JOIN friends_list AS fl ON fl.friendship_status = 'confirmed'
       WHERE u.user_id = 1
       OR u.user_id = 2
       GROUP BY fl.user_id
       HAVING COUNT(fl.user_i) > 1;
