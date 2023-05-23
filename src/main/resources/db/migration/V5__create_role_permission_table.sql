create table rolePermission(
roleId bigint NOT NULL,
permissionId bigint NOT NULL,
PRIMARY KEY(roleId, permissionId),
FOREIGN KEY (roleId) REFERENCES role(id),
FOREIGN KEY (permissionId) REFERENCES permission(id)
)