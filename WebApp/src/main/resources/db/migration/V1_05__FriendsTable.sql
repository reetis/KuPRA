CREATE SEQUENCE friendship_seq START WITH 1;
CREATE TABLE friendship (
  friendship_id     INT PRIMARY KEY DEFAULT nextval('friendship_seq'),
  source_id         VARCHAR(24) NOT NULL REFERENCES "user" (user_id),
  target_id         VARCHAR(24) NOT NULL REFERENCES "user" (user_id),
  friendship_status BOOLEAN     NOT NULL
);