create table comment(

id int auto_increment primary key ,
parent_id int not null ,
type int not null ,
commentator int not null ,
gmt_create bigint,
gmt_modified bigint,
like_count int default 0,
content varchar(1024) null
)