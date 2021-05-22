/*drop table Liniacomanda;
drop table comanda;
drop table taula;
drop table cambrer;
drop table liniaescandall;
drop table plat;
drop table categoria;
drop table unitat;
drop table ingredient;
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
plat INT,
num INT,
quantitat INT,
unitat INT,
ingredient INT,
constraint primary key(plat, num),
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
primary key(codi),
constraint FK_PLAT_CATEGORIA foreign key(categoria) references Categoria(codi)
);

create table if not exists Cambrer(
codi INT,
nom varchar(30),
cognom1 varchar(30),
cognom2 varchar(30),
usuari varchar(30),
passw varchar(30),
constraint primary key(codi)
);

create table if not exists Comanda (
codi INT,
data Date,
cambrer INT,
constraint primary key(codi),
constraint FK_COMANDA_CAMBRER foreign key(cambrer) references Cambrer(codi)
);

create table if not exists LiniaComanda(
comanda INT,
num INT,
quantitat int,
estat enum('EN_PREPARACIO', 'PREPARADA'),
constraint primary key(comanda, num),
constraint FK_LINIACOMANDA_COMANDA foreign key(comanda) references Comanda(codi)
);

create table if not exists Taula(
numero INT,
constraint primary key(numero)
);

alter table comanda add column taula INT;
alter table comanda add constraint FK_COMANDA_TAULA foreign key(taula) references Taula(numero); 