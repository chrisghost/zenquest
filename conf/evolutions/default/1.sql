
# --- !Ups
CREATE TABLE game (
  time int,
  karma int,
  state text,
  energy int,
  id serial
);

CREATE TABLE level (
  id varchar(255),
  conf TEXT
);

# --- !Downs
