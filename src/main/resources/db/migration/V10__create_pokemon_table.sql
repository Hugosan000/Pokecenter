create table pokemonType(
pokemonId bigint NOT NULL,
typeId bigint NOT NULL,
PRIMARY KEY (pokemonId, typeId),
FOREIGN KEY (pokemonId) REFERENCES pokemon(id),
FOREIGN KEY (typeId) REFERENCES type(id)
)