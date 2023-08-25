-- Table: public.weather
CREATE TABLE IF NOT EXISTS public.weather
(
    id serial NOT NULL PRIMARY KEY,
    description character varying(250) COLLATE pg_catalog."default",
    date date,
    created_date date,
    created_by integer,
    last_modified_date date,
    last_modified_by integer,
    degrees integer NOT NULL,
    cloudiness character varying COLLATE pg_catalog."default",
    pressure character varying COLLATE pg_catalog."default"
    )

    TABLESPACE pg_default;