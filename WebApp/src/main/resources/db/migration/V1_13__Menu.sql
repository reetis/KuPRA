CREATE SEQUENCE menu_seq START WITH 1;
CREATE TABLE menu (
  id        INT PRIMARY KEY DEFAULT nextval('menu_seq'),
  user_id   VARCHAR(64) REFERENCES "user" (user_id) NOT NULL,
  date_time TIMESTAMP                               NOT NULL,
  recipe_id INT                                     NOT NULL
);