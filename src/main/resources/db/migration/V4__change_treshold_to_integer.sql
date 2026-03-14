ALTER TABLE discounts
    ALTER COLUMN threshold TYPE INTEGER
        USING threshold::INTEGER;