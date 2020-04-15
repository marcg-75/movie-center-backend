insert into authority_type (code, name) values ('REG', 'Region');
insert into authority_type (code, name) values ('KOM', 'Kommun');
insert into authority_type (code, name) values ('STA', 'Statlig');
insert into authority_type (code, name) values ('PRI', 'Privat');

-- Flyway 1.36
/*
insert into contact_role (code, name, registry_type_cd, require_description, sequence) values ('ANT', 'Annat', null,  1, 100) ON DUPLICATE KEY UPDATE sequence=100;
insert into contact_role (code, name, registry_type_cd, sequence) values ('BBS', 'Biobankssamordnare', 'AUTHORITY', 11) ON DUPLICATE KEY UPDATE sequence=11;
insert into contact_role (code, name, registry_type_cd, sequence) values ('BBS', 'Kontaktperson', 'AUTHORITY', 10) ON DUPLICATE KEY UPDATE sequence=10;
insert into contact_role (code, name, registry_type_cd, sequence) values ('BBA', 'Biobanksansvarig', 'BIOBANK', 21) ON DUPLICATE KEY UPDATE sequence=21;
insert into contact_role (code, name, registry_type_cd, sequence) values ('BUT', 'Utlistning', 'BIOBANK', 22) ON DUPLICATE KEY UPDATE sequence=22;
insert into contact_role (code, name, registry_type_cd, sequence) values ('BAA', 'Biobanksavdelningsansvarig', 'BIOBANK_DIVISION', 31) ON DUPLICATE KEY UPDATE sequence=31;
insert into contact_role (code, name, registry_type_cd, sequence) values ('BAU', 'Utlistning', 'BIOBANK_DIVISION', 32) ON DUPLICATE KEY UPDATE sequence=32;
insert into contact_role (code, name, registry_type_cd, sequence) values ('PRA', 'Provsamlingsansvarig', 'SAMPLE_COLLECTION', 41) ON DUPLICATE KEY UPDATE sequence=41;
insert into contact_role (code, name, registry_type_cd, sequence) values ('PHU', 'Forskningshuvudman', 'SAMPLE_COLLECTION', 42) ON DUPLICATE KEY UPDATE sequence=42;
insert into contact_role (code, name, registry_type_cd, sequence) values ('PAF', 'Ansvarig forskare/prövare', 'SAMPLE_COLLECTION', 43) ON DUPLICATE KEY UPDATE sequence=43;
*/

insert into rbc(id, hsaid, name) values (1, 'SE0000000000-0001', 'Norra');
insert into rbc(id, hsaid, name) values (2, 'SE0000000000-0002', 'Stockholm');
insert into rbc(id, hsaid, name) values (3, 'SE0000000000-0003', 'Sydöstra');
insert into rbc(id, hsaid, name) values (4, 'SE0000000000-0004', 'Södra');
insert into rbc(id, hsaid, name) values (5, 'SE0000000000-0005', 'Uppsala Örebro');
insert into rbc(id, hsaid, name) values (6, 'SE0000000000-0006', 'Västra');

insert into medical_area (code, name, sequence, require_description) values ('PAT', 'Patologi/cytologi', 1, 0);
insert into medical_area (code, name, sequence, require_description) values ('GYN', 'Gynekologi/obstetrik', 2, 0);
insert into medical_area (code, name, sequence, require_description) values ('BAK', 'Bakteriologi/virologi', 3, 0);
insert into medical_area (code, name, sequence, require_description) values ('TRP', 'Transplantation', 4, 0);
insert into medical_area (code, name, sequence, require_description) values ('KEM', 'Klinisk kemi', 5, 0);
insert into medical_area (code, name, sequence, require_description) values ('IVF', 'IVF eller liknande', 6, 0);
insert into medical_area (code, name, sequence, require_description) values ('TRF', 'Transfusion', 7, 0);
insert into medical_area (code, name, sequence, require_description) values ('KOM', 'Kombination av provsamlingar', 9, 1);
insert into medical_area (code, name, sequence, require_description) values ('ANT', 'Annat', 10, 1);

insert into material_type (code, name) values ('MT1', 'Organ');
insert into material_type (code, name) values ('MT2', 'Vävnad');
insert into material_type (code, name) values ('MT3', 'Celler/cellinjer');
insert into material_type (code, name) values ('MT4', 'Genomiskt material');
insert into material_type (code, name) values ('MT5', 'Blod eller blodplasma');
insert into material_type (code, name) values ('MT6', 'Urin');
insert into material_type (code, name) values ('MT7', 'Saliv');
insert into material_type (code, name) values ('MT8', 'Preparerat DNA');
insert into material_type (code, name) values ('MT9', 'Annat');

insert into user(hsaid, status, role_id) values ('TNT4477663322-102C', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-102F', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1028', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1027', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1026', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1025', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-102D', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1029', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-102B', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1023', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-1033', 'ACTIVE', 1);
insert into user(hsaid, status, role_id) values ('TNT4477663322-103T', 'ACTIVE', 1);

SET SQL_SAFE_UPDATES=0;
UPDATE user SET firstname = 'OLOF' , lastname = 'ERICSSON' WHERE hsaid = 'TNT4477663322-102C';
UPDATE user SET firstname = 'JAN' , lastname = 'LARSSON' WHERE hsaid = 'TNT4477663322-102F';
UPDATE user SET firstname = 'ELIN' , lastname = 'GUSTAFSSON' WHERE hsaid = 'TNT4477663322-1028';
UPDATE user SET firstname = 'SVEN' , lastname = 'LARSSON' WHERE hsaid = 'TNT4477663322-1027';
UPDATE user SET firstname = 'SARA' , lastname = 'JOHANSSON JOHANSSON' WHERE hsaid = 'TNT4477663322-1026';
UPDATE user SET firstname = 'MIKAEL' , lastname = 'ERICSSON' WHERE hsaid = 'TNT4477663322-1025';
UPDATE user SET firstname = 'SARA' , lastname = 'GUSTAFSSON KARLSSON' WHERE hsaid = 'TNT4477663322-102D';
UPDATE user SET firstname = 'PER' , lastname = 'LARSSON NILSSON' WHERE hsaid = 'TNT4477663322-1029';
UPDATE user SET firstname = 'ELIN' , lastname = 'ANDERSSON' WHERE hsaid = 'TNT4477663322-102B';
UPDATE user SET firstname = 'HUMBERTUS' , lastname = 'CLAESSON' WHERE hsaid = 'TNT4477663322-103T';
SET SQL_SAFE_UPDATES=1;

