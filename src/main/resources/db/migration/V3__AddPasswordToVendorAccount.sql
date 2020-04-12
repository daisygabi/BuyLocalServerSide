alter table vendor_account
    add password varchar;

alter table vendor_account
    add role int default 1 not null;
