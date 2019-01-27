# Api Documentation


<a name="overview"></a>
## Übersicht
Api Documentation


### Aktuelle Version
*Version* : 1.0


### Lizenzinformationen
*Lizenz* : Apache 2.0  
*Lizenz-URL* : http://www.apache.org/licenses/LICENSE-2.0  
*Nutzungsbedingungen* : urn:tos


### URI Schema
*Host* : localhost:8080  
*Basis-Pfad* : /


### Tags

* 学生接口 : Student Controller
* 教师接口 : Teacher Controller
* 管理员接口 : Admin Controller




<a name="paths"></a>
## Pfade

<a name="indexusingget"></a>
### 控制面板主页
```
GET /config
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="savecasloginurlusingpost"></a>
### 设置CAS登陆地址
```
POST /config/casLoginUrl
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**name**  <br>*verpflichtend*|CAS登陆地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="savecaslogouturlusingpost"></a>
### 设置CAS登出地址
```
POST /config/casLogoutUrl
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**name**  <br>*verpflichtend*|CAS登出地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="savecasserverurlusingpost"></a>
### 设置CAS服务器地址
```
POST /config/casServerUrl
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**name**  <br>*verpflichtend*|CAS服务器地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="savefilepathusingpost"></a>
### 设置文件目录
```
POST /config/filePath
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**name**  <br>*verpflichtend*|文件目录地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="doinitusingpost"></a>
### 提交初始化方法
```
POST /config/init
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**password**  <br>*verpflichtend*|新密码|string|
|**Query**|**username**  <br>*verpflichtend*|新用户名|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="initusingget"></a>
### 第一次进入控制面板
```
GET /config/init
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="savelocalserverurlusingpost"></a>
### 设置本地服务器地址
```
POST /config/localServerUrl
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**name**  <br>*verpflichtend*|本地服务器地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="dologinusingpost"></a>
### 提交登陆方法
```
POST /config/login
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**password**  <br>*verpflichtend*|密码|string|
|**Query**|**username**  <br>*verpflichtend*|用户名|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="loginusingget"></a>
### 登陆页面
```
GET /config/login
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="saveloginsuccessurlusingpost"></a>
### 设置CAS登陆成功跳转地址
```
POST /config/loginSuccessUrl
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**name**  <br>*verpflichtend*|CAS登陆成功跳转地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="logoutusingget"></a>
### 注销登陆
```
GET /config/logout
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="savetempdirusingpost"></a>
### 设置临时目录
```
POST /config/tempDir
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Body**|**name**  <br>*verpflichtend*|打包临时目录地址|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="userusingpost"></a>
### 设置用户名密码
```
POST /config/user
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**password**  <br>*verpflichtend*|新密码|string|
|**Query**|**username**  <br>*verpflichtend*|新用户名|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|string|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 管理员接口


<a name="downloadfileusingget"></a>
### 学生作业下载
```
GET /student/down/{studentNumber}/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Header**|**range**  <br>*verpflichtend*|Range请求头(用于判断是否需要支持多线程下载和断点续传)|string|
|**Path**|**studentNumber**  <br>*verpflichtend*|学号|string|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 学生接口


<a name="addgroupusingpost"></a>
### 学生加入群组
```
POST /student/group
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**code**  <br>*verpflichtend*|邀请码|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[Group](#group)|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 学生接口


<a name="isstudentjoinanystudentgroupusingget"></a>
### 查询学生是否有学生群组
```
GET /student/group/exist
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[RestModel](#restmodel)|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 学生接口


<a name="dropoutgroupusingdelete"></a>
### 退出群组
```
DELETE /student/group/{groupId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**groupId**  <br>*verpflichtend*|群组ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**204**|No Content|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 学生接口


<a name="getallgroupsusingget"></a>
### 获取学生所有群组
```
GET /student/groups
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [Group](#group) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 学生接口


<a name="getuploadinfobyworkidusingget"></a>
### 根据作业ID获取上传信息
```
GET /student/upload/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[Upload](#upload)|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 学生接口


<a name="uploadworkusingpost"></a>
### 学生上传作业
```
POST /student/work/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|
|**FormData**|**file**  <br>*verpflichtend*|上传的作业|file|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `multipart/form-data`


#### Erzeugt

* `\*/*`


#### Tags

* 学生接口


<a name="deleteuploadworkusingdelete"></a>
### 学生删除已上传作业
```
DELETE /student/work/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**204**|No Content|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 学生接口


<a name="getalldoneworksusingget"></a>
### 获取学生所有已上传作业
```
GET /student/works/done
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [WorkModel](#workmodel) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 学生接口


<a name="getallundoneworksusingget"></a>
### 获取学生所有未上传作业
```
GET /student/works/un_done
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [WorkModel](#workmodel) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 学生接口


<a name="downloadallfileusingget"></a>
### 下载所有
```
GET /teacher/down/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Header**|**range**  <br>*verpflichtend*|Range请求头(用于判断是否需要支持多线程下载和断点续传)|string|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 教师接口


<a name="addgroupusingpost_1"></a>
### 新建群组
```
POST /teacher/group
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**groupName**  <br>*verpflichtend*|教师添加的组名|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[Group](#group)|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口


<a name="isteacherhaveanygroupusingget"></a>
### 查询教师是否有群组
```
GET /teacher/group/exist
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[RestModel](#restmodel)|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口


<a name="deletegroupusingdelete"></a>
### 删除群组
```
DELETE /teacher/group/{id}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**id**  <br>*verpflichtend*|要删除的群组ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**204**|No Content|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 教师接口


<a name="updategroupnameusingpatch"></a>
### 更新群组
```
PATCH /teacher/group/{id}/{name}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**id**  <br>*verpflichtend*|群组id|string|
|**Path**|**name**  <br>*verpflichtend*|修改的新群组名|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**204**|No Content|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 教师接口


<a name="getteachercreategroupsusingget"></a>
### 教师获取创建的所有群组
```
GET /teacher/groups
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [Group](#group) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口


<a name="packusingget"></a>
### 打包
```
GET /teacher/pack/{workId}
```


#### Beschreibung
下载所有作业之前必须先调用该API


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[RestModel](#restmodel)|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 教师接口


<a name="addworkusingpost"></a>
### 添加作业
```
POST /teacher/work
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Query**|**groupId**  <br>*verpflichtend*|群ID|string|
|**Query**|**workName**  <br>*verpflichtend*|作业名|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|[Work](#work)|
|**201**|Created|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口


<a name="getteacherworkusingget"></a>
### 根据群ID获取作业
```
GET /teacher/work/{groupId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**groupId**  <br>*verpflichtend*|群ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [WorkModel](#workmodel) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口


<a name="deleteworkusingdelete"></a>
### 删除作业
```
DELETE /teacher/work/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**204**|No Content|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|


#### Erzeugt

* `\*/*`


#### Tags

* 教师接口


<a name="updateworkenabledusingpatch"></a>
### 更新作业启用状态
```
PATCH /teacher/work/{workId}/{enabled}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**enabled**  <br>*verpflichtend*|开启状态|string|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|Kein Inhalt|
|**204**|No Content|Kein Inhalt|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|


#### Verarbeitet

* `application/json`


#### Erzeugt

* `\*/*`


#### Tags

* 教师接口


<a name="getteacherworkdetailsusingget"></a>
### 获取作业详情
```
GET /teacher/work_detail/{workId}
```


#### Parameter

|Typ|Name|Beschreibung|Typ|
|---|---|---|---|
|**Path**|**workId**  <br>*verpflichtend*|作业ID|string|


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [WorkDetailsModel](#workdetailsmodel) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口


<a name="getteacherworksusingget"></a>
### 获取教师所有作业
```
GET /teacher/works
```


#### Antworten

|HTTP Code|Beschreibung|Typ|
|---|---|---|
|**200**|OK|< [WorkModel](#workmodel) > array|
|**401**|Unauthorized|Kein Inhalt|
|**403**|Forbidden|Kein Inhalt|
|**404**|Not Found|Kein Inhalt|


#### Erzeugt

* `\*/*`
* `application/json;charset=UTF-8`


#### Tags

* 教师接口




<a name="definitions"></a>
## Definitionen

<a name="8765645ce2889ba7283ea01e8c317f79"></a>
### Callable«ResponseEntity«RestModel»»
*Typ* : object


<a name="group"></a>
### Group
群组


|Name|Beschreibung|Typ|
|---|---|---|
|**code**  <br>*verpflichtend*|邀请码|string|
|**gmtCreate**  <br>*verpflichtend*|创建时间|string (date-time)|
|**gmtModified**  <br>*verpflichtend*|更新时间|string (date-time)|
|**groupName**  <br>*verpflichtend*|群组名|string|
|**id**  <br>*verpflichtend*|群组ID|string|
|**teacherName**  <br>*verpflichtend*|教师名|string|
|**teacherNumber**  <br>*verpflichtend*|教师序号|string|


<a name="restmodel"></a>
### RestModel
Rest 返回消息


|Name|Beschreibung|Typ|
|---|---|---|
|**code**  <br>*verpflichtend*|状态码|integer (int32)|
|**data**  <br>*verpflichtend*|数据|object|
|**msg**  <br>*verpflichtend*|消息|string|


<a name="student"></a>
### Student
用于存储学生元数据的实体


|Name|Beschreibung|Typ|
|---|---|---|
|**clazzId**  <br>*optional*|班级|string|
|**loginName**  <br>*optional*|登录名|string|
|**name**  <br>*optional*|姓名|string|
|**no**  <br>*verpflichtend*|学号|string|


<a name="upload"></a>
### Upload
上传


|Name|Beschreibung|Typ|
|---|---|---|
|**extensionName**  <br>*verpflichtend*|扩展名|string|
|**gmtCreate**  <br>*verpflichtend*|创建时间|string (date-time)|
|**gmtModified**  <br>*verpflichtend*|更新时间|string (date-time)|
|**mime**  <br>*verpflichtend*|文件类型|string|
|**size**  <br>*verpflichtend*|文件大小(bytes)|integer (int64)|
|**studentId**  <br>*verpflichtend*|学生ID|string|
|**workId**  <br>*verpflichtend*|作业ID|string|


<a name="work"></a>
### Work
作业


|Name|Beschreibung|Typ|
|---|---|---|
|**enabled**  <br>*verpflichtend*|作业启用状态|boolean|
|**fileNameFormat**  <br>*verpflichtend*|文件名规范|string|
|**gmtCreate**  <br>*verpflichtend*|创建时间|string (date-time)|
|**gmtModified**  <br>*verpflichtend*|更新时间|string (date-time)|
|**groupId**  <br>*verpflichtend*|群组ID，该作业所属群|string|
|**id**  <br>*verpflichtend*|作业ID，标识唯一作业|string|
|**workName**  <br>*verpflichtend*|作业名|string|


<a name="workdetailsmodel"></a>
### WorkDetailsModel
作业详情


|Name|Beschreibung|Typ|
|---|---|---|
|**groupName**  <br>*verpflichtend*|群组名|string|
|**student**  <br>*verpflichtend*|学生信息|[Student](#student)|
|**up**  <br>*verpflichtend*|是否上传|boolean|
|**upload**  <br>*verpflichtend*|上传信息|[Upload](#upload)|
|**workName**  <br>*verpflichtend*|作业名|string|


<a name="workmodel"></a>
### WorkModel
作业模型


|Name|Beschreibung|Typ|
|---|---|---|
|**enabled**  <br>*optional*||boolean|
|**fileNameFormat**  <br>*verpflichtend*|文件名规范|string|
|**gmtCreate**  <br>*verpflichtend*|创建时间|string (date-time)|
|**gmtModified**  <br>*verpflichtend*|更新时间|string (date-time)|
|**groupId**  <br>*verpflichtend*|群组ID，该作业所属群|string|
|**groupName**  <br>*verpflichtend*|群组名|string|
|**id**  <br>*verpflichtend*|作业ID，标识唯一作业|string|
|**teacherName**  <br>*verpflichtend*|教师名|string|
|**workName**  <br>*verpflichtend*|作业名|string|





