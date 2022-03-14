--liquibase formatted sql
--changeset haziq:mindful-minutes-table

CREATE TABLE IF NOT EXISTS mindful_minutes (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
    name                varchar         NOT NULL,
	arabic_name        	text			NOT NULL,
    translation         varchar         NOT NULL,
    quran               jsonb           NULL,
    hadith              jsonb           NULL,
    dua                 jsonb           NULL
);

--rollback DROP TABLE IF EXISTS mindful_minutes;