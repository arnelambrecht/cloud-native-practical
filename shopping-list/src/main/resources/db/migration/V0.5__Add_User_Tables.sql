create table USERS (
    USERNAME VARCHAR(50) NOT NULL PRIMARY KEY,
    PASSWORD VARCHAR(500) NOT NULL,
    ENABLED BOOLEAN NOT NULL
);

create table AUTHORITIES (
    USERNAME VARCHAR(50) NOT NULL,
    AUTHORITY VARCHAR(50) NOT NULL,
    CONSTRAINT FK_AUTHORITIES_USERS FOREIGN KEY (USERNAME) REFERENCES USERS(USERNAME)
);

create unique index IX_AUTH_USERNAME on AUTHORITIES (USERNAME, AUTHORITY);