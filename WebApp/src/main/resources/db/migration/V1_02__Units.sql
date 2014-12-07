CREATE SEQUENCE unit_seq START WITH 1;
CREATE TABLE unit (
  id INT PRIMARY KEY DEFAULT nextval('unit_seq'),
  name         VARCHAR(64) NOT NULL,
  abbreviation VARCHAR(8)  NOT NULL
);