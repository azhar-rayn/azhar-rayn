--liquibase formatted sql
--changeset daniyaalbeg:added-verse-group-tables

CREATE TABLE IF NOT EXISTS verse_groups (
  id uuid	DEFAULT uuid_generate_v4() PRIMARY KEY,
  metadata jsonb NULL
);

CREATE TABLE IF NOT EXISTS verse_groups_verse_mapping (
  verse_group_id uuid NOT NULL REFERENCES verse_groups (id) ON DELETE CASCADE,
  verse_id uuid NOT NULL REFERENCES verses (id) ON DELETE CASCADE,
  is_primary boolean NOT NULL
);

CREATE TABLE IF NOT EXISTS topic_verse_groups (
  topic_id uuid NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
  verse_group_id uuid NOT NULL REFERENCES verse_groups (id) ON DELETE CASCADE
);

--rollback DROP TABLE IF EXISTS verse_groups;
--rollback DROP TABLE IF EXISTS verse_groups_verse_mapping;
--rollback DROP TABLE IF EXISTS topic_verse_groups;

