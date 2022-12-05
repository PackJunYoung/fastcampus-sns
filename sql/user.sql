create table if not exists public."user"
(
    id integer not nill default nextval('user_id_seq'::regclass),
    password character varying(255) collate pg_catalog."default",
    registered_at timestamp without time zone,
    removed_at timestamp without time zone,
    role character varying(255) collate pg_catalog."default",
    updated_at timestamp without time zone,
    user_name character varying(255) collate pg_catalog."default",
    constraint user_pkey primary key (id),
    constraint uk_lqjrcobrh9jc9wpcar64q1bfh unique (user_name)
)

