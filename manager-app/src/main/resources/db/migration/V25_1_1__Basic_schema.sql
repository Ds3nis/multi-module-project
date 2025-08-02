CREATE SCHEMA IF NOT EXISTS user_management;

CREATE TABLE IF NOT EXISTS user_management.t_user (
                                                      id SERIAL PRIMARY KEY,
                                                      c_username VARCHAR(255) NOT NULL UNIQUE CHECK (length(trim(c_username)) > 0),
    c_password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS user_management.t_authority (
                                                           id SERIAL PRIMARY KEY,
                                                           c_authority VARCHAR(255) NOT NULL UNIQUE CHECK (length(trim(c_authority)) > 0)
    );

CREATE TABLE IF NOT EXISTS user_management.t_user_authority (
                                                                id SERIAL PRIMARY KEY,
                                                                user_id INTEGER NOT NULL REFERENCES user_management.t_user(id),
    authority_id INTEGER NOT NULL REFERENCES user_management.t_authority(id),
    CONSTRAINT uk_user_authority UNIQUE (user_id, authority_id)
    );
