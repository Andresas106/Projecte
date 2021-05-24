/* Taula Ingredients*/
insert into ingredient values(1, 'Tomaquet');
insert into ingredient values(2, 'Mozzarella');
insert into ingredient values(3, 'Xampinyons');
insert into ingredient values(4, 'Gambes');
insert into ingredient values(5, 'embotit');
insert into ingredient values(6, 'Pernil dolc');

/* Taula Unitat*/


/*Taula Categoria*/
insert into categoria values(1, 'Primers', '#00FF00');
/*Taula Plat*/
insert into plat values(1, 'Pizza 4 Stagioni', 
'Pizza de 4 estacions en la que en cada porcio trobem ingredients de cada estacio del any', '15.5',
load_file('C:/Users/averd/OneDrive/Escritorio/pizza_4_stagionni.jpg'), 1, 1);

delete from plat;