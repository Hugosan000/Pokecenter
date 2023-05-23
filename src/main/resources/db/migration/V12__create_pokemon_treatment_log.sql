create table pokemonTreatmentLog(
id bigint NOT NULL AUTO_INCREMENT,
pokemonUserId bigint NOT NULL,
action varchar(250) NOT NULL,
nurseId bigint NOT NULL,
trainerId bigint NOT NULL,
createdAt datetime NOT NULL default now(),
PRIMARY KEY (id),
FOREIGN KEY (pokemonUserId) REFERENCES pokemonUser(id),
FOREIGN KEY (nurseId) REFERENCES user(id),
FOREIGN KEY (trainerId) REFERENCES user(id)
)