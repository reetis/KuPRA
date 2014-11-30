CREATE TABLE recipe (
  recipe_id           INT PRIMARY KEY,
  author              VARCHAR(24) NOT NULL,
  name                VARCHAR(64) NOT NULL,
  cooking_time         INT         NOT NULL,
  servings             INT         NOT NULL,
  public_access        BOOLEAN     NOT NULL,
  description         VARCHAR(256),
  process_description VARCHAR(512)
);
CREATE SEQUENCE recipe_seq;