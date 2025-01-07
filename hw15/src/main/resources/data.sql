insert into accounts (id, number, client_id, balance, is_block) values
('1455763f-8d01-4911-88fb-446baff756fa', '408178100001', '1000000001', 100.00, 0),
('c138d9d3-3da1-4e1b-b55e-b223eeac350a', '408178100002', '1000000002', 150.00, 1);

insert into transfers (id, client_id, target_client_id, source_account_id, target_account_id, amount, message) values
('bde76ffa-f133-4c23-9bca-03618b2a94b2', '1000000001', '1000000002', '1455763f-8d01-4911-88fb-446baff756fa', 'c138d9d3-3da1-4e1b-b55e-b223eeac350a', 100.00, 'Тестовый перевод'),
('32ebb2eb-ed35-4baa-b500-b7f6535e4c88', '1000000002', '1000000001', 'c138d9d3-3da1-4e1b-b55e-b223eeac350a', '1455763f-8d01-4911-88fb-446baff756fa', 50.00, 'Обратный тестовый перевод');