CREATE TABLE user_profile_image (
  id        INT PRIMARY KEY,
  user_id   VARCHAR(24) NOT NULL,
  image_url VARCHAR(512),
  thumb_url VARCHAR(512),

  FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);
CREATE SEQUENCE user_profile_image_seq;