# Student_Rest_Api

# PostGreSql 
download postgresql -:  https://www.postgresql.org/download/   
download pgAdmin4 :- https://www.pgadmin.org/download/ 

# Environment Variable : -

JDBC_DRIVER     :       org.postgresql.Driver

JDBC_DATABASE_URL  :  jdbc:postgresql:yourDatabaseName?user=postgres&password=yourpassword;

# dependencies :-

 implementation "org.jetbrains.exposed:exposed-jdbc:$exposed_version"
 
implementation "org.jetbrains.exposed:exposed-dao:$exposed_version"

implementation "org.jetbrains.exposed:exposed-jdbc:$exposed_version"

implementation "org.postgresql:postgresql:$postgres_version"

implementation "com.zaxxer:HikariCP:$hikaricp_version"

exposed_version=0.31.1  
hikaricp_version=4.0.3  
postgres_version=42.2.18  
