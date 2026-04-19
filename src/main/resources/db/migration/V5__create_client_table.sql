CREATE TABLE client (
                          id VARCHAR(255) PRIMARY KEY,
                          clientName VARCHAR(255),
                          hasLoyaltyCard BOOLEAN DEFAULT FALSE,
                          points INT DEFAULT 0
);