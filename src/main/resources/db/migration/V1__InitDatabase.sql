create table products
(
    id                     bigserial                    not null,
    name                   varchar(400)                 not null,
    min_quantity_per_order double precision default 0.5 not null,
    max_quantity_per_order int              default 0,
    quantity_type          int                          not null
);

comment on table products is 'Vendor Products';

create unique index products_id_uindex
    on products (id);

alter table products
    add constraint products_pk
        primary key (id);

create table vendor_account
(
    id    bigserial    not null,
    name  varchar(200) not null,
    phone varchar(100) not null,
    email varchar(400),
    city  varchar(200) not null
);

create unique index vendor_account_id_uindex
    on vendor_account (id);

create unique index vendor_account_phone_uindex
    on vendor_account (phone);

alter table vendor_account
    add constraint vendor_account_pk
        primary key (id);

alter table products
    add created_at timestamp not null;

alter table products
    add updated_at timestamp default CURRENT_DATE not null;

alter table vendor_account
    add created_at timestamp not null;

alter table vendor_account
    add updated_at timestamp default CURRENT_DATE not null;