--liquibase formatted sql
--changeset haziq:added-fragments-pending-table


CREATE TABLE IF NOT EXISTS fragments_pending (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	language_id			uuid			NOT NULL REFERENCES languages (id) ON DELETE CASCADE,
	topic_id			uuid			NOT NULL REFERENCES topics (id) ON DELETE CASCADE,
	type				varchar(50)		NOT NULL,
	beginning			int				NOT NULL,
	ending				int				NOT NULL,
	highlighted_by		varchar(50)		NOT NULL,
	added_date			timestamp		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	approved			boolean			NOT NULL DEFAULT FALSE,
	metadata			jsonb			NOT NULL
);

--rollback DROP TABLE IF EXISTS fragments_pending;