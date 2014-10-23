CREATE TABLE recipe (
  recipe_id           INT PRIMARY KEY,
  name                VARCHAR(64) NOT NULL,
  cookingTime         INT         NOT NULL,
  publicAccess        BOOL        NOT NULL,
  description         VARCHAR(256),
  process_description VARCHAR(512)
);
