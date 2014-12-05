CREATE TABLE "user" (
  user_id     VARCHAR(24) PRIMARY KEY,
  name        VARCHAR(64) NOT NULL,
  surname     VARCHAR(64) NOT NULL,
  email       VARCHAR(64) NOT NULL,
  description VARCHAR(64),
  locale      VARCHAR(64),
  is_admin    BOOLEAN     NOT NULL DEFAULT FALSE
);

CREATE TABLE password_auth (
  user_id  VARCHAR(24) PRIMARY KEY,
  username VARCHAR(24)  NOT NULL UNIQUE,
  password VARCHAR(120) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);