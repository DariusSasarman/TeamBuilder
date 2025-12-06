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
	person_owner_id int NOT NULL,
	CONSTRAINT person_pk PRIMARY KEY (person_id),
	CONSTRAINT person_users_fk FOREIGN KEY (person_owner_id) REFERENCES public.users(app_user_id) ON DELETE CASCADE
);

CREATE TABLE public.bonds (
	bond_id serial NOT NULL,
	bond_head_id int NOT NULL,
	bond_tail_id int NOT NULL,
	bond_rating int NOT NULL,
	bond_notes varchar NULL,
	bond_owner_id int NOT NULL,
	CONSTRAINT bond_pk PRIMARY KEY (bond_id),
	CONSTRAINT bond_rating_check CHECK ((bond_rating >=1 AND bond_rating<=10)),
	CONSTRAINT bond_check CHECK ((bond_head_id > bond_tail_id)),
	CONSTRAINT bond_unique UNIQUE (bond_head_id,bond_tail_id),
	CONSTRAINT bond_head_fk FOREIGN KEY (bond_head_id) REFERENCES public.persons(person_id) ON DELETE CASCADE,
	CONSTRAINT bond_tail_fk FOREIGN KEY (bond_tail_id) REFERENCES public.persons(person_id) ON DELETE CASCADE,
	CONSTRAINT bond_owner_fk FOREIGN KEY (bond_owner_id) REFERENCES public.users(app_user_id) ON DELETE CASCADE
);

CREATE TABLE public.groups_table (
	group_id serial NOT NULL,
	group_title varchar NOT NULL,
	group_owner_id int NOT NULL,
	CONSTRAINT groups_pk PRIMARY KEY (group_id),
	CONSTRAINT groups_table_users_fk FOREIGN KEY (group_owner_id) REFERENCES public.users(app_user_id) ON DELETE CASCADE
);

CREATE TABLE public.person_in_group (
	person_in_group_pk serial NOT NULL,
	person_id int NOT NULL,
	group_id int NOT NULL,
	owner_id int NOT NULL,
	CONSTRAINT person_in_group_pk PRIMARY KEY (person_in_group_pk),
	CONSTRAINT person_in_group_unique UNIQUE (person_id,group_id),
	CONSTRAINT person_in_group_persons_fk FOREIGN KEY (person_id) REFERENCES public.persons(person_id) ON DELETE CASCADE,
	CONSTRAINT person_in_group_groups_table_fk FOREIGN KEY (group_id) REFERENCES public.groups_table(group_id) ON DELETE CASCADE,
	CONSTRAINT person_in_group_users_fk FOREIGN KEY (owner_id) REFERENCES public.users(app_user_id) ON DELETE CASCADE
);

