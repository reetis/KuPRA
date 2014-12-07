CREATE SEQUENCE menu_seq START WITH 1;
CREATE TABLE menu (
  id INT PRIMARY KEY DEFAULT nextval('menu_seq'),
  date_time TIMESTAMP NOT NULL ,
  recipe_id INT NOT NULL
);