-- Lag database
CREATE DATABASE tododb;

-- Koble til databasen (kjør denne i psql etter å ha laget databasen)
\c tododb;

-- Lag schema (f.eks. 'todo')
CREATE SCHEMA todo;

-- Lag tabell i schema 'todo'
CREATE TABLE todo.task (
                           id SERIAL PRIMARY KEY,
                           description VARCHAR(255) NOT NULL,
                           completed BOOLEAN NOT NULL DEFAULT FALSE
);
