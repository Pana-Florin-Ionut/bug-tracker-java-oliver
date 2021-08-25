CREATE TABLE users (
	user_id bigint NOT NULL AUTO_INCREMENT,
    fname varchar(100),
    lname varchar(100),
    email varchar(100),
    password varchar(255),
    created date,
    last_login date,
    PRIMARY KEY(user_id)
);

CREATE TABLE roles (
	role_id bigint NOT NULL AUTO_INCREMENT,
    role varchar(100),
    PRIMARY KEY(role_id)
);

CREATE TABLE users_roles (
	user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

CREATE TABLE projects (
	project_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint NOT NULL,
    title varchar(30),
    description varchar(30),
    readme varchar(1000),
    PRIMARY KEY(project_id)
);