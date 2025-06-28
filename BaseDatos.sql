-- Create sequence for person_id
CREATE SEQUENCE person_id_seq START 1;

-- Create person table
CREATE TABLE person (
    person_id BIGINT PRIMARY KEY DEFAULT nextval('person_id_seq'),
    identification VARCHAR(13) NOT NULL CHECK (length(identification) BETWEEN 10 AND 13),
    name VARCHAR(50) NOT NULL CHECK (length(name) BETWEEN 1 AND 50),
    gender VARCHAR NOT NULL,
    age INTEGER NOT NULL CHECK (age BETWEEN 0 AND 100),
    address VARCHAR(100),
    phone VARCHAR(10)
);

-- Create sequence for customer_id
CREATE SEQUENCE customer_id_seq START 1;

-- Create customer table
CREATE TABLE customer (
    customer_id BIGINT PRIMARY KEY DEFAULT nextval('customer_id_seq'),
    password VARCHAR(30) CHECK (length(password) BETWEEN 1 AND 30),
    status BOOLEAN,
    person_id BIGINT REFERENCES person(person_id)
);

-- Create sequence for account_id
CREATE SEQUENCE account_id_seq START 1;

-- Create account table
CREATE TABLE account (
    account_id BIGINT PRIMARY KEY DEFAULT nextval('account_id_seq'),
    number VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    initial_balance DECIMAL NOT NULL,
    status BOOLEAN,
    customer_id BIGINT REFERENCES customer(customer_id)
);

-- Create sequence for movement_id
CREATE SEQUENCE movement_id_seq START 1;

-- Create movement table
CREATE TABLE movement (
    movement_id BIGINT PRIMARY KEY DEFAULT nextval('movement_id_seq'),
    date DATE NOT NULL,
    type VARCHAR NOT NULL,
    value DECIMAL NOT NULL,
    balance DECIMAL NOT NULL,
    status BOOLEAN,
    account_id BIGINT REFERENCES account(account_id)
);