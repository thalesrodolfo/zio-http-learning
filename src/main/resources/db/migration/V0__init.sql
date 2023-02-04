CREATE TABLE coins (
    coin_id SERIAL PRIMARY KEY,
    coin_name VARCHAR(80) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE rewards (
    reward_id SERIAL PRIMARY KEY,
    data DATE NOT NULL,
    origem INT NOT NULL,
    coin_id INT NOT NULL,
    preco FLOAT NOT NULL,
    quantidade FLOAT NOT NULL,
    total FLOAT NOT NULL,
    acao INT NOT NULL,
    comentario VARCHAR(300),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

ALTER TABLE rewards
ADD CONSTRAINT fk_coin FOREIGN KEY(coin_id) REFERENCES coins(coin_id);

