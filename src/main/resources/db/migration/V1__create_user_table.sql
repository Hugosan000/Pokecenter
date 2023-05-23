create table user(
id bigint NOT NULL AUTO_INCREMENT,
name varchar(250) NOT NULL,
login varchar(50) NOT NULL UNIQUE,
password varchar(256) NOT NULL,
PRIMARY KEY(id)
)