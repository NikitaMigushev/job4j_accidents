CREATE TABLE user_authority_mapping (
  id bigserial PRIMARY KEY,
  user_id bigint NOT NULL REFERENCES accident_user(id),
  authority_id bigint NOT NULL REFERENCES user_authority (id),
  UNIQUE (user_id, authority_id)
);