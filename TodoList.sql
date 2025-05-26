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


INSERT INTO todo.task (description, completed) VALUES
                                                   ('Buy groceries', FALSE),
                                                   ('Call mom', TRUE),
                                                   ('Finish Java assignment', FALSE),
                                                   ('Pay electricity bill', TRUE),
                                                   ('Book dentist appointment', FALSE),
                                                   ('Clean the house', FALSE),
                                                   ('Prepare dinner', TRUE),
                                                   ('Walk the dog', FALSE),
                                                   ('Read 30 pages of a book', FALSE),
                                                   ('Reply to work emails', TRUE);
