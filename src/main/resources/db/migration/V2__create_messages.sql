CREATE TABLE message
(
    id              BIGSERIAL PRIMARY KEY,
    order_id        BIGINT REFERENCES orders (id),
    business_id     BIGINT                   NOT NULL,
    user_id         BIGINT                   NOT NULL,
    message_content TEXT,
    created         TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);


CREATE INDEX message_table_order_id_idx ON message (order_id);
CREATE INDEX message_table_business_id_idx ON message (business_id);
CREATE INDEX message_table_user_id_idx ON message (user_id);
