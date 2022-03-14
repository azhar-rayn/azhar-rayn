--liquibase formatted sql
--changeset daniyaalbeg:hadith

INSERT INTO scriptures
VALUES ('929ee644-7fdb-4832-a4b6-3948dd1a53ac', 'Bukhari-Hadith', '0846-01-01 00:00:00', 'Sahih al-Bukhari');

--rollback DELETE FROM scriptures WHERE id='929ee644-7fdb-4832-a4b6-3948dd1a53ac';
