use docker start mysql service
```
docker run --name my-mysql2 -e MYSQL_ROOT_PASSWORD=123 -e MYSQL_DATABASE='content_center' -p 3307:3306 -d mysql
```
use flyway init DB(should better click the plugin on IDEA)
```
mvn flyway:clean flyway:migrate
```