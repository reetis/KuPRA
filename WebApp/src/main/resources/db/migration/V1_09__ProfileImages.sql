CREATE TABLE user_profile_image (
  id            int PRIMARY KEY,
  user_id       varchar(24) NOT NULL,
  image_url    VARCHAR(512),
  thumb_url    VARCHAR(512),

  FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);
CREATE SEQUENCE user_profile_image_seq;