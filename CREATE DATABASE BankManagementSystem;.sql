CREATE DATABASE BankManagementSystem;
SHOW DATABASES;
CREATE TABLE accounts (
  accountNumber INT PRIMARY KEY,
  accountHolderName VARCHAR(100) NOT NULL,
  balance DOUBLE DEFAULT 0.0,
  cibilScore INT DEFAULT 700
);

