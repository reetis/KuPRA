CREATE SEQUENCE recipe_image_seq;
CREATE TABLE recipe_image (
  id            int PRIMARY KEY DEFAULT nextval('recipe_image_seq'),
  recipe_id     int NOT NULL,
  image_url    VARCHAR(512),
  thumb_url    VARCHAR(512),

  FOREIGN KEY (recipe_id) REFERENCES recipe (recipe_id)
);