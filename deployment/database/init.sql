CREATE TABLE configuration_entity(
    "id" INTEGER NOT NULL,
    "temperature_max" INTEGER,
    "temperature_min" INTEGER);

INSERT INTO configuration_entity values (1, 2500, 2000);

CREATE TABLE temperature_snapshot_entity(
    "id" SERIAL,
    "date" TIMESTAMP,
    "mean" NUMERIC,
    "reading1" NUMERIC,
    "reading2" NUMERIC);
