create table preorders
(
    id bigserial not null,
    vendor_id bigint not null,
    customer_phone_number varchar not null,
    product_ids anyarray not null,
    accepted_by_vendor boolean default false,
    accept_link varchar not null,
    deny_link varchar not null
);

create unique index preorders_id_uindex
    on preorders (id);

alter table preorders
    add constraint preorders_pk
        primary key (id);
