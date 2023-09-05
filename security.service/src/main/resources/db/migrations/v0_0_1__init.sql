CREATE TABLE IF NOT EXISTS public.users (
    id SERIAL PRIMARY KEY NOT NULL,
    username varchar(50),
    password_hash varchar(255)
)
TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.permissions (
    id SERIAL PRIMARY KEY NOT NULL,
    name varchar(255)
)
TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.user_permissions (
    user_id bigint NOT NULL,
    permission_id bigint NOT NULL,
    PRIMARY KEY (user_id, permission_id),
    FOREIGN KEY (user_id) references users(id),
    FOREIGN KEY (permission_id) references permissions(id)
)
TABLESPACE pg_default;