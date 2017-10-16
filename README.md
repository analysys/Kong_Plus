# 易观Kong Plus (A Tool for Operate Kong)

易观根据自身需求定制的封装Kong的相关功能的简单Portal。

Kong的操作是通过命令来实现的，比较抽象，对于真正部署、实施、监控都不太方便。Kong的免费版本也没有提供可以操作Admin API的可视化界面，因此，为了方便开发和集成，我们自己开发了一套简单的界面来帮助公司开发人员方便地集成自己的服务。界面功能包含API的创建、认证插件的添加、其他扩展插件的添加、API管理、Kong服务器状态查询以及API调用统计等功能。功能也在随着需求的增加而不断地丰富完善。


首页
![index](/img/index.jpg)

添加API
![api](/img/addapi.jpg)

添加消费者
![api](/img/addconsumer.jpg)

服务器信息
![info](/img/sinfo.jpg)

调用次数统计
![call](/img/count1.jpg)

调用次数设置
![call](/img/count2.jpg)


------

## 说明
 - src/main/java: 程序源码
 - src/main/resources: 配置文件
 - src/main/webapp: 页面及资源文件
 - sql: 数据库及表创建脚本

## 运行
 - 1 初始化数据库： 运行sql下的.sql文件，初始化数据库以及表
 - 2 Eclipse导入maven项目
 - 3 修改src/resources下的conf.properties中相关地址的配置
 - 4 打包项目，发布至web服务器运行
