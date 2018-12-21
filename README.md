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
|  create_time   |  data  | 创建时间 |    非空    |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |
|                |        |          |            |

## 作业属性(work)

|   属性名    |  类型  |         说明         |      约束      |
| :---------: | :----: | :------------------: | :------------: |
|     id      | string | 作业ID，标识唯一作业 |   非空，主键   |
|  group_id   | string | 群组ID，该作业所属群 |   非空，索引   |
|   status    |  bit   |     作业启用状态     | 非空，默认启用 |
|    name     | string |        作业名        |      非空      |
| create_time |  date  |       创建时间       |      非空      |
| update_time |  date  |       更新时间       |      非空      |
|             |        |                      |                |
|             |        |                      |                |
|             |        |                      |                |

## 上传属性(history)

|     属性名     |  类型  |   说明   |    约束    |
| :------------: | :----: | :------: | :--------: |
| student_number | string |  学生ID  | 非空，主键 |
|    work_id     | string |  作业ID  | 非空，主键 |
|      mime      | string | 文件类型 |    非空    |
|      size      | double | 文件大小 |    非空    |
|  create_time   |  date  | 上传时间 |    非空    |
|                |        |          |            |
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
|    create_time     |  data  |     创建时间     |    非空    |
|                    |        |                  |            |
|                    |        |                  |            |
|                    |        |                  |            |
|                    |        |                  |            |

# Api

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
>    "data":响应数据
>}
>```

## 群组

### 获取学生的所有群组

请求方法 GET

请求路径 /student/groups

请求参数

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

请求参数 code 代表群组邀请码

响应状态 201(创建成功)

### 学生退出群组

请求方法 DELETE

请求路径 /student/group/{id}

请求参数 id 群组ID

响应状态 204(退出成功) 404(ID不存在)

### 获取教师的所有群组

请求方法 GET

请求路径 /teacher/groups

请求参数

响应状态 200(请求成功)

### 教师创建群组

请求方法 PUT

请求路径 /teacher/group

请求参数 name 群组名称

响应状态 201(创建成功)

### 教师解散群组

请求方法 DELETE

请求路径 /teacher/group/{id}

请求参数 id 群组ID

响应状态 204(解散成功) 404(ID不存在)

### 教师修改群信息

请求方法 PATCH

请求路径 /teacher/group/{id}

请求参数 id 群组ID name 新群名称

响应状态 204(修改成功) 404(ID不存在)

### 管理员查询所有群信息

请求方法 GET

请求路径 /admin/groups

请求参数 limit 指定返回结果数量 0~n page 指定返回页码1~n

响应状态 200(查询成功)

### 管理员根据群名称模糊搜索

请求方法 GET

请求路径 /admin/search/groups/{name}

请求参数 name 模糊群名称

响应状态 200(查询成功)

## 邀请(待定)

### 学生拉取邀请消息

请求方法 GET

请求路径 /student/notices

请求参数

响应状态 200(l拉取成功)

### 学生处理邀请消息

请求方法 PATCH

请求路径 /student/notice/{id}

请求参数 id 消息ID status 处理状态->1 同意加入 0-> 拒绝加入

响应状态 204(处理成功) 404(ID不存在)

备注 消息处理后直接删除

## 作业

### 教师新建作业

请求方法 PUT

请求路径 /teacher/work

请求参数 name 作业名 group_id 该作业所属群组ID

响应状态 201(新建成功)

### 教师查询所有作业

请求方法 GET

请求路径 /teacher/works/{group_id}

请求参数 group_id 群组ID

响应状态 200(查询成功) 404(ID不存在)

### 教师修改作业状态

请求方法 PATCH

请求路径 /teacher/work/{id}

请求参数 id 作业ID name 修改后的作业名 status 作业启用状态

响应状态 204(修改成功) 404(ID不存在)

### 教师删除作业

请求方法 DELETE

请求路径 /teacher/work/{id}

请求参数 id 作业ID

响应状态 204(删除成功) 404(ID不存在)

### 学生上传作业

请求方法 POST

请求路径 /student/work/{id}

请求参数 id 作业ID file 文件 filename 文件名

响应状态 201(上传成功)

### 下载某学生作业

请求方法 GET

请求路径 /student/download/{student_id}/{work_id}

请求参数 student_id 学生ID work_id 作业ID

响应状态 200(请求成功) 404(ID不存在)