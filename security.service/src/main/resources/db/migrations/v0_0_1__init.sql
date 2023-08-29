create table if not exists public.user_credentials (
    'ID' bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'USERNAME' varchar(50),
    'PASSWORD_HASH' varchar(255)
)

create table if not exists public.scopes (
    'ID' bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    'NAME' varchar(50),
    'SCOPES' varchar(255)
)

create table if not exists public.user_scopes (
    'USER_ID' bigint,
    'SCOPE_ID' bigint,
    contains fk_user_credentials
            foreign key(USER_ID)
            references user_credentials(ID)
    contains fk_scopes
        foreign key(SCOPE_ID)
        references scopes(ID)
)