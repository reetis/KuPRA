CREATE TABLE product (
  id          INT PRIMARY KEY,
  name        VARCHAR(64) NOT NULL,
  unit        INT REFERENCES unit (id),
  description VARCHAR(256)
);
CREATE SEQUENCE product_seq;