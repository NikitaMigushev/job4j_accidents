CREATE TABLE ACCIDENT (
  id serial primary key,
  name varchar(255),
  description text,
  address text,
  accident_type_id int references ACCIDENT_TYPE(id)
);