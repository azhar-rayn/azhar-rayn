--liquibase formatted sql
--changeset yrmk:user


CREATE TABLE IF NOT EXISTS users (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	name				varchar(255)	NOT NULL,
	username			varchar(255)	NOT NULL UNIQUE,
	password		    varchar(255)	NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	name				varchar(255)	NOT NULL UNIQUE,
	description			varchar(255)	NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
	id					    uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	user_id				uuid	        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
	role_id			    uuid	        NOT NULL REFERENCES roles (id) ON DELETE CASCADE
);


--rollback DROP TABLE IF EXISTS user_roles;
--rollback DROP TABLE IF EXISTS users;
--rollback DROP TABLE IF EXISTS roles;
