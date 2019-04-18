drop table if exists users;
create table users(
  id INT NOT NULL AUTO_INCREMENT primary key ,
	username VARCHAR(31) not null,
	password VARCHAR(127) not null,
	role CHAR(1) NOT NULL DEFAULT 'c',
	enabled boolean not null
);