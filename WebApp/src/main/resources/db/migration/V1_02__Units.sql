CREATE TABLE unit (
  id           INT PRIMARY KEY,
  name         VARCHAR(64) NOT NULL,
  abbreviation VARCHAR(8)  NOT NULL
);
CREATE SEQUENCE unit_seq;