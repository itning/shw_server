# 作业管理系统服务端

**V2版本目前正在编写中**

**[点我](https://github.com/itning/shw_server/tree/master)查看主分支完成版本**

[![GitHub stars](https://img.shields.io/github/stars/itning/shw_server.svg?style=social&label=Stars)](https://github.com/itning/shw_server/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/itning/shw_server.svg?style=social&label=Fork)](https://github.com/itning/shw_server/network/members)
[![GitHub watchers](https://img.shields.io/github/watchers/itning/shw_server.svg?style=social&label=Watch)](https://github.com/itning/shw_server/watchers)
[![GitHub followers](https://img.shields.io/github/followers/itning.svg?style=social&label=Follow)](https://github.com/itning?tab=followers)

[![GitHub issues](https://img.shields.io/github/issues/itning/shw_server.svg)](https://github.com/itning/shw_server/issues)
[![GitHub license](https://img.shields.io/github/license/itning/shw_server.svg)](https://github.com/itning/shw_server/blob/master/LICENSE)
[![GitHub last commit](https://img.shields.io/github/last-commit/itning/shw_server.svg)](https://github.com/itning/shw_server/commits)
[![GitHub release](https://img.shields.io/github/release/itning/shw_server.svg)](https://github.com/itning/shw_server/releases)
[![GitHub repo size in bytes](https://img.shields.io/github/repo-size/itning/shw_server.svg)](https://github.com/itning/shw_server)
[![HitCount](http://hits.dwyl.io/itning/shw_server.svg)](http://hits.dwyl.io/itning/shw_server)
[![language](https://img.shields.io/badge/language-JAVA-green.svg)](https://github.com/itning/shw_server)

# 架构

微服务架构。使用Spring Cloud开发部署。

项目采用微服务开发，采用JWT技术作为身份验证手段，前后端分离。目前前后端交互使用JSON方式，[GraphQL](https://graphql.org/)和[protobuf](https://github.com/protocolbuffers/protobuf)通信方式正在开发中。

![jiagou](https://raw.githubusercontent.com/itning/shw_server/v2/pic/kk.png)

![jiagou](https://raw.githubusercontent.com/itning/shw_server/v2/pic/jiagou.png)

技术栈：

前端：Vue.JS

后端：Spring Cloud

数据库：MongoDB

中间件：RabbitMQ

| 技术栈  | 描述                                                         |
| ------- | ------------------------------------------------------------ |
| Eureka  | Eureka用于服务的注册与发现。Eureka是Netflix开源的一款提供服务注册和发现的产品，它提供了完整的Service Registry和Service Discovery实现。也是spring Cloud体系中最重要最核心的组件之一。 |
| Ribbon  | Ribbon是Netflix发布的云中间层服务开源项目，其主要功能是提供客户端实现负载均衡算法。 |
| Feign   | Feign支持服务的调用以及均衡负载。Feign是从Netflix中分离出来的轻量级项目，能够在类接口上添加注释，成为一个REST API 客户端。 |
| Config  | Spring Cloud Config为分布式系统中的外部配置提供服务器和客户端支持。 |
| Bus     | Spring Cloud Bus通过轻量消息代理连接各个分布的节点。         |
| Stream  | Spring Cloud Stream 是一个构建消息驱动微服务的框架。         |
| Zuul    | 微服务网关                                                   |
| Hystrix | Hystrix处理服务的熔断防止故障扩散。Hystrix-dashboard 是一款针对Hystrix进行实时监控的工具，通过Hystrix Dashboard我们可以在直观地看到各Hystrix Command的请求响应时间, 请求成功率等数据。 |
| Sleuth  | Spring Cloud Sleuth为服务之间调用提供链路追踪。通过Sleuth可以很清楚的了解到一个服务请求经过了哪些服务，每个服务处理花费了多长。从而让我们可以很方便的理清各微服务间的调用关系。 |
| WebFlux | 异步非阻塞，函数式编程风格Web框架                            |

服务（模块）简要功能说明：

1. shw-gateway

   网关服务。使用zuul框架实现的微服务网关，该网关拦截未经认证的请求并将请求转发到`shw-security`服务。

2. shw-eureka

   微服务发现与注册服务。

3. shw-security

   安全服务。使用CAS实现SSO进行统一登陆，发放token。token使用JWT技术实现。

4. shw-group

   群组服务。教师所创建的群组CRUD的业务逻辑实现。

5. shw-studentgroup

   学生群组服务。管理学生加入的群组。

6. shw-upload

   上传服务。学生上传作业文件，教师下载文件的业务逻辑服务。

7. shw-work

   作业服务。管理教师创建的作业，作业数据的CRUD。

8. shw-notice

   通知服务。管理教师发布的通知。

9. shw-file

   文件服务。文件的持久化服务。

10. shw-common

    公共模块。包括一些异常，工具类等等
    
11. shw-hystrix-dashboard

    hystrix监控信息服务

消息队列系统:

![msg](https://raw.githubusercontent.com/itning/shw_server/v2/pic/shw_msg.png)

为什么使用消息队列？

1. 异步

   教师与学生的一些操作在异步的环境下进行，增加了系统承载QPS。

2. 解耦

   服务与服务之间耦合度降低。

3. 削峰

   减轻高峰时间数据库压力。

数据库：

![jiagou](https://raw.githubusercontent.com/itning/shw_server/v2/pic/er.png)

# 部署

开启rabbitmq

`docker run -d --hostname localhost --name myrabbit -p 15672:15672 -p 5672:5672 -p 4369:4369 -p 5671:5671 -p 15671:15671 -p 25672:25672 rabbitmq:3.7.14-management`

开启ziplin

`docker run -d -e RABBIT_ADDRESSES=10.0.75.2 -p 9411:9411 openzipkin/zipkin`

# API文档

[HTML版本](https://itning.github.io/shw/)

[Markdown版本](https://github.com/YunShuSoftwareStudio/shw_server/blob/master/api.md)

# 表字段实体属性

## 群组属性(group)

|     属性名     |  类型  |          说明          |    约束    |
| :------------: | :----: | :--------------------: | :--------: |
|       id       | string |      唯一标识群组      | 非空，主键 |
|      name      | string |        群组名称        |    非空    |
|  teacher_name  | string |         教师名         |    非空    |
| teacher_number | string |        教师序号        | 非空，索引 |
|      code      | string | 邀请码（暂时和ID相同） | 非空，索引 |
|  create_time   |  data  |        创建时间        |    非空    |
|  update_time   |  data  |        更新时间        |    非空    |
|                |        |                        |            |
|                |        |                        |            |

## 学生群组属性(student_group)

|     属性名     |  类型  |   说明   |    约束    |
| :------------: | :----: | :------: | :--------: |
| student_number | string |  学生ID  | 非空，主键 |
|    group_id    | string |  群组ID  | 非空，主键 |
|  create_time   |  date  | 创建时间 |    非空    |
|  update_time   |  date  | 更新时间 |    非空    |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |

## 作业属性(work)

|      属性名      |  类型  |         说明         |      约束      |
| :--------------: | :----: | :------------------: | :------------: |
|        id        | string | 作业ID，标识唯一作业 |   非空，主键   |
|     group_id     | string | 群组ID，该作业所属群 |   非空，索引   |
|      enabled      |  bit   |     作业启用状态     | 非空，默认启用 |
|       name       | string |        作业名        |      非空      |
|   create_time    |  date  |       创建时间       |      非空      |
|   update_time    |  date  |       更新时间       |      非空      |
| file_name_format | string |      文件名规范      |      非空      |
|                  |        |                      |                |
|                  |        |                      |                |

## 上传属性(upload)

|     属性名     |  类型  |   说明   |    约束    |
| :------------: | :----: | :------: | :--------: |
| student_number | string |  学生ID  | 非空，主键 |
|    work_id     | string |  作业ID  | 非空，主键 |
|      mime      | string | 文件类型 |    非空    |
|      size      | double | 文件大小 |    非空    |
|  create_time   |  date  | 上传时间 |    非空    |
|  update_time   |  date  | 修改时间 |    非空    |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |

## 通知属性(notice)

|       属性名       |  类型  |       说明       |    约束    |
| :----------------: | :----: | :--------------: | :--------: |
|         id         | string | 通知ID，唯一标识 | 非空，主键 |
| invite_people_name | string |      邀请人      |    非空    |
|  invite_group_id   | string | 邀请加入的群组ID |    非空    |
|   student_number   | string |   被邀请人学号   | 非空，索引 |
|    create_time     |  date  |     创建时间     |    非空    |
|    update_time     |  date  |     修改时间     |    非空    |
|                    |        |                  |            |
|                    |        |                  |            |
|                    |        |                  |            |

# 接口

> 统一响应格式:
>
> Content-Type: application/json
>
> 状态吗:
>
> 400 请求参数错误
>
> 401 需要登陆
>
> 403 权限不足
>
> 404 资源不存在
>
> 405 不支持该请求方法
>
> 500 服务器出错
>
> 503 服务不可用
>

>Json格式规范:
>
>```json
>{
>    "code": -1,
>    "msg": "该活动不存在",
>    "data":"响应数据"
>}
>```

## 公共

### 获取登陆用户信息

请求方法 GET

请求路径 /user

响应状态 200(请求成功) 

## 群组

### 获取学生的所有群组

请求方法 GET

请求路径 /student/groups

响应状态 200(请求成功) 

```
{
    "code": 200,
    "msg": "请求成功",
    "data":[{
        "id":"a3f65ga621v3za6",
        "name":"群组A",
        ...
    }, {   } ]
}
```



###学生添加群组

请求方法 POST

请求路径 /student/group

响应状态 201(创建成功)

| 请求参数 |    说明    |
| :------: | :--------: |
|   code   | 群组邀请码 |



### 学生退出群组

请求方法 DELETE

请求路径 /student/group/{id}

响应状态 204(退出成功) 404(ID不存在)

| 请求参数 |  说明  |
| :------: | :----: |
|    id    | 群组id |



### 获取教师的所有群组

请求方法 GET

请求路径 /teacher/groups

响应状态 200(请求成功)

### 教师创建群组

请求方法 PUT

请求参数 name 

响应状态 201(创建成功)

| 请求参数 |   说明   |
| :------: | :------: |
|   name   | 群组名称 |



### 教师解散群组

请求方法 DELETE

请求路径 /teacher/group/{id}

响应状态 204(解散成功) 404(ID不存在)

| 请求参数 |  说明  |
| :------: | :----: |
|    id    | 群组ID |



### 教师修改群信息

请求方法 PATCH

请求路径 /teacher/group/{id}

响应状态 204(修改成功) 404(ID不存在)

| 请求参数 |   说明   |
| :------: | :------: |
|    id    |  群组ID  |
|   name   | 新群名称 |



### 管理员查询所有群信息

请求方法 GET

请求路径 /admin/groups

响应状态 200(查询成功)

| 请求参数 |         说明         |
| :------: | :------------------: |
|  limit   | 指定返回结果数量 0~n |
|   page   |   指定返回页码1~n    |



### 管理员根据群名称模糊搜索

请求方法 GET

请求路径 /admin/search/groups/{name}

响应状态 200(查询成功)

| 请求参数 |    说明    |
| :------: | :--------: |
|   name   | 模糊群名称 |



## 邀请(待定)

### 学生拉取邀请消息

请求方法 GET

请求路径 /student/notices

响应状态 200(l拉取成功)

### 学生处理邀请消息

请求方法 PATCH

请求路径 /student/notice/{id}

响应状态 204(处理成功) 404(ID不存在)

备注 消息处理后直接删除

| 请求参数 |                说明                |
| :------: | :--------------------------------: |
|    id    |               消息ID               |
|  enabled  | 处理状态 1-> 同意加入 0-> 拒绝加入 |



## 作业

### 教师新建作业

请求方法 PUT

请求路径 /teacher/work

响应状态 201(新建成功)

| 请求参数 |       说明       |
| :------: | :--------------: |
|   name   |      作业名      |
| group_id | 该作业所属群组ID |



### 教师查询所有作业

请求方法 GET

请求路径 /teacher/works/{group_id}

响应状态 200(查询成功) 404(ID不存在)

| 请求参数 |  说明  |
| :------: | :----: |
| group_id | 群组ID |



### 教师修改作业状态

请求方法 PATCH

请求路径 /teacher/work/{id}

响应状态 204(修改成功) 404(ID不存在)

| 请求参数 |      说明      |
| :------: | :------------: |
|    id    |     作业ID     |
|   name   | 修改后的作业名 |
|  enabled  |  作业启用状态  |



### 教师删除作业

请求方法 DELETE

请求路径 /teacher/work/{id}

响应状态 204(删除成功) 404(ID不存在)

| 请求参数 |  说明  |
| :------: | :----: |
|    id    | 作业ID |



### 学生上传作业

请求方法 POST

请求路径 /student/work/{id}

响应状态 201(上传成功)

| 请求参数 |  说明  |
| :------: | :----: |
|    id    | 作业ID |
|   file   |  文件  |
| filename | 文件名 |



### 下载某学生作业

请求方法 GET

请求路径 /student/download/{student_id}/{work_id}

响应状态 200(请求成功) 404(ID不存在)

|  请求参数  |  说明  |
| :--------: | :----: |
| student_id | 学生ID |
|  work_id   | 作业ID |

### 学生查询未交作业

请求方法 GET

请求路径 /student/works/un_done

响应状态 200(请求成功) 

### 学生查询已交作业

请求方法 GET

请求路径 /student/works/done

响应状态 200(请求成功)

### 学生删除作业

请求方法 DELETE

请求路径 /student/work/{word_id}

| 请求参数 |  说明  |
| :------: | :----: |
| work_id  | 作业ID |

## 上传

### 学生查询上传信息

请求方法 GET

请求路径 /student/upload/{work_id}

响应状态 200(请求成功)

| 请求参数 |  说明  |
| :------: | :----: |
| work_id  | 作业ID |
