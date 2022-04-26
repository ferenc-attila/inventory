-- CREATE USER inventory
--     WITH PASSWORD 'inventory'
--     VALID UNTIL 'infinity';

CREATE DATABASE inventory
    WITH OWNER = inventory
    TEMPLATE = 'template0'
    ENCODING = 'UTF-8'
    LC_COLLATE = 'hu_HU.UTF-8'
    LC_CTYPE = 'hu_HU.UTF-8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE inventory
    IS 'Develeopment database for BNPI DHTE inventory webapp';
