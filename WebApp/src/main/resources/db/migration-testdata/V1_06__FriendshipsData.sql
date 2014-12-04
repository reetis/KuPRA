INSERT INTO friendship (friendship_id, target_id, source_id, friendship_status)
  VALUES ((SELECT nextval('friendship_seq')), 'Hudas', 'rytis_stankus', true);
INSERT INTO friendship (friendship_id, target_id, source_id, friendship_status)
  VALUES ((SELECT nextval('friendship_seq')), 'Hudas', 'lukas_babelis', true);
INSERT INTO friendship (friendship_id, target_id, source_id,  friendship_status)
  VALUES ((SELECT nextval('friendship_seq')), 'Hudas', 'gintare_stalygaite', false);
