create table firstAccess(
userId bigint NOT NULL,
roleId bigint NOT NULL,
firstAccess tinyint(1) NOT NULL default 0,
PRIMARY KEY(userId, roleId),
FOREIGN KEY (userId) REFERENCES userRole(userId),
FOREIGN KEY (roleId) REFERENCES userRole(roleId)
)