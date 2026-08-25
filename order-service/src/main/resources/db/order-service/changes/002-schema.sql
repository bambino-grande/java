INSERT INTO users(id, keycloak_id, name, type, created_at, updated_at, removed)
VALUES
    ('10000000-0000-0000-0000-000000000001', '90000000-0000-0000-0000-000000000001','petya', 'USER', NOW(), NOW(), FALSE),
    ('10000000-0000-0000-0000-000000000002', '90000000-0000-0000-0000-000000000002','petya2', 'MANAGER', NOW(), NOW(), FALSE),
    ('10000000-0000-0000-0000-000000000003', '90000000-0000-0000-0000-000000000003','petya3', 'WAREHOUSE_ADMIN', NOW(), NOW(), FALSE),
    ('10000000-0000-0000-0000-000000000004', '90000000-0000-0000-0000-000000000004','petya4', 'ADMIN', NOW(), NOW(), FALSE),
    ('10000000-0000-0000-0000-000000000005', '90000000-0000-0000-0000-000000000005','petya5', 'USER', NOW(), NOW(), FALSE);

INSERT INTO in_stock_car_orders(
    id,
    client_id,
    manager_id,
    car_id,
    stage,
    created_at,
    updated_at,
    removed
)

VALUES(
       '70000000-0000-0000-0000-000000000001',
       '10000000-0000-0000-0000-000000000001',
       '10000000-0000-0000-0000-000000000002',
       '40000000-0000-0000-0000-000000000001',
       'Placed',
       NOW(),
       NOW(),
       FALSE
      );

INSERT INTO complectation_car_orders(
    id,
    client_id,
    manager_id,
    car_id,
    stage,
    created_at,
    updated_at,
    removed
)

VALUES(
          '80000000-0000-0000-0000-000000000001',
          '10000000-0000-0000-0000-000000000001',
          '10000000-0000-0000-0000-000000000002',
          '40000000-0000-0000-0000-000000000002',
          'Placed',
          NOW(),
          NOW(),
          FALSE
      );

