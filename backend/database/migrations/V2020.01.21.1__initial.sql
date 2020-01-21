create table todo(
    id bigint(20) not null auto_increment,
    title varchar(256),
    description text,
    status varchar(32),
    due timestamp,

    primary key(id)
);
