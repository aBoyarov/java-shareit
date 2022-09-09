DROP TABLE IF EXISTS users, requests, items, bookings, comments;

CREATE TABLE IF NOT EXISTS users (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
name VARCHAR(255) NOT NULL,
email VARCHAR(512) NOT NULL,
CONSTRAINT pk_user PRIMARY KEY (id),
CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS requests (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
description VARCHAR(512),
requestor_id BIGINT,
created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
CONSTRAINT pk_requests PRIMARY KEY(id),
CONSTRAINT fk_requests_user FOREIGN KEY(requestor_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS items (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
name VARCHAR(255) NOT NULL,
description VARCHAR(512) NOT NULL,
is_available BOOLEAN,
owner_id BIGINT NOT NULL,
request_id BIGINT,
CONSTRAINT pk_item PRIMARY KEY(id),
CONSTRAINT fk_item_user FOREIGN KEY(owner_id) REFERENCES users(id),
CONSTRAINT fk_item_request FOREIGN KEY(request_id) REFERENCES requests(id)
);

CREATE TABLE IF NOT EXISTS bookings (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
start_date TIMESTAMP WITHOUT TIME ZONE,
end_date TIMESTAMP WITHOUT TIME ZONE,
item_id BIGINT NOT NULL,
booker_id BIGINT NOT NULL,
status VARCHAR(64),
CONSTRAINT pk_bookings PRIMARY KEY (id),
CONSTRAINT fk_bookings_item FOREIGN KEY(item_id) REFERENCES items(id),
CONSTRAINT fk_bookings_user FOREIGN KEY(booker_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS comments (
id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
text VARCHAR(2000),
item_id BIGINT,
author_id BIGINT,
created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
CONSTRAINT pk_comments PRIMARY KEY(id),
CONSTRAINT fk_comments_item FOREIGN KEY(item_id) REFERENCES items(id),
CONSTRAINT fk_comments_user FOREIGN KEY(author_id) REFERENCES users(id)
);