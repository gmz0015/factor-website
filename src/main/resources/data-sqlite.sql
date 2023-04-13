REPLACE INTO user (id, username, password, salt, status, is_delete) VALUES (1609533124106301442, 'admin', '64936f84dc9f352ac94b567e789fb28a', '93w8ro2D+JWBhHxMJ33Zb2Ba0LfVyk0evLdQEbGoXxk=', 1, 0);
REPLACE INTO user (id, username, password, salt, status, is_delete) VALUES (1640964797162962946, 'visitor', 'b8679792bfc2a44dda3646d2202c9767', 'S/32EHLNc1FUYU76jEM3KN+8Ohb5nph2d8+pI4mJALs=', 1, 0);
REPLACE INTO role (id, name, status) VALUES (1, 'admin', 1);
REPLACE INTO role (id, name, status) VALUES (2, 'visitor', 1);
REPLACE INTO user_role (id, user_id, role_id) VALUES (1609533124173410306, 1609533124106301442, 1);
REPLACE INTO user_role (id, user_id, role_id) VALUES (1644984949499019265, 1640964797162962946, 2);
