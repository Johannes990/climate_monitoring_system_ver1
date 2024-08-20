# climate_monitoring_system_ver1
A system that monitors and logs climate data from sensor output and gives warnings when environment variables are outside allowable limits


## Setting up DataBase

Assuming here that server connection is up and running with MS SQL...

1. go to <clone_folder>/database/sql
2. run CreateDB.sql commands
3. run CreateSchemaClimateInfo.sql command
4. run CreateSchemaUserAuth.sql command
5. run CreateTablesClimateInfo.sql commands
6. run CreateTablesUserAuth.sql commands

## Setting up spring boot

Currently spring.datasource and spring.security credentials are set to be read from system or user environment variables. Find a way to set these variables for you specific OS.

1. create DB_URL variable and set the value to be your specific server connection url
2. create DB_USERNAME variable and set the value to be the SQL Server Authentication user name
3. create DB_PASSWORD variable and set the value to be the SQL Server Authentication password for that user
4. create SECURITY_USERNAME variable and set the value to be whatever username you will enter for the Spring-security login screen
5. create SECURITY_PASSWORD variable and set the value to be the password you want to enter on Spring-secutiry login screen

This should be sufficient to get app up and running and logged in
