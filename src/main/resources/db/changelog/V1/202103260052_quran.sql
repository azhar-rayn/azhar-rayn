--liquibase formatted sql
--changeset ydmk:quran

INSERT INTO scriptures
VALUES ('21f4bc7f-0205-418d-9e25-6098c481f512', 'Quran', '0632-01-01 00:00:00', 'Allah');

--rollback DELETE FROM scriptures WHERE id='21f4bc7f-0205-418d-9e25-6098c481f512';
