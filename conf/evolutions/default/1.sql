
# --- !Ups
CREATE TABLE game (
  time int,
  karma int,
  energy int,
  coffee int,
  position varchar(255),
  id serial
);

CREATE TABLE level (
  id varchar(255),
  conf TEXT
);

CREATE TABLE menu (
  id varchar(255),
  choices TEXT
);

# --- !Downs
 DROP TABLE game;
 DROP TABLE level;
 DROP TABLE menu;

