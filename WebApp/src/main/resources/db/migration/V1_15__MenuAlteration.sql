ALTER TABLE menu
  ADD COLUMN isCompleted BOOL NOT NULL DEFAULT false,
  ADD COLUMN score INT,
  ADD CONSTRAINT recipeFrgKey FOREIGN KEY (recipe_id) REFERENCES recipe (recipe_id);