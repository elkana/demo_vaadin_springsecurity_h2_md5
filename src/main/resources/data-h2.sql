-- MD5 password, use https://www.md5hashgenerator.com/
-- INSERT INTO MC_ROLE (code, name) VALUES
--   ('USR', 'USER'),
--   ('CRC', 'COLLECTOR'),
--   ('ADM', 'ADMIN');

INSERT INTO MC_USER (role, user_id, full_name, email, encrypted_pwd, created_date) VALUES
  ('CRC', 'elkana911', 'Eric Elkana 911', 'elkana911@yahoo.com', 'dbde0e2da3e90265d06cb70e1cb701c0', CURRENT_TIMESTAMP),
  ('ADM', 'ellkana','Ellkana Eric', 'ellkana@gmail.com', null, CURRENT_TIMESTAMP),
  ('ADM', 'admin','Administrator', 'admin@gmail.com', '21232f297a57a5a743894a0e4a801fc3', CURRENT_TIMESTAMP);

