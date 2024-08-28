# climate_monitoring_system_ver1
A system that monitors and logs climate data from sensor output and 
gives warnings when environment variables are outside allowable limits


## Docker database creation (assuming docker desktop has been installed)
1. Add ```com.microsoft.sqlserver:mssql-jdbc``` to build.gradle dependencies under 
```implementation``` and ```runtimeonly```.
2. Create compose.yaml in project root.
3. Copy this to compose.yaml:
    ```
    version: '3.4'
    services:
      mssql:
        container_name: your_db_name
        hostname: your_db_name
        image: mcr.microsoft.com/mssql/server:2022-latest
        environment:
          ACCEPT_EULA: 'Y'
          MSSQL_SA_PASSWORD: your_sa_password
          MSSQL_DATA_DIR: /var/opt/mssql/data
          MSSQL_PID: 'Developer' 
          MSSQL_TCP_PORT: 1433 
        ports: 
          - "1455:1433"
        volumes:
          - ./data:/var/opt/mssql/data
          - ./log:/var/opt/mssql/log
          - ./secrets:/var/opt/mssql/secrets
    ```
   - Here we used port mapping 1455:1433, but can use 1433 which is the default port
   for MS SQL Server as well.
   - Note about MS SQL Server passwords: Should be 8 characters long, should not contain 
   username and contain at least 3 of the following: latin lowercase letters (a - z), 
   latin uppercase letters (A - Z), base 10 digits (0 - 9), non-alphanumeric characters such 
   as '!', '$', "@", '%' etc. Max length 128 characters.
4. Run ```docker-compose up -d```.


## Connecting to database
1. Open database view in IntelliJ.
2. Select new data source...
3. Select Microsoft SQL Server as data source.
4. Make sure to download drivers for mssql if needed.
5. Set the following:
    ```
    host: localhost
    port: 1455
    username: sa
    password: your_sa_password
    url (should be automatically correct): jdbc:sqlserver://localhost:1455
    ```
6. Test connection. Should be OK. If not, then get drunk.


## Liquibase setup
1. Download Liquibase.
2. Set add to PATH in liquibase installer.
3. Open terminal, run ```liquibase --help```.
   - If command not recognized then Restart computer. This will read
   the new env variables where liquibase should now be set.
4. Once ```liquibase``` is a recognized command, run:
```liquibase init project-new```. Use setting ```c``` for customization
5. Set path to <project_folder>/src/main/resources/liquibase/
6. Enter name for changelog file (e.g. ```changelog_1_0```), 
set ```sql``` as extension.
7. set defaults file name as: ```liquibase.properties```.
8. Give JDBC url: database_connection_url, e.g. ```jdbc:sqlserver://localhost:1455```.
9. Username ```sa```.
10. Password ```your_sa_password```.
11. Go to <project_folder>/src/main/resources/liquibase/liquibase.properties
and change the target database url to database_connection_url + ```;trustServerCertificate=true;``` to
skip annoying SSL checks. Example URL: ```jdbc:sqlserver://localhost:1455;trustServerCertificate=true;```.

## Setting up spring boot
~~Currently spring.datasource and spring.security credentials are set to be 
read from system or user environment variables. Find a way to set these variables 
for you specific OS.~~

~~1. Create DB_URL variable and set the value to be your specific server connection url~~
~~2. Create DB_USERNAME variable and set the value to be the SQL Server Authentication
user name~~
~~3. Create DB_PASSWORD variable and set the value to be the SQL Server Authentication 
password for that user~~
~~4. Create SECURITY_USERNAME variable and set the value to be whatever username you
will enter for the Spring-security login screen~~
~~5. Create SECURITY_PASSWORD variable and set the value to be the password you want 
to enter on Spring-secutiry login screen~~
1. Create application-db.properties file in <project_folder>/src/main/resources/.
2. Set spring.datasource.url to database_connection_url + ```;trustServerCertificate=true;``` to
skip annoying SSL checks. Example URL: ```jdbc:sqlserver://localhost:1455;trustServerCertificate=true;```.
3. Set spring.datasource.username to 'sa'.
4. Set spring.datasource.password to your_sa_password.
5. Set spring.security.user.name and spring.security.user.pass both to be the credentials
you want to enter on spring boot splash screen.
6. Create application.properties in <project_foler>/src/main/resources/
or copy the application.properties file from project root to that location.
7. Copy this ti application.properties:
```
spring.application.name=climate-monitoring-system

spring.config.import=optional:classpath:application-db.properties
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver

spring.liquibase.change-log=classpath:/liquibase/your_changelog_filename.sql

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.SQLServerDialect

```

This should be sufficient to get app up and running and logged in.


### Points about database management:
  - schema, table and field names all pascalCase: ```myCoolSchema.myCoolTable```
  - primary key constraint naming: ```PK_<tableName>_<fieldName>```.
  For example:
   ```sql
      CREATE TABLE schema.myTable (
          rowId INT IDENTITY(1, 1) NOT NULL,
          rowDesc NVARCHAR(MAX),
          CONSTRAINT PK_myTable_rowId PRIMARY KEY (rowId)
      );
   ```
   - foreign key constraint naming: ```FK_<childTableName>_<parentTableName>```
   For example:
   ```sql
      CREATE TABLE schema.location (
          locationId INT IDENTITY(1, 1) NOT NULL,
          locationName NVARCHAR(MAX) UNIQUE,
          CONSTRAINT PK_location_locationId PRIMARY KEY (locationId),
      );
   
      CREATE TABLE schema.house (
          houseId INT IDENTITY(1, 1) NOT NULL,
          locationId INT NOT NULL,
          CONSTRAINT PK_house_houseId PRIMARY KEY (houseId),
          CONSTRAINT FK_house_location FOREIGN KEY (locationId) 
              REFERENCES schema.location(locationId)
      );
   ```
  - changeset identification:
    - name part: firstNameLastNameYYYYMMDD ```--changeset JohnDoe20240828```
    - id part: unique within file, not unique across files. Reason being not wanting to deal
with keeping track of id-s across several files which would be a mess. Tracking by file is sufficient
as long as file names are also unique.