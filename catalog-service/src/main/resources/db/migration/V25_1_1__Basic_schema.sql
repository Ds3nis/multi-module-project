CREATE SCHEMA IF NOT EXISTS catalog;

CREATE TABLE catalog.t_product (
    id SERIAL PRIMARY KEY,
    c_title VARCHAR(50) NOT NULL CHECK (length(trim(c_title)) >= 3),
    c_details VARCHAR(1000)
);


-- CREATE TABLE catalog.t_product (
--     id INTEGER AUTO_INCREMENT PRIMARY KEY,
--     c_title VARCHAR(50) NOT NULL CHECK (CHAR_LENGTH(TRIM(c_title)) >= 3),
--     c_details VARCHAR(1000)
-- );
