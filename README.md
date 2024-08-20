# climate_monitoring_system_ver1
A system that monitors and logs climate data from sensor output and gives warnings when environment variables are outside allowable limits


## Setting up DataBase
(assuming here that server connection is up and running with MS SQL)
1. go to <clone_folder>/database/sql
2. run CreateDB.sql commands
3. run CreateSchemaClimateInfo.sql command
4. run CreateSchemaUserAuth.sql command
5. run CreateTablesClimateInfo.sql commands
6. run CreateTablesUserAuth.sql commands

## gitignore
database/data/climate_system_data.mdf
database/log/climate_system_database_log.ldf