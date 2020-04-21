/* Create demo dev users*/
INSERT INTO vendor_account (name, phone, email, city, created_at, updated_at, verified, verifying_code, password, role)
VALUES ('Martin Dev', '+560542255', 'dev@email.com', 'Sibiu', '2019-04-08 20:21:27.025000',
        '2019-04-08 20:21:27.025000', true, '214fc', 'dasfdg7guhfdgdfjghfd', 1)
on conflict do nothing;

INSERT INTO vendor_account (name, phone, email, city, created_at, updated_at, verified, verifying_code, password, role)
VALUES ('Samantha Dev', '+460342225', 'sam@email.com', 'Bohinj', '2019-04-08 20:21:27.025000',
        '2019-04-08 20:21:27.025000', true, 'dsdas', 'fgfdgadfgfgf', 1)
on conflict do nothing;

/* Create demo dev products*/
INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Bok Choy', 1, 0, 1, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'EURO')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Sorrel Leaves', 1, 0, 2, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'EURO')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Kaffir Lime', 1, 0, 2, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'EURO')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Pimiento', 1, 0, 1, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'EURO')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Mustard Leaves', 1, 0, 0, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'EURO')
on conflict do nothing;

------------------------------------

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Product 1', 1, 0, 1, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 2, 'EURO')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Product 2', 1, 0, 2, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 2, 'EURO')
on conflict do nothing;
