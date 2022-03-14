--liquibase formatted sql
--changeset haziq:pending-table

CREATE TABLE IF NOT EXISTS pending_table (
	id					uuid			DEFAULT uuid_generate_v4() PRIMARY KEY,
	data        		jsonb			NOT NULL,
    action              varchar         NOT NULL,
    type                varchar         NOT NULL,
    status              varchar         NOT NULL DEFAULT 'PENDING',
    added_date                timestamp		NOT NULL DEFAULT CURRENT_TIMESTAMP
);

--rollback DROP TABLE IF EXISTS pending_table;