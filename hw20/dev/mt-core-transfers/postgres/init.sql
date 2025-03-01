CREATE USER mt_core_transfers_admin PASSWORD 'mt_core_transfers_admin';
CREATE DATABASE mt_core_transfers OWNER mt_core_transfers_admin;
GRANT ALL PRIVILEGES ON DATABASE mt_core_transfers TO mt_core_transfers_admin;