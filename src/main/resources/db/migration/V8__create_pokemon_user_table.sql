create table pokemonUser(
id bigint NOT NULL AUTO_INCREMENT,
pokemonId bigint NOT NULL,
userId bigint NOT NULL,
life int NOT NULL DEFAULT 100,
PRIMARY KEY(id),
FOREIGN KEY (pokemonId) REFERENCES pokemon(id),
FOREIGN KEY (userId) REFERENCES user(id)
)