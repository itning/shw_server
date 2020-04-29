# 作业管理系统服务端

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


**前后端分离项目，前端：[shw_client](https://github.com/itning/shw_client)**

# 架构

这是一个前后端分离项目，这里是后端工程。

B/S架构，通信采用RESTful API，使用JSON数据格式，身份验证使用JWT技术。

部署推荐使用Docker进行部署。

Redis缓存支持，使用``Spring Boot Admin``监控APP状态。

集群化部署，无痛水平扩展

前端：Vue.Js

后端：Spring Boot

数据库：MySql

缓存：Redis

# 编译

1. GIT克隆项目

   ``git clone https://github.com/itning/shw_server.git``

2. 更改分支（必须更改分支，否则默认是V2分支，V2分支是Spring Cloud版本。master分支是Spring Boot版本）

   ``git checkout master``

3. 安装本地依赖

   请执行根目录下``install-aspose-dependency.bat``批处理文件

4. Enjoy Project

# 获取发布版本

## Releases

[点我进入Releases页面](https://github.com/YunShuSoftwareStudio/shw_server/releases)

## Docker

1. 拉取镜像

   `sudo docker pull registry.cn-beijing.aliyuncs.com/itning/shw_server`

2. 运行镜像

   ```shell
   sudo docker run -p 80:8080 -e MYSQL_URL=192.168.66.1:3306 -e MYSQL_USERNAME=root -e MYSQL_PASSWORD=root -e REDIS_HOST=192.168.66.1 -e REDIS_PORT=6379 -e ADMIN_SERVER_URL=http://lcoalhost:8888 -e ADMIN_SERVER_USERNAME=admin -e ADMIN_SERVER_PASSWORD=admin -it registry.cn-beijing.aliyuncs.com/itning/shw_server:latest
   ```

   | 参数                  | 含义                               |
   | --------------------- | ---------------------------------- |
   | MYSQL_URL             | mysql地址                          |
   | MYSQL_USERNAME        | mysql用户名                        |
   | MYSQL_PASSWORD        | mysql密码                          |
   | REDIS_HOST            | redis地址                          |
   | REDIS_PORT            | redis端口                          |
   | ADMIN_SERVER_URL      | spring boot admin server服务端地址 |
   | ADMIN_SERVER_USERNAME | spring boot admin server用户名     |
   | ADMIN_SERVER_PASSWORD | spring boot admin server密码       |

   

# API文档

[HTML版本](https://itning.github.io/shw/)

[Markdown版本](https://github.com/YunShuSoftwareStudio/shw_server/blob/master/api.md)

# Docker 部署结果

![r1](https://raw.githubusercontent.com/itning/shw_server/master/pic/r1.png)
![r1](https://raw.githubusercontent.com/itning/shw_server/master/pic/r2.png)

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
