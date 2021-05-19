drop table LiniaEscandall;
drop table Categoria;
drop table Unitat;
drop table Ingredient;

create table if not exists Ingredient(
codi INT primary key,
nom varchar(30)
);

create table if not exists Unitat(
codi INT primary key,
nom varchar(30)
);

create table if not exists Categoria(
codi INT primary key,
nom varchar(30),
color varchar(6)
);

create table if not exists LiniaEscandall(
num INT,
quantitat INT,
unitat INT,
ingredient INT,
foreign key(unitat) references Unitat(codi),
foreign key(ingredient) references Ingredient(codi)
);