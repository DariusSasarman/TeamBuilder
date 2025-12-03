CREATE USER app_user WITH ENCRYPTED PASSWORD 'password';

GRANT USAGE ON SCHEMA public TO app_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT INSERT, SELECT, UPDATE, DELETE ON TABLES TO app_user;

ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT USAGE, SELECT ON SEQUENCES TO app_user;

CREATE TABLE public.users (
	app_user_id serial NOT NULL,
	app_user_name varchar NOT NULL,
	app_user_hashed_password varchar NOT NULL,
	CONSTRAINT app_users_pk PRIMARY KEY (app_user_id),
	CONSTRAINT app_users_unique UNIQUE (app_user_name)
);

CREATE TABLE public.persons (
	person_id serial NOT NULL,
	person_name varchar NOT NULL,
	person_image bytea NOT NULL,
	person_notes varchar NULL,
	person_owner_id serial NOT NULL,
	CONSTRAINT person_pk PRIMARY KEY (person_id),
	CONSTRAINT person_users_fk FOREIGN KEY (person_owner_id) REFERENCES public.users(app_user_id)
);

