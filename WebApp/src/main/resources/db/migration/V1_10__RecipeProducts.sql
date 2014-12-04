CREATE TABLE recipe_product(
  id            int PRIMARY KEY,
  recipe_id     int NOT NULL REFERENCES recipe(recipe_id),
  product_id    int NOT NULL REFERENCES product(id),
  quantity      float
);
CREATE SEQUENCE recipe_product_seq;