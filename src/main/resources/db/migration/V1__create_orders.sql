create table orders
(
    id               bigserial not null,
    guest_id         bigserial not null,
    establishment_id bigserial not null,
    spot_id          bigint,
    date             date      not null,
    start_time       time      not null,
    end_time         time      not null,
    status           smallint default 0,
    guest_count      int       not null,
    guest_name       varchar   not null,
    primary key (id)
);

create index status_index on orders(status);
create index establishment_index on orders(establishment_id);
create index user_index on orders(guest_id);
