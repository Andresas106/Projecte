/*drop table Plat;
drop table LiniaEscandall;
drop table Categoria;
drop table Unitat;
drop table Ingredient;
*/
create table if not exists Ingredient(
codi INT,
nom varchar(30),
primary key(codi)
);

create table if not exists Unitat(
codi INT,
nom varchar(30),
primary key(codi)
);

create table if not exists Categoria(
codi INT,
nom varchar(30),
color varchar(6),
primary key(codi)
);

create table if not exists LiniaEscandall(
id Int,
num INT,
quantitat INT,
unitat INT,
ingredient INT,
primary key(id),
constraint FK_LINIA_UNITAT foreign key(unitat) references Unitat(codi),
constraint FK_LINIA_INGREDIENT foreign key(ingredient) references Ingredient(codi)
);

create table if not exists Plat(
codi INT,
nom varchar(50),
descripcioMD varchar(150),
preu float(2,2),
foto Blob,
disponible bool,
categoria INT,
escandall INT,
constraint FK_PLAT_LINIA foreign key(escandall) references LiniaEscandall(id),
primary key(codi),
constraint FK_PLAT_CATEGORIA foreign key(categoria) references Categoria(codi)
);