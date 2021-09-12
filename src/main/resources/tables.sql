CREATE TABLE users (
	user_id bigint NOT NULL AUTO_INCREMENT,
    fname varchar(100),
    lname varchar(100),
    email varchar(100),
    password varchar(255),
    created date,
    last_login date,
    image varchar(128),
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
    description varchar(100),
    readme varchar(1000),
    PRIMARY KEY(project_id)
);

CREATE TABLE tasks (
	task_id bigint NOT NULL AUTO_INCREMENT,
    project_id bigint NOT NULL,
    user_id bigint NOT NULL,
    title varchar(50),
    description varchar(1000),
    created varchar(100),
    status varchar(20),
    PRIMARY KEY(task_id)
);

CREATE TABLE bugs (
	bug_id bigint NOT NULL AUTO_INCREMENT,
    project_id bigint NOT NULL,
    user_id bigint NOT NULL,
    title varchar(50),
    description varchar(1000),
    created varchar(100),
    status varchar(20),
    PRIMARY KEY(bug_id)
);

CREATE TABLE projects_users (
    project_id bigint NOT NULL,
	user_id bigint NOT NULL
);

CREATE TABLE notifications (
	notification_id bigint NOT NULL AUTO_INCREMENT,
	user_id bigint,
    message varchar(255),
    sent varchar(255),
    isOpened int(10),
    PRIMARY KEY(notification_id)
);

CREATE TABLE todo (
	todo_id bigint NOT NULL AUTO_INCREMENT,
    user_id bigint,
    task varchar(46),
    status varchar(32),
    PRIMARY KEY(todo_id)
);