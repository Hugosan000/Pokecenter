create table pokemonTreatment(
id bigint NOT NULL AUTO_INCREMENT,
pokemonUserId bigint NOT NULL UNIQUE,
PRIMARY KEY (id),
FOREIGN KEY (pokemonUserId) REFERENCES pokemonUser(id)
)