CREATE TABLE recipe_product (
  id         INT PRIMARY KEY,
  recipe_id  INT NOT NULL REFERENCES recipe (recipe_id),
  product_id INT NOT NULL REFERENCES product (id),
  quantity   FLOAT
);
CREATE SEQUENCE recipe_product_seq;