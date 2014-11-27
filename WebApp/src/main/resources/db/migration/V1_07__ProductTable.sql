CREATE TABLE product (
  id            int PRIMARY KEY,
  name                VARCHAR(64) NOT NULL,
  selected_unit        int,
  description         VARCHAR(256),
);
CREATE SEQUENCE product_seq;