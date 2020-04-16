alter table products
    add in_stock boolean default true not null;

alter table products
    add price double precision default 0.0 not null;

alter table products
    add vendor_id bigint not null;

alter table products
    add currency varchar not null;

