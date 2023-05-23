create table userRole(
userId bigint NOT NULL,
roleId bigint NOT NULL,
PRIMARY KEY(userId, roleId),
FOREIGN KEY (userId) REFERENCES user(id),
FOREIGN KEY (roleId) REFERENCES role(id)
)