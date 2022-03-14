--liquibase formatted sql
--changeset yrmk:base-tables
CREATE TABLE IF NOT EXISTS languages (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	name				varchar(255)	NOT NULL,
	language			varchar(255)	NOT NULL,
	native_language		boolean			NOT NULL DEFAULT false,
	translator			varchar(255)
);

CREATE TABLE IF NOT EXISTS scriptures (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	name				varchar(50)		NOT NULL,
	publish_date		timestamp		NOT NULL,
	author				varchar(50)		NOT NULL,
	CONSTRAINT unique_scriptures_name_author UNIQUE (name, author)
);

CREATE TABLE IF NOT EXISTS scripture_languages (
	scripture_id		uuid			NOT NULL REFERENCES scriptures (id) ON DELETE CASCADE,
	language_id			uuid			NOT NULL REFERENCES languages (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS verses (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	scripture_id		uuid			NOT NULL REFERENCES scriptures (id) ON DELETE CASCADE,
	verse_order			bigint			NOT NULL,
	metadata			jsonb			NOT NULL,
	CONSTRAINT unique_verses_scripture_id_verse_order UNIQUE (scripture_id, verse_order)
);

CREATE TABLE IF NOT EXISTS verse_contents (
	verse_id			uuid			NOT NULL REFERENCES verses (id) ON DELETE CASCADE,
	language_id			uuid			NOT NULL REFERENCES languages (id) ON DELETE CASCADE,
	content				text			NOT NULL,
	CONSTRAINT unique_verse_language UNIQUE (verse_id, language_id)
);

CREATE TABLE IF NOT EXISTS topics (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	name				varchar(50)		NOT NULL,
	area				varchar(50)		NOT NULL,

	UNIQUE (name, area)
);

CREATE TABLE IF NOT EXISTS topic_verses (
	topic_id			uuid			NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
	verse_id			uuid			NOT NULL REFERENCES verses (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS related_topics (
	topic_id			uuid			NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
	related_topic_id	uuid			NOT NULL REFERENCES topics (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS fragments (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	verse_id			uuid			NOT NULL REFERENCES verses (id) ON DELETE CASCADE,
	language_id			uuid			NOT NULL REFERENCES languages (id) ON DELETE CASCADE,
	topic_id			uuid			NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
	type				varchar(50)		NOT NULL,
	beginning			int				NOT NULL,
	ending				int				NOT NULL
);

--rollback DROP TABLE IF EXISTS fragments;
--rollback DROP TABLE IF EXISTS related_topics;
--rollback DROP TABLE IF EXISTS topic_verses;
--rollback DROP TABLE IF EXISTS topics;
--rollback DROP TABLE IF EXISTS verse_contents;
--rollback DROP TABLE IF EXISTS verses;
--rollback DROP TABLE IF EXISTS scripture_languages;
--rollback DROP TABLE IF EXISTS scriptures;
--rollback DROP TABLE IF EXISTS languages;
