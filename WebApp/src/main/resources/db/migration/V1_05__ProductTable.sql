CREATE SEQUENCE product_seq START WITH 1;
CREATE TABLE product (
  id INT PRIMARY KEY DEFAULT nextval('product_seq'),
  name        VARCHAR(64) NOT NULL,
  unit        INT REFERENCES unit (id),
  description TEXT
);