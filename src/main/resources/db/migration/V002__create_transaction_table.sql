create table account_transaction_projection
(
    id                                text                     not null,
    account_id                        text                     not null,
    type                              text                     not null,
    status                            text                     not null,
    amount_delta                      decimal(20, 2)           not null,
    payee_account_id                  text,
    payer_account_id                  text,

    PRIMARY KEY (id, account_id),
    FOREIGN KEY (account_id) REFERENCES bank_account_projection (id)
);