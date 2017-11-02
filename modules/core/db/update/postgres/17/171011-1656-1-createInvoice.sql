create table TEZISTEST_INVOICE (
    CARD_ID uuid,
    --
    NUMBER_ varchar(50),
    BUDGET_ITEM_ID uuid,
    CONTRACTOR_ID uuid,
    PAYMENT_DATE date,
    AMOUNT varchar(50),
    PAYMENT_CONDITIONS varchar(4000),
    --
    primary key (CARD_ID)
);
