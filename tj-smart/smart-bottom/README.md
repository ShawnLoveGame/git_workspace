#smart-buttom

-------------------------------------------------
重庆移动不限量小程序

springboot项目


###项目部署

1）lombok
```
使用lombok进行代码简化
```

2）gradle
```
gradle clean build   -> 在build->libs目录下会生成对应的jar

```

3）运行脚本
```
nohup java -Xms2g -Xmx2g -jar smart-bottom.jar --spring.profiles.active=dev --server.port=8081 &

nohup java  -jar smart-bottom.jar --spring.profiles.active=dev --server.port=8081 &
```

4）数据库连接池
```
 hikari -> mysql
```

5）缓存服务
```
redis -> jedis客户端
```

### Swagger文档

访问：http://127.0.0.1:8090/card/swagger-ui.html


### 日志说明

_每天生成对应的日志文件，服务器存放最近90天的日志记录_