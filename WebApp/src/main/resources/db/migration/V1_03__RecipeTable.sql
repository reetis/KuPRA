CREATE SEQUENCE recipe_seq;
CREATE TABLE recipe (
  recipe_id           INT PRIMARY KEY DEFAULT nextval('recipe_seq'),
  author              VARCHAR(24) NOT NULL REFERENCES "user"(user_id),
  name                VARCHAR(64) NOT NULL,
  cooking_time         INT         NOT NULL,
  servings             INT         NOT NULL,
  public_access        BOOLEAN     NOT NULL,
  description         VARCHAR(256),
  process_description VARCHAR(512)
);