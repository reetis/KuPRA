INSERT INTO "user" (user_id, name, surname, email, is_admin)
    VALUES ('admin','admin','admin','admin@admin.lt',true);

INSERT INTO password_auth (username, password, user_id)
    VALUES ('admin', '0d5768579b73c329c69a8cecfdd50352c3f58463934a0be5b152129bd7965dfd65e44ea89a4a8a45', 'admin');

