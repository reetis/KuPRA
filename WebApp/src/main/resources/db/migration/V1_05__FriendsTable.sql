CREATE TABLE friendship (
  friendship_id           INT PRIMARY KEY,
  source_id                INT NOT NULL,
  target_id                INT NOT NULL,
  friendship_status     BOOLEAN NOT NULL
);
CREATE SEQUENCE friendship_seq;