create table comment
(
    id bigint auto_increment,
    parent_id bigint not null,
    type int not null,
    commentator bigint not null,
    gmt_create bigint not null ,
    gmt_modified bigint not null ,
    like_count int default 0,
    constraint COMMENT_PK
        primary key (id)
);