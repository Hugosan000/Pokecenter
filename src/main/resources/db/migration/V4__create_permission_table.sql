create table permission(
id bigint NOT NULL AUTO_INCREMENT,
action varchar(50) NOT NULL UNIQUE,
PRIMARY KEY(id)
)