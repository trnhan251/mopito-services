-- CREATE TABLE account(
--    user_id serial PRIMARY KEY,
--    username VARCHAR (50) UNIQUE NOT NULL,
--    password VARCHAR (50) NOT NULL,
--    email VARCHAR (355) UNIQUE NOT NULL,
--    created_on TIMESTAMP NOT NULL,
--    last_login TIMESTAMP
-- );

CREATE TABLE `users` (
    `id` serial PRIMARY KEY,
    `username` VARCHAR(50) UNIQUE NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `email` VARCHAR(200) UNIQUE NOT NULL,
    `first_name` VARCHAR(50) NOT NULL,
    `last_name` VARCHAR(50) NOT NULL,
    `role` VARCHAR(50) NOT NULL,
    `created_on` TIMESTAMP  NOT NULL,
);

