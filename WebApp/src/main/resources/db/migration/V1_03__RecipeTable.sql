CREATE TABLE recipe (
  recipe_id           INT PRIMARY KEY,
  name                VARCHAR(64) NOT NULL,
  cooking_time         INT         NOT NULL,
  public_access        BOOLEAN     NOT NULL,
  description         VARCHAR(256),
  process_description VARCHAR(512)
);
