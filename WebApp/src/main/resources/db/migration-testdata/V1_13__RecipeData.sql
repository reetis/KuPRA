INSERT INTO public.recipe (recipe_id, author, name, cooking_time, servings, public_access, description, process_description)
VALUES ((SELECT nextval('recipe_seq')), 'Hudas', 'Pirmasis Receptas', 150, 4, true, 'Puikus receptukas', '1. Paimti produktus
2. Pagaminti
3. Suvalgyti');
INSERT INTO public.recipe (recipe_id, author, name, cooking_time, servings, public_access, description, process_description)
VALUES ((SELECT nextval('recipe_seq')), 'Hudas', 'Kietas Receptas', 45, 2, false, 'Receptas gaminamas dažniausiai iš pieno ir Bananaų, labai skanu prie žuvies', '1. Nulupam banaus
2. Nuskutam žuvį
3. Suplakam
4. Pakepam');
INSERT INTO public.recipe (recipe_id, author, name, cooking_time, servings, public_access, description, process_description)
VALUES ((SELECT nextval('recipe_seq')), 'lukas_babelis', 'Kebabas', 120, 2, false, 'Pukus Kebabas', '1. Apdorojam');
