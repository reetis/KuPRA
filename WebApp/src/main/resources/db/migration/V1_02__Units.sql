CREATE TABLE unit (
  id            int PRIMARY KEY,
  name          varchar(64) NOT NULL,
  abbreviation  varchar(8) NOT NULL
);
CREATE SEQUENCE unit_seq;