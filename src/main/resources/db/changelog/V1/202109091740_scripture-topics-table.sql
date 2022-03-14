--liquibase formatted sql
--changeset haziq:scripture-topics-table

CREATE TABLE IF NOT EXISTS scripture_topics (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	scripture_id		uuid			NOT NULL REFERENCES scriptures (id) ON DELETE CASCADE,
    topic_id			uuid			NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
	UNIQUE (scripture_id, topic_id)
);

--rollback DROP TABLE IF EXISTS scripture_topics;