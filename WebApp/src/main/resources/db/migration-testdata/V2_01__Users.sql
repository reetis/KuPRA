--User: admin
--Pass: adminadmin

--User: Hudas
--Pass: adminadmin

--User: rytis_stankus
--Pass: rytisrytis

--User: gintare_stalygaite
--Pass: gintaregintare

--User: lukas_babelis
--Pass: lukaslukas

INSERT INTO "user" (user_id, name, surname, email, is_admin)
VALUES ('admin', 'admin', 'admin', 'admin@admin.lt', TRUE);

INSERT INTO public."user" (user_id, name, surname, email, is_admin)
VALUES ('Hudas', 'Ignas', 'Bobinas', 'hudas@asdfasdf.lt', FALSE);

INSERT INTO public."user" (user_id, name, surname, email, is_admin)
VALUES ('rytis_stankus', 'Rytis', 'Stankus', 'rytis_stankus@gmail.com', FALSE);

INSERT INTO public."user" (user_id, name, surname, email, is_admin)
VALUES ('gintare_stalygaite', 'Gintare', 'Stalygaite', 'gintare_stalygaite@gmail.com', FALSE);

INSERT INTO public."user" (user_id, name, surname, email, is_admin)
VALUES ('lukas_babelis', 'Lukas', 'Babelis', 'lukas_babelis@gmail.com', TRUE);


INSERT INTO password_auth (username, password, user_id)
VALUES ('admin', '0d5768579b73c329c69a8cecfdd50352c3f58463934a0be5b152129bd7965dfd65e44ea89a4a8a45',
        'admin');

INSERT INTO public.password_auth (username, password, user_id)
VALUES ('Hudas', '0d5768579b73c329c69a8cecfdd50352c3f58463934a0be5b152129bd7965dfd65e44ea89a4a8a45',
        'Hudas');

INSERT INTO public.password_auth (username, password, user_id)
VALUES ('rytis_stankus',
        '7da684a98c092e6426ce336f69a47a46c0ce36dde6607462e394a8803c74e8df431778d10a7233fc',
        'rytis_stankus');

INSERT INTO public.password_auth (username, password, user_id)
VALUES ('gintare_stalygaite',
        '0972a3fcafb0c80a8027a3bb708004ec6689d67db55cba5fe648e0bb69c433a290a2c89fe8c4b8e0',
        'gintare_stalygaite');

INSERT INTO public.password_auth (username, password, user_id)
VALUES ('lukas_babelis',
        '844ad7dbc865ca1df23ac5922269fd3bf045ac33a2411d5f0590b382537511278cab0f35c8c09dc5',
        'lukas_babelis');


