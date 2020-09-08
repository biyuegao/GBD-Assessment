CREATE TABLE experience(
experience_id SERIAL PRIMARY KEY,
player_id INT NOT NULL,
balance INT,
created_at_timestamp TIMESTAMP NOT NULL DEFAULT now(),
updated_at_timestamp TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE experience_log(
experience_log_id VARCHAR(36) NOT NULL,
experience_id INT NOT NULL,
player_id INT NOT NULL,
amount INT,
type CHARACTER VARYING(9),
remarks VARCHAR(255),
created_at_timestamp TIMESTAMP NOT NULL DEFAULT now(),
PRIMARY KEY (experience_log_id),
constraint fk_experience_id FOREIGN KEY (experience_id) REFERENCES experience (experience_id)
);