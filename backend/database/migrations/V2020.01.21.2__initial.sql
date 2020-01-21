create table category(
    id bigint(20) not null auto_increment,
    name varchar(256),

    primary key(id)
);

create table category_relation(
    todo_id bigint(20) not null,
    cat_id bigint(20) not null,

    foreign key (todo_id) references todo(id),
    foreign key (cat_id) references category(id)
);
