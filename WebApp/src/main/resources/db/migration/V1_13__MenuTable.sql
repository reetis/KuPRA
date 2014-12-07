CREATE SEQUENCE menu_seq START WITH 1;
CREATE TABLE menu (
  id INT PRIMARY KEY DEFAULT nextval('menu_seq'),
  recipe_id  INT NOT NULL REFERENCES recipe (recipe_id),
  menu_date DATE,
  menu_time TIMESTAMP,
);