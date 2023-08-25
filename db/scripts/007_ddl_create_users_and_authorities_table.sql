create table accident_user(
    id bigserial primary key,
	username varchar(50) not null,
	password varchar(500) not null,
	enabled boolean not null
);

create table user_authority (
    id bigserial primary key,
	authority varchar(50) not null
);

INSERT INTO user_authority (authority) VALUES ('ROLE_USER');
INSERT INTO user_authority (authority) VALUES ('ROLE_ADMIN');