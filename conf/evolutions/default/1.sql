
# --- !Ups
CREATE TABLE game (
  time: Long,
  karma: Long,
  position: Long,
  energy: Long,
  id serial
);

CREATE TABLE level (
  id String,
  conf: TEXT
);

# --- !Downs
