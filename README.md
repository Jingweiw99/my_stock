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

## (5) 添加swagger的ui界面
![image](https://github.com/Jingweiw99/my_stock/assets/101159761/8a7da454-bc47-4554-9e4f-9f922f864617)


![image](https://github.com/Jingweiw99/my_stock/assets/101159761/c93504e8-2c7e-4c97-a372-75537cffbc32)

在线测试通过，可以通过其他细致的注解，添加中文注释

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/c8ed2d6c-f160-49ff-ade8-e18d25762ca7)


## (6) 添加knife4j
增强swagger，如：增强ui，导出离线文档。访问/doc.html

添加依赖

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/18604ec6-f00a-48f2-a86d-9eeec514860e)


添加注解

![image](https://github.com/Jingweiw99/my_stock/assets/101159761/4e0c2dc9-6307-44c3-aab0-6cd6b0db28dd)


![image](https://github.com/Jingweiw99/my_stock/assets/101159761/b195f7be-f403-4027-b2e7-3a7d3717ac60)


## (7) 国内大盘指数功能
1. 对展示域数据和常量封装

![对展示域数据和常量数据封装](https://github.com/Jingweiw99/my_stock/assets/101159761/1a6cac1e-5a78-4681-af21-4ad6316d493c)

2. 国内大盘指数功能代码

![国内大盘指数功能代码](https://github.com/Jingweiw99/my_stock/assets/101159761/c839d270-2e72-4674-a5ca-6648bb12130a)

## (8) 国内板块指数功能

![国内板块指数功能](https://github.com/Jingweiw99/my_stock/assets/101159761/1539773b-95a8-4f30-9906-6cbf9ed173f5)

## (9) 涨幅榜更多数据功能

略

## (10) 涨跌停数据统计功能

略

## (11) easyexcel导出分页数据

![excel导出分页数据](https://github.com/Jingweiw99/my_stock/assets/101159761/f2e6a90e-8b46-4575-be8d-fc4c7cecae3d)


## (12) 股票成交量对比功能

略

## (13) 个股分时涨跌幅度统计功能

![个股分时涨跌幅度统计功能](https://github.com/Jingweiw99/my_stock/assets/101159761/a65be228-705f-4af9-90ce-d24b0900e14a)


## (14) 个股分时图行情功能

略

## (15) 日k线单mapper接口实现

![日k线单mapper接口实现](https://github.com/Jingweiw99/my_stock/assets/101159761/08c4f287-b60e-4cbe-9a20-a4f2bc92f1cb)

## (16) 对日k线功能拆分

后续分表分库的时候，比较方便

![拆分后的日k线png](https://github.com/Jingweiw99/my_stock/assets/101159761/ab6d47af-7802-4875-89fd-e09755d2bf30)










































