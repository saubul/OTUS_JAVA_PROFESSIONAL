create table accounts (
    id varchar(36) primary key,
    number varchar(12) not null,
    client_id varchar(10),
    balance decimal(10, 2),
    is_block numeric(1)
);

create table transfers (
    id varchar(36) primary key,
    client_id varchar(10),
    target_client_id varchar(10),
    source_account_id varchar(36) references accounts,
    target_account_id varchar(36) references accounts,
    amount decimal(10, 2),
    message varchar(255)
);