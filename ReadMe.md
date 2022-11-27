## 从0到1做一个脚手架 day01
开局只有一个idea。和一台华为云服务器，没准备即兴搞，看我会不会翻车呢！
1、环境准备
git
maven
apipost
2、我们为什么要做脚手架
首先我们平时在公司都是写业务代码，小公司crud。公司的leader，架构设计好的。你去怎么想，怎么做，这就是脚手架的意义。
3、建立git仓库
4、设置阿里镜像
5、集成springboot
6、集成springmvc
7、linux下安装mysql
## 从0到1做一个脚手架 day02
1、服务模块拆解
2、lombok作用演示
3、集成数据库连接池
4、集成mybatis plus
5、集成druid监控
6、集成mybatis的优化器
## 从0到1做一个脚手架 day03
1、mybatis优化器的自动装载
2、mybatis的实体优化
3、全局异常捕获
4、mybatisplus提供的拦截器集成

## 从0到1做一个脚手架 day04
1、easycode插件使用
2、提供一套完整的crud的流程
3、封装一个无状态的page对象
4、集成mapstruct
5、集成一下swagger
6、自定义banner

## 从0到1做一个脚手架 day05
1、安装redis
01、上传redis的安装包
02、tar -xvf redis-5.0.7.tar.gz
03、yum -y install gcc-c++
04、make
05、make PREFIX=/soft/redis install
06、cd /soft/redis/bin
07、./redis-server redis.conf

2、集成redistemplate
3、处理默认序列化乱码问题
4、封装redisutil
5、项目启动自动预热缓存

## 从0到1做一个脚手架 day06
1、复盘一下上周服务器中木马问题
一、集成redis，配置conf文件的时候，我们的bind 0.0.0.0 port 6379 requirepass 
二、收到华为云的通知，服务器冻结。产生的现象，全部连接不上
三、我的服务器每秒的连接，7w多个。scan，流量超大
四、重装服务器
五、redis设置密码

2、封装一个分布式锁
3、分布式锁结合一个业务场景来讲
01、任务调度，集群系统
02、算钱，操作同一数据 money 100 120 120 100
总结：操作同一数据

4、注解缓存的方式
5、快捷注释设置
6、优化一下现有代码

## 从0到1做一个脚手架 day07
1、日志log4j集成，异步日志集成
2、项目架构整体修改

## 从0到1做一个脚手架 day08
1、aop实现一个操作日志记录
2、freemaker实现自定义word格式文件导出
3、工具类模块抽取

## 从0到1做一个脚手架 day09
1、模板模式讲解
2、工厂模式讲解
3、抽象工厂模式讲解
4、策略模式讲解
5、工厂加策略工作实战

## 从0到1做一个脚手架 day10
1、封装一个cacheutil，函数式编程+泛型


## 后续规划

9、导入导出的，写一个例子，封一个工具类
10、集成rabbitmq或者rocketmq
11、mq的例子
13、elk的使用 filebeat+logstash+kibana
14、httpclient的优化
15、shard-jdbc分表
16、mysql的读写分离
18、集成xxljob
20、集成redission
21、aop去实现一个数据加解密
23、幂等性案例
24、乐观锁案例
25、集成spring oauth
26、多数据源集成
27、延时任务实现方式
28、threadlocal怎么用
29、手写一个分布式id 叶子算法
30、远程debug
31、单测案例
32、websocket
33、guava集成
34、本地缓存，guava
35、设计模式的案例
36、skywalking


微服务版(待规划)
1、分布式事务
2、alibaba
3、traceid的链路追踪

领域驱动版