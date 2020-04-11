alter table vendor_account
    add verified boolean default false not null;

alter table vendor_account
    add verifying_code varchar(5);
