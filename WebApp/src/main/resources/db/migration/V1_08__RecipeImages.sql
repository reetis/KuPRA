CREATE SEQUENCE recipe_image_seq START WITH 1;
CREATE TABLE recipe_image (
  id        INT PRIMARY KEY DEFAULT nextval('recipe_image_seq'),
  recipe_id INT NOT NULL,
  image_url VARCHAR(512),
  thumb_url VARCHAR(512),

  FOREIGN KEY (recipe_id) REFERENCES recipe (recipe_id)
);