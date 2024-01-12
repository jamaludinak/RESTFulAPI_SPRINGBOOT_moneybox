CREATE DATABASE moneybox;

USE moneybox;

CREATE TABLE users
(
    username         VARCHAR(100) NOT NULL,
    password         VARCHAR(100) NOT NULL,
    name             VARCHAR(100) NOT NULL,
    token            VARCHAR(100),
    token_expired_at BIGINT,
    PRIMARY KEY (username),
    UNIQUE (token)
) ENGINE InnoDB;

CREATE TABLE kategori
(
    nama  VARCHAR(100)                      NOT NULL,
    jenis BOOLEAN                           NOT NULL,
    PRIMARY KEY (nama)
) ENGINE InnoDB;

CREATE TABLE transaksi
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    keterangan  VARCHAR(255) NOT NULL,
    nominal     DOUBLE       NOT NULL,
    tanggal     DATE         NOT NULL,
    username    VARCHAR(100) NOT NULL,
    kategori VARCHAR(100) NOT NULL,
    CONSTRAINT fk_transaksi_user FOREIGN KEY (username) REFERENCES users (username),
    CONSTRAINT fk_transaksi_kategori FOREIGN KEY (kategori) REFERENCES kategori (nama)
) ENGINE InnoDB;

