CREATE TABLE TRANSACTIONS  (
    ID UUID PRIMARY KEY,
    ACCOUNT_NUMBER VARCHAR(255),
    AMOUNT DECIMAL(30,2),
    DESCRIPTION VARCHAR(255),
    CREATE_DATE DATE,
    CUSTOMER_ID INT
 );