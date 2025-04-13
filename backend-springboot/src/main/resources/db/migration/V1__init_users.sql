CREATE TABLE users
(
    id        SERIAL PRIMARY KEY,
    full_name VARCHAR(100),
    email     VARCHAR(100) UNIQUE NOT NULL,
    password  TEXT,
    role      VARCHAR(20)
);
