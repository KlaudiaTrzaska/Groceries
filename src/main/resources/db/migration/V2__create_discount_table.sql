CREATE TYPE type_of_discount AS ENUM ('percentage', 'gratis');

CREATE TABLE discounts (
                          product_name VARCHAR(255),
                          threshold NUMERIC,
                          type_of_discount type_of_discount,
                          discount DOUBLE PRECISION
);