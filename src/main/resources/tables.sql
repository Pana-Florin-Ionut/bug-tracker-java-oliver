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