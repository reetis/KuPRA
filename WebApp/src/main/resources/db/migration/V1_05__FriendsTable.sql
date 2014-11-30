CREATE TABLE friendship (
  friendship_id           INT PRIMARY KEY,
  source_id                VARCHAR(24) NOT NULL REFERENCES "user"(user_id),
  target_id                 VARCHAR(24) NOT NULL REFERENCES "user"(user_id),
  friendship_status     BOOLEAN NOT NULL
);
CREATE SEQUENCE friendship_seq;