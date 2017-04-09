DROP TABLE IF EXISTS public.department;
DROP SEQUENCE IF EXISTS public.department_id_seq;

CREATE SEQUENCE public.department_id_seq
    INCREMENT 1
    START 0
    MINVALUE 0
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.department_id_seq
    OWNER TO depemp;

CREATE TABLE public.department (
    id bigint NOT NULL DEFAULT nextval('department_id_seq'::regclass),
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(32) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.department
    OWNER to depemp;
