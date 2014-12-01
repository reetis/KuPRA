CREATE TABLE "user" (
  user_id       varchar(24) PRIMARY KEY,
  name          varchar(64) NOT NULL,
  surname       varchar(64) NOT NULL,
  email         varchar(64) NOT NULL,
  description   varchar(64),
  locale        varchar(64),
  is_admin      boolean NOT NULL DEFAULT FALSE
);

CREATE TABLE password_auth (
  username      varchar(24) PRIMARY KEY,
  password      varchar(120) NOT NULL,
  user_id       varchar(24) NOT NULL UNIQUE,
  FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);