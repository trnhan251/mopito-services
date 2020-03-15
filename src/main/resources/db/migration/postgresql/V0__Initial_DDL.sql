CREATE TABLE users (
    id serial PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(200) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_on TIMESTAMP  NOT NULL
);

CREATE TABLE collections (
  id BIGINT PRIMARY KEY,
  topic VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  user_id BIGINT NOT NULL,
  created_date DATE
);

CREATE TABLE images (
  id BIGINT PRIMARY KEY,
  url VARCHAR NOT NULL,
  description VARCHAR,
  user_id BIGINT NOT NULL,
  collection_id bigint,
  created_date DATE
);

ALTER TABLE collections ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE images ADD FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE images ADD FOREIGN KEY (collection_id) REFERENCES collections (id);

