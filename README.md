## (1) Docker添加redis环境
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

## (2) 项目添加redis
application-cache.yml

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/be031062-5370-4867-9bc0-ddab5c0d09ac)


![image](https://github.com/Jingweiw99/my_stock/assets/101159761/3e3dfc6f-801c-4566-adb5-f5541be8131c)

application.yml中添加


![image](https://github.com/Jingweiw99/my_stock/assets/101159761/88cfcf1b-622e-4d32-a5a1-f8e88339921f)

装载redisTemplate bean

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/9a7c623d-3f3f-47e7-affd-0932c3111dea)

通过测试，集成redis完成

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/efb08f0d-fdbd-4fad-909d-333275e48ff3)

## (3) 雪花算法
那个机房的那台机器在什么样的时间点生成的，如果在相同的时间点并发生成，就加入序列号。

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/f9549562-4e02-495d-8829-a831a1e5e457)

CommonConfig添加

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/61e31a86-8893-4e4e-b824-71288180c32e)

通过生成不同的序列id测试

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/b391e898-ebc8-4ece-b8c5-2adcd3992563)


## (4) 生成校验码，完善登录功能
1. 导入二维码的依赖

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/a3bfc3e2-fe07-4584-9282-a1ddbb6ef869)

2. 业务代码

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/e12ef83d-0fcd-4e8c-8ce6-d19ae7305a7f)

3. 测试验证

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/848085fa-8adc-40ee-bfbe-40e1d8cddac7)

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/bee4e3aa-45fb-4add-abe3-0a4e82de456c)

4. 完善登录

前后台验证码，sessionId校验

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/294f9a6c-6c5b-46e4-ae5c-20f7021ae63d)

其中还抽取了一下常量
























































