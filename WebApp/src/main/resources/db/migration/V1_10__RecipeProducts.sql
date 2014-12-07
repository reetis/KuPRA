CREATE SEQUENCE recipe_product_seq START WITH 1;
CREATE TABLE recipe_product (
  id INT PRIMARY KEY DEFAULT nextval('recipe_product_seq'),
  recipe_id  INT NOT NULL REFERENCES recipe (recipe_id),
  product_id INT NOT NULL REFERENCES product (id),
  quantity   FLOAT
);