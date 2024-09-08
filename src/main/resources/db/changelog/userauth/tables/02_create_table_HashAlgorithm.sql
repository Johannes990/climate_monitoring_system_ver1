--liquibase formatted sql

--changeset JohannesJyrgenson20240828:1
DROP TABLE IF EXISTS userauth.HashAlgorithm;
CREATE TABLE userauth.HashAlgorithm (
    HashAlgorithmID INT IDENTITY(1, 1) NOT NULL,
    HashAlgorithmName NVARCHAR(50) UNIQUE NOT NULL,
    CONSTRAINT PK_HashAlgorithm_HashAlgorithmID PRIMARY KEY CLUSTERED (HashAlgorithmID)
);

--changeset JohannesJyrgenson20240901:2
INSERT INTO userauth.HashAlgorithm (HashAlgorithmName)
VALUES ('bcrypt');
