CREATE DATABASE STACK_TRADING_db;
use STACK_TRADING_db;
CREATE TABLE USERS_LOGGINS(
	username VARCHAR(35) NOT NULL,
    password VARCHAR(20) NOT NULL,
    PRIMARY KEY (username, password)
    );
    
CREATE TABLE MARKET_DATA(
	stocks_name VARCHAR(30),
    price DOUBLE,
    inc_dec_rate DOUBLE DEFAULT 0.00
    );  

INSERT INTO MARKET_DATA	
	(stocks_name,price)
	VALUES
    ("microsoft",1000.0),
    ("apple",1100.0),
    ("cocacola",250.0),
    ("samsung",1050.0),
    ("gold",2020),
    ("amazon",800);
    
CREATE TABLE USERS_DATA(
	name VARCHAR(35) NOT NULL,
    password VARCHAR(20) NOT NULL,
    balance double NOT NULL,
    microsoft INT DEFAULT 0,
    apple INT DEFAULT 0,
    cocacola INT DEFAULT 0,
    samsung INT DEFAULT 0,
    gold INT DEFAULT 0,
    amazon INT DEFAULT 0,
    PRIMARY KEY (name, password)
    );    