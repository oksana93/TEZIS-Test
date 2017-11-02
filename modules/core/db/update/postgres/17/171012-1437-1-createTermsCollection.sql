create table TEZISTEST_TERMS_COLLECTION (
    ID uuid,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    VERSION integer,
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TERM varchar(4000),
    DEADLINE timestamp,
    INVOICE_ID uuid,
    --
    primary key (ID)
);
