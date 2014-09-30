CREATE TABLE user (
  login         varchar(24) PRIMARY KEY,
  password_hash varchar(64) NOT NULL,
  name          varchar(64) NOT NULL,
  surname       varchar(64) NOT NULL,
  email         varchar(64) NOT NULL
);