CREATE SEQUENCE fridge_item_seq START WITH 1;
CREATE TABLE fridge_item (
  id         INT PRIMARY KEY DEFAULT nextval('fridge_item_seq'),
  user_id    VARCHAR(64) REFERENCES "user" (user_id) NOT NULL,
  product_id INT REFERENCES product (id)             NOT NULL,
  amount     NUMERIC(10, 2)
);