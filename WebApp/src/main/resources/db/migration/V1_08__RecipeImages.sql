CREATE TABLE recipe_image (
  id            int PRIMARY KEY,
  recipe_id     int NOT NULL,
  image_url    VARCHAR(512),
  thumb_url    VARCHAR(512),

  FOREIGN KEY (recipe_id) REFERENCES recipe (recipe_id)
);
CREATE SEQUENCE recipe_image_seq;