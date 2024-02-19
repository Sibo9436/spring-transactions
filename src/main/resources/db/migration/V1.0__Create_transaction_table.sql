create schema if not exists accenture authorization accenture_app;

create type accenture.transaction_status as enum(
    'PENDING',
    'CANCELED',
    'COMPLETED',
    'DELETED'
);

create table if not exists accenture.transaction (
    transaction_id serial primary key ,
    currency varchar(5),
    amount bigint,
    transaction_time timestamp,
    sender_id int,
    receiver_id int,
    description text,
    status varchar(20)
);