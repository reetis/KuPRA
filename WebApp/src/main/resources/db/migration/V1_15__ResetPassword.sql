ALTER TABLE password_auth ADD COLUMN reset_password_token VARCHAR(64) NULL;
ALTER TABLE password_auth ADD COLUMN reset_password_token_valid_till TIMESTAMP NULL;

CREATE INDEX password_auth_reset_token ON password_auth (reset_password_token, reset_password_token_valid_till);