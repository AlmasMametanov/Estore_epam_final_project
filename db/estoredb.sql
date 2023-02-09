CREATE DATABASE estoredb;

USE estoredb;

create table user
(id bigint primary key auto_increment,
first_name varchar(30) not null,
last_name varchar(30) not null,
birthday date not null,
phone_number varchar(20) not null,
address varchar(100) not null,
password varchar(100) not null,
is_admin tinyint(1) default 0,
login varchar(25) not null unique,
is_banned tinyint(1) default 0);

create table locale
(id int primary key auto_increment,
short_name varchar(10) not null,
name varchar(30) not null);

create table status
(id int primary key auto_increment,
locale_id int,
name varchar(50) not null,
foreign key (locale_id) references locale (id));

create table country
(id int primary key auto_increment,
locale_id int,
name varchar(50) not null,
foreign key (locale_id) references locale (id));

create table product_category
(id int primary key auto_increment,
parent_id int,
foreign key (parent_id) references product_category (id));

create table product_category_locale
(product_category_id int not null,
locale_id int,
name varchar(50) not null,
foreign key (locale_id) references locale (id),
foreign key (product_category_id) references product_category (id));

create table product
(id bigint primary key auto_increment,
name varchar(50) not null,
description varchar(400) not null,
cost int not null,
count int default 0,
country_id int,
product_category_id int,
foreign key (country_id) references country (id),
foreign key (product_category_id) references product_category_locale (product_category_id));

create table basket
(id bigint primary key auto_increment,
user_id bigint,
product_id bigint,
count int default 0,
foreign key (user_id) references user (id),
foreign key (product_id) references product (id));

create table orders
(id bigint primary key auto_increment,
user_id bigint,
status_id int,
total_cost int not null,
date_start date not null,
date_finish date not null,
foreign key (user_id) references user (id),
foreign key (status_id) references status (id));

create table order_detail
(id bigint primary key auto_increment,
order_id bigint,
product_id bigint,
count int not null,
cost int not null,
foreign key (order_id) references orders (id),
foreign key (product_id) references product (id));

insert into user (first_name, last_name, birthday, phone_number, address, password, is_admin, login, is_banned) values ('admin', 'admin', '1998-07-01', 87751234567, 'city Melkiy', 'c177bd4348a475e1a02858d01f464512', true, 'admin', false);
insert into user (first_name, last_name, birthday, phone_number, address, password, is_admin, login, is_banned) values ('user', 'user', '1990-08-07', 87757654321, 'city Bolshoy', 'c177bd4348a475e1a02858d01f464512', false, 'user', false);
insert into user (first_name, last_name, birthday, phone_number, address, password, is_admin, login, is_banned) values ('blocked', 'blocked', '1995-10-07', 87750123456, 'city Nepovezlo', 'c177bd4348a475e1a02858d01f464512', false, 'blocked', true);
insert into locale (short_name, name) values ('RU', 'русский'), ('EN', 'english');
insert into status (locale_id, name) values (1, 'В обработке'), (2, 'In processing'), (1, 'Принят'), (2, 'Accepted');
insert into country (locale_id, name) values (1, 'Казахстан'), (2, 'Kazakhstan'), (1, 'Китай'), (2, 'China'), (1, 'США'), (2, 'USA');
insert into product_category (parent_id) values (null), (1), (2), (2), (2), (1), (1), (1), (8), (8);
insert into product_category_locale (product_category_id, locale_id, name) values (1, 1, 'Одежда'), (1, 2, 'Clothing'), (2, 1, 'Верхняя одежда'), (2, 2, 'Outerwear'), (3, 1, 'Футболки'), (3, 2, 'T-shirts'), (4, 1, 'Рубашки'), (4, 2, 'Shirts'),
(5, 1, 'Толстовки'), (5, 2, 'Sweatshirts'), (6, 1, 'Джинсы'), (6, 2, 'Jeans'), (7, 1, 'Брюки'), (7, 2, 'Pants'), (8, 1, 'Нижнее белье'), (8, 2, 'Underwear'), (9, 1, 'Трусы'), (9, 2, 'Underpants'), (10, 1, 'Майки'), (10, 2, 'Undershirts');
insert into product (name, description, cost, count, country_id, product_category_id) values ('Футболка', 'Белая, хлопок 100%', 7500, 35, 1, 3), ('T-shirts', 'Black and white', 6500, 40, 6, 3), ('Джинсы', 'Черные', 20000, 10, 3, 6), ('Jeans', 'Blue', 25000, 20, 4, 6);



