create table cliente (
id integer not null primary key auto_increment,
nome varchar(100),
cpf varchar(11)
);

create table produto(
id integer  not null primary key auto_increment,
descricao varchar(100),
preco numeric(20,2)
);

create table pedido (
id integer primary key auto_increment,
cliente_id integer references cliente (id),
data_pedido timestamp,
status varchar(50),
total numeric(20,2)
);

create table item_pedido (
id integer primary key auto_increment,
pedido_id integer references pedido (id),
produto_id integer references produto(id),
quantidade integer
);

create table usuarios(
id integer primary key auto_increment,
login varchar(50) not null,
senha varchar(255) not null,
admin bool default false
);

