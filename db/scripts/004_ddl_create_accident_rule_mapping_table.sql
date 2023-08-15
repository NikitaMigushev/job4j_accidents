CREATE TABLE ACCIDENT_RULE_MAPPING (
  id serial primary key,
  accident_id int references ACCIDENT(id),
  rule_id int references ACCIDENT_RULE(id),
  unique (accident_id, rule_id)
);