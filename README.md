## Docker添加redis环境
1. 查看下载的镜像没有redis

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/6086708e-f4ab-4f72-a3f2-6f373d69b31d)

2. dockerhub上pull

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/d08dfe9f-8770-4a2c-9aa1-128c1366620e)

3 . 开启并运行一个redis容器

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/29734606-cf23-4946-b56d-3a62e1c4a7d2)

镜像名 redis
宿主机端口左，容器端口右
-d redis redis-server  表示redis镜像(redis:latest），redis服务
开启AOF持久化数据

redis上面没有配置密码，直接使用

## 项目添加redis
application-cache.yml

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/be031062-5370-4867-9bc0-ddab5c0d09ac)


![image](https://github.com/Jingweiw99/my_stock/assets/101159761/3e3dfc6f-801c-4566-adb5-f5541be8131c)

application.yml中添加


![image](https://github.com/Jingweiw99/my_stock/assets/101159761/88cfcf1b-622e-4d32-a5a1-f8e88339921f)

装载redisTemplate bean

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/9a7c623d-3f3f-47e7-affd-0932c3111dea)

通过测试，集成redis完成

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/72b95994-088e-4bbe-89cb-b8e1db67973e)




