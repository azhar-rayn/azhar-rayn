--liquibase formatted sql
--changeset daniyaalbeg:remove-urdu-qadri

DROP TABLE IF EXISTS ur_qadri;
DELETE FROM languages WHERE id='316f4ecc-c007-48c9-afb5-ba88e908b130';
