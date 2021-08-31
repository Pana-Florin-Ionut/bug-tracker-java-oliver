INSERT INTO roles(role) VALUES ('EMPLOYEE');
INSERT INTO roles(role) VALUES ('ADMIN');

INSERT INTO users (fname, lname, email, password, image) VALUES ('First', 'Last', 'user@gmail.com', '$2a$10$et.spcFiyH3XTx0.8SvfX.bSvBw61wG8YpgY5Mdus9QbVZYOOh6vO', '/assets/images/faces/face15.jpg');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);