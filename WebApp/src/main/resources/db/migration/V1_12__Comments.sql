CREATE SEQUENCE comment_seq;
CREATE TABLE comment (
  id        INT PRIMARY KEY DEFAULT nextval('comment_seq'),
  recipe_id INT          NOT NULL REFERENCES recipe(recipe_id),
  author VARCHAR(24) NOT NULL REFERENCES "user" (user_id),
  comment   VARCHAR(256) NOT NULL
);
