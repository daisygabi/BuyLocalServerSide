/* Create demo dev user*/
INSERT INTO vendor_account (name, phone, email, city, created_at, updated_at, verified, verifying_code, password, role)
VALUES ('Martin Dev', '+560542255', 'dev@email.com', 'Sibiu', '2019-04-08 20:21:27.025000',
        '2019-04-08 20:21:27.025000', true, '214fc', 'dasfdg7guhfdgdfjghfd', 1)
on conflict do nothing;

/* Create demo dev products*/
INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Bok Choy', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Sorrel Leaves', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Kaffir Lime', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Pimiento', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Mustard Leaves', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Capers', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Lotus Stem', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Colocasia', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Parmesan Cheese', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Khoya', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Turnip', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Mulberry', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Lychee', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;

INSERT INTO products (name, min_quantity_per_order, max_quantity_per_order, quantity_type, created_at, updated_at,
                      in_stock, price, vendor_id, currency)
VALUES ('Indian Gooseberry', 1, 0, 4, '2020-04-16T14:12:39.000+0000', '2020-04-16T14:12:39.000+0000', true, 12, 1, 'Eur')
on conflict do nothing;