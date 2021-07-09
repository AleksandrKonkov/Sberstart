create table CLIENT (ID BIGINT auto_increment primary key,LOGIN  VARCHAR(15) not null);

create table  ACCOUNT (ID BIGINT auto_increment, NUMBER VARCHAR(25),
           BALANCE DECIMAL(15,2) not null, CLIENT_ID BIGINT not null
           references CLIENT (ID), constraint ACCOUNT_PK primary key (ID));

    create table CARD (ID BIGINT auto_increment, NUMBER VARCHAR(16) not null,
        ACCOUNT_ID BIGINT not null, constraint CARD_PK primary key (ID), constraint FK_ACCOUNT_ID
            foreign key (ACCOUNT_ID) references ACCOUNT (ID)
    );



