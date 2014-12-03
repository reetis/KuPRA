CREATE TABLE fridge (
  id            int PRIMARY KEY,
  user VARCHAR(64) REFERENCES "user" (user_id) NOT NULL,
  product_id    int REFERENCES product (id) NOT NULL,
  amount        float
);
CREATE SEQUENCE fridge_seq;