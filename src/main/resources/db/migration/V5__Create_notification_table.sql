create  table notification(
id int auto_increment primary key ,
notifier int not null ,
receiver int not null ,
outerId int not null ,
type int not null ,
gmt_create bigint not null ,
type int default 0 not null
)