/* Taula Ingredients*/
insert into ingredient values(1, 'Tomaquet');
insert into ingredient values(2, 'Mozzarella');
insert into ingredient values(3, 'Xampinyons');
insert into ingredient values(4, 'Gambes');
insert into ingredient values(5, 'embotit');
insert into ingredient values(6, 'Pernil dolc');
insert into ingredient values(7, 'Parmigiano');
insert into ingredient values(8, 'bacó');
insert into ingredient values(9, 'formatge taleggio');
insert into ingredient values(10, 'alfabrega');
insert into ingredient values(11, 'ou');
insert into ingredient values(12, 'Crema de tòfona blanca');
insert into ingredient values(13, 'foie');
insert into ingredient values(14, 'foramtge tomino');
insert into ingredient values(15, 'ceba picada');

/* Taula Unitat*/
insert into unitat values(1, 'Kg');
insert into unitat values(2, 'Litres');
insert into unitat values(3, 'Cullerades');
insert into unitat values(4, 'Unitats');
insert into unitat values(5, 'cm3');
insert into unitat values(6, 'ml');

/*Taula Categoria*/
insert into categoria values(1, 'Entrants', '#00FF00');
insert into categoria values(2, 'Primers', '#FF0000');
insert into categoria values(3, 'Segons', '#FFFF00');
insert into categoria values(4, 'Postres', '#FF00FF');
/*Taula Plat
Exemple de insert de plat -- imatge --
*/
insert into plat values(1, 'Pizza 4 Stagioni', 
'Pizza de 4 estacions en la que en cada porcio trobem ingredients de cada estacio del any', '15.5',
load_file('C:/Users/averd/OneDrive/Escritorio/pizza_4_stagionni.jpg'), 1, 1);

insert into plat values(2, 'Pizza Al Parmigiano', 
'Pizza de 4 estacions en la que en cada porcio trobem ingredients de cada estacio del any', '20',
load_file('C:/Users/averd/OneDrive/Escritorio/pizza_alparmigiano.jpg'), 1, 1);

insert into plat values(3, 'Pizza Bianca di Foie', 
'Pizza de 4 estacions en la que en cada porcio trobem ingredients de cada estacio del any', '18.75',
load_file('C:/Users/averd/OneDrive/Escritorio/pizza_bianca_di_foie.jpg'), 1, 1);

/*insert into plat values(4, 'Inventat', 'Inventat total', '19.70', null, 0, 2);*/

/*Taula Taula*/
insert into taula values(1);
insert into taula values(2);
insert into taula values(3);
insert into taula values(4);
insert into taula values(5);
insert into taula values(6);
/*Taula Cambrer*/
insert into cambrer values(1, 'Pepe', 'Gotera', '', 'pgotera', 'pgotera');
/*Taula COmanda*/
insert into comanda values(1, sysdate(), 1, 1); 
/*Taula LiniaComanda*/
insert into liniaComanda values(1, 1, 1, false, 1);

select * from liniacomanda;