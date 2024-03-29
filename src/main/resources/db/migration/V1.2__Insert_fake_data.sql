insert into accenture.transaction (
    currency ,
    amount ,
    sender_id ,
    receiver_id ,
    description,
    status ) values
    ('EUR', 10000, 1, 2,'Bolletta Luce', 'PENDING'),
    ('EUR', 10006, 4, 2,'Bolletta Gas', 'PENDING'),
    ('EUR', 10000, 1, 2,'Bolletta Luce', 'PENDING'),
    ('EUR', 10100, 1, 2,'Bolletta Luce', 'CANCELED'),
    ('EUR', 20200, 1, 2,'Pagamento vacanze', 'PENDING'),
    ('EUR', 20301, 3, 4,'Bolletta Luce', 'CANCELED'),
    ('EUR', 20401, 1, 2,'Cinema', 'COMPLETED'),
    ('EUR', 20501, 1, 2,'Bolletta Luce', 'COMPLETED'),
    ('EUR', 20601, 1, 2,'Bolletta Luce', 'PENDING'),
    ('EUR', 20701, 1, 2,'Bolletta Luce', 'CANCELED'),
    ('EUR', 20801, 1, 2,'Bolletta Luce', 'PENDING'),
    ('EUR', 20001, 2, 4,'Ristorante', 'PENDING'),
    ('EUR', 20001, 1, 2,'Acquisto amazon', 'PENDING'),
    ('GBP', 20010, 1, 3,'Viaggio Londra', 'COMPLETED'),
    ('EUR', 20001, 2, 1,'', 'PENDING'),
    ('EUR', 20001, 1, 2,'Bolletta Luce', 'CANCELED'),
    ('GBP', 20001, 1, 2,'Bolletta Luce', 'PENDING'),
    ('EUR', 20001, 1, 1,'Bolletta Luce', 'PENDING'),
    ('EUR', 20001, 1, 2,'Pizzeria', 'PENDING'),
    ('EUR', 20001, 1, 2,'Bolletta Luce', 'CANCELED'),
    ('USD', 20000, 1, 2,'Bolletta Luce', 'PENDING'),
    ('EUR', 10000, 4, 1,'Bolletta Luce', 'PENDING'),
    ('EUR', 10000, 3, 2,'Ho finito le idee', 'CANCELED'),
    ('EUR', 10000, 1, 2,'Affitto febbraio', 'PENDING')
