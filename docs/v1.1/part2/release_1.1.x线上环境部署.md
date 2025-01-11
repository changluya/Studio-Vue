# 项目环境及技术

> 本地所需环境

前端：Node.js、npm

后端：IDEA、Maven、JDK8



> 技术

官网：html、js、css、jquery

后台前端：vue2、element-ui

后台后端：SpringBoot、SpringSecurity、MybatisPlus、Spring、SpringMVC、log4j2

+ 中间件：MySQL、Redis

# Docker环境安装

- 安装`yum-utils`：

```bash
yum install -y yum-utils device-mapper-persistent-data lvm2
```

- 为yum源添加docker仓库位置：

```bash
yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

#建议使用阿里云的镜像进行加速，要不太慢了
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo 
```

- 安装docker：

```bash
yum install -y docker-ce
```

- 启动docker：

```bash
systemctl start docker
```

配置镜像加速：

```shell
# 创建目录
sudo mkdir -p /etc/docker   

# 编写配置文件
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://aytmcj86.mirror.aliyuncs.com"]
}
EOF

# 将服务重启一下
sudo systemctl daemon-reload

# 重启docker服务
sudo systemctl restart docker
```

---

# 一、MySQL部署

## 步骤

1、下载MySQL`5.7`的docker镜像：

```shell
docker pull mysql:5.7
```

2、使用如下命令启动MySQL服务：

```shell
docker run -p 3306:3306 --name mysql \
-v /etc/localtime:/etc/localtime \
-v /mydata/mysql/log:/var/log/mysql \
-v /mydata/mysql/data:/var/lib/mysql \
-v /mydata/mysql/conf:/etc/mysql \
-e MYSQL_ROOT_PASSWORD=root  \
-d mysql:5.7
```

参数说明

- -p 3306:3306：将容器的3306端口映射到主机的3306端口
- -v /mydata/mysql/conf:/etc/mysql：将配置文件夹挂在到主机
- -v /mydata/mysql/log:/var/log/mysql：将日志文件夹挂载到主机
- -v /mydata/mysql/data:/var/lib/mysql/：将数据文件夹挂载到主机
- -e MYSQL_ROOT_PASSWORD=root：初始化root用户的密码

接下来可按方式一或者方式二来进行导入数据库、表

> 方式一：在服务器中导入sql。

3、将准备好的sql复制到容器中

```shell
# 复制宿主机的zhifeng.sql到mysql容器下的/路径中
docker cp /mydata/zhifeng.sql mysql:/
```

4、进入运行MySQL的docker容器：

```shell
# 在容器中创建一个新的命令行
docker exec -it mysql /bin/bash   # docker exec -it mysql mysql -uroot -p   # 使用mysql容器中的命令行
```

5、在容器中对mysql进行操作

```shell
# 使用MySQL命令打开客户端：
mysql -uroot -proot --default-character-set=utf8

# 此时进入mysql的命令行中，使用数据库并进行导入
use zhifeng;
source /zhifeng.sql;

# 接着创建一个账户，该账号所有ip都能够访问
grant all privileges on *.* to 'zhifeng' @'%' identified by 'Oec2W12Jsatq';
```

> 方式二：开放3306端口，远程navicat连接来创建数据库并导入sql（推荐，方便）

1、进入mysql容器添加一个用户名及密码

```shell
# 使用mysql容器中的命令行
docker exec -it mysql /bin/bash

# 使用MySQL命令打开客户端：
mysql -uroot -proot --default-character-set=utf8

# 接着创建一个账户，该账号所有ip都能够访问
grant all privileges on *.* to 'zhifeng' @'%' identified by 'Oec2W12Jsatq';
```

2、使用navicat来连接服务器的mysql服务，导入表数据

打开navicat，连接远程服务器3306端口，导入`部署配置文件/zhifeng.sql`

![image-20220601112114901](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011121940.png)  

## 添加配置文件

**注意注意**：

在mysql7中有一个groupby的问题：

我们可以自定义一个my.cnf放置到/mysql/conf目录中，docker的mysql镜像源没有带配置文件，我们进行绑定之后启动mysql也就有了

```sql
[mysqld]
sql_mode = STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION
```

```shell
# 修改添加好了之后，重启我们的docker容器
docker restart mysql
```

# 二、Redis环境配置

## 步骤

1、拉取redis镜像

```shell
docker pull redis:5
```

2、运行redis镜像

```shell
# –restart=always 总是开机启动
# –log是日志方面的
# –appendonly yes 开启redis 持久化
# –requirepass xxx 设置密码 
docker run --restart=always --log-opt max-size=100m --log-opt max-file=2  \
    -p 6379:6379 --name redis  \
    -v /mydata/redis/redis.conf:/etc/redis/redis.conf  \
    -v /mydata/redis/data:/data  \
    -d redis:5 redis-server /etc/redis/redis.conf   \
    --appendonly yes   \
    --requirepass SZcmfGJGUD4v
```

进入Redis容器使用`redis-cli`命令进行连接：

```shell
docker exec -it redis redis-cli
```

3、添加配置文件项并重启redis容器

```shell
docker restart redis
```

# 三、IDEA集成Docker制作SpringBoot镜像并推送到服务器

## 说明

项目里面的话步骤3、4、5都已经配置好了，我们**只需要跟着做步骤1、2、6、7即可**！（可见视频）

做好之后，把原本存储的同学照片导入到相应的static目录中。

docker中进行映射的位置为源主机的：`/mydata/project/zhifeng-web/static/`

![image-20220601113401686](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011134750.png) 

## 详细步骤

### 1、首先开启docker的2375端口监听

修改配置文件：

```shell
# 编辑打开其中的docker.service文件
vim /usr/lib/systemd/system/docker.service

# 将其中的execstart进行替换
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock
```

然后重新加载docker.server文件并重启docker服务：

```shell
# 重新加载服务配置docker.server
systemctl daemon-reload
# 重新启动docker
systemctl restart docker
```

来测试一下当前2375端口是否在监听：

```shell
# 若是出现json文件内容说明已经在监听了
curl http://127.0.0.1:2375/version

# 查看下2375端口是否被监听
netstat -nlpt

# 服务器防火墙开启2375端口
firewall-cmd --add-port=2375/tcp --permanent
firewall-cmd --reload
firewall-cmd --zone=public --list-ports
```

### 2、IDEA配置docker

输入好之后即可自动进行连接

```shell
tcp://117.80.186.198:2375
```

![image-20220414163409501](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129559.png) 

点击ok之后

在services窗口我们就可以看到对应的docker了：此时我们可以对镜像、容器来进行命令方式了

![image-20220414164021898](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129614.png)  

### 3、集成Maven插件

```xml
<properties>
    <!--docker镜像的前缀：标签名-->
    <docker.image.prefix>docker</docker.image.prefix>
</properties>

<plugin>
  <groupId>com.spotify</groupId>
  <artifactId>docker-maven-plugin</artifactId>
  <version>1.0.0</version>

  <configuration>
    <!--远程Docker的地址-->
    <dockerHost>http://服务器地址:2375</dockerHost>
    <!--镜像名称，前缀/项目名-->
    <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
    <!-- 我们自己配置编写的docker目录，也就是dockerfile：指定位置在src/main/docker中    -->
    <dockerDirectory>src/main/docker</dockerDirectory>
    <!-- 我们要打包镜像需要带上的资源：默认会去找我们项目目录下对应打包好的jar包   -->
    <resources>
      <resource>
        <targetPath>/</targetPath>
        <directory>${project.build.directory}</directory>
        <include>${project.build.finalName}.jar</include>
      </resource>
    </resources>
  </configuration>
</plugin>
```

### 4、Maven添加SpringBoot项目启动类配置

若是不配置就会出现：no main manifest attribute, in /app/app.jar 报错

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>repackage</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <includeSystemScope>true</includeSystemScope>
        <!--指定主类-->
        <mainClass>com.**.**.xxxApplication</mainClass>
    </configuration>
</plugin>
```

添加一个指定配置启动类

![image-20220414175346274](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129502.png)  

### 5、项目打包，编写Dockerfile

对项目我们来进行打包：

![image-20220414171212407](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129554.png)  

在3中我们默认Dockerfile的文件目录下src/main/docker，此时我们在main下新建docker目录，然后编写Dockerfile文件

![image-20220414171248836](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129760.png)  

```dockerfile
FROM java:8
MAINTAINER changlu 939974883@qq.com
VOLUME /tmp
ADD zf-web-1.0.0.jar /app.jar
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
EXPOSE 8999
ENTRYPOINT ["java", "-jar", "app.jar"]
```

说明：主要就是ADD后jar包名改为打包好的

```dockerfile
# 指定的基础镜像
FROM java:8
# 镜像维护者姓名或邮箱地址
MAINTAINER Sunny 306509906@qq.com
# 指定容器挂载点到宿主机自动生成的目录，用于作为缓存目录
VOLUME /tmp
# 拷贝宿主机jar包到容器中，这里是将application-server.jar拷贝成app.jar【***修改这里的application-server.jar即可***】
ADD application-server.jar /app.jar
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
# 暴露出端口（提示）
EXPOSE 28011
# 运行容器时执行的shell命令
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 6、构建镜像

1、构建之前，打包springboot项目。

2、在对应引入插件的下，找到docker，执行docker:build命令

![image-20220414171423552](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129539.png)  

出现build success就表示构建成功！

![image-20220414171603802](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129024.png)  

由于我IDEA已经连接上了服务器的Docker，这里构建完的镜像会直接上传上去

![image-20220414171742465](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206011129093.png)  

### 7、进入容器执行运行命令（这一步部署完成Nginx后进行操作）

内容：IDEA连接docker，然后打包镜像到服务器。

配置地址：tcp://117.80.186.198:2375

```sh
# --link表示链接容器，让他们能够进行互相访问
# 日志文件映射
docker run -p 8999:8999 --name studo-admin \
--link mysql:db \
--link redis:redis \
--link nginx:nginx \
-v /etc/localtime:/etc/localtime \
-v /mydata/project/zhifeng-web/logs:/tmp/logs \
-v /mydata/nginx/html/static:/tmp/static \
-d studio/zf-web:latest
```

可实现docker内部执行docker命令：

```shell
docker run -p 8999:8999 --name studo-admin \
--link mysql:db \
--link redis:redis \
--link nginx:nginx \
--privileged \
-v /var/run/docker.sock:/var/run/docker.sock \
-v /usr/bin/docker:/usr/bin/docker \
-v /usr/bin/chmod:/usr/bin/chmod \
-v /etc/localtime:/etc/localtime \
-v /mydata/project/zhifeng-web/logs:/tmp/logs \
-v /mydata/nginx/html/static:/tmp/static \
-d studio/zf-web:latest
```

进入docker容器中：

docker exec -it mysql mysqldump -hdb -P3306 -u zhifeng -pOec2W12Jsatq zhifeng > /tmp/backup/zhifeng.sql

```
docker exec -it 60 /bin/bash
```

# 四、部署Nginx

## 步骤

下载Nginx`1.10`的docker镜像：

```shell
docker pull nginx:1.10
```

先运行一次容器（为了拷贝配置文件）：若是不这样拷贝直接指定对应目录的话，在宿主机的conf目录下就都是为空的了

```shell
docker run --restart=always -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx/logs:/var/log/nginx  \
-d nginx:1.10
```

将容器内的配置文件拷贝到指定目录：

```shell
docker container cp nginx:/etc/nginx /mydata/nginx/
```

进入到宿主机的/mydata/nginx目录，修改该目录下nginx文件名称：

```shell
mv nginx conf
```

终止并删除容器：

```shell
docker stop nginx
docker rm nginx
```

启动nginx：

docker container cp mysql:/usr/bin/mysqldump /mydata/project/zhifeng-web/mysqldump

```shell
# /usr/share/nginx/html是nginx默认访问的路径、/var/log/nginx是对应的log日志、/etc/nginx也就是对应nginx配置文件
docker run -p 80:80 --name nginx \
-v /mydata/nginx/html:/usr/share/nginx/html \
-v /mydata/nginx/logs:/var/log/nginx  \
-v /mydata/nginx/conf:/etc/nginx \
-d nginx:1.10
```

## 配置文件

核心：替换服务器中的default.nginx与nginx.conf。

自定义配置default.conf内容如下：

```sh
server {
    listen       80;
    server_name  localhost;

    #charset koi8-r;
    access_log  /var/log/nginx/studio.access.log  main;

    # 官网首页
    location / {
        root   /usr/share/nginx/html/front;
        index  index.html index.htm;
    }

    # 后台服务转发，对应地址可以使用docker inspect --format '{{ .NetworkSettings.IPAddress }}'  studio-admin来查看网络地址
    location /api {
        proxy_pass  http://172.17.0.5:8999;
        proxy_set_header Host $host;
        proxy_set_header X-Real-Ip $remote_addr;
        proxy_set_header X-Forwarded-For $remote_addr;
        # 最大请求体为20MB
        client_max_body_size 20m;
    }

    # 访问管理页面
    location /admin/ {
        alias   /usr/share/nginx/html/admin/;
        index  index.html index.htm;
    }
    
    # 访问静态资源页面（对应docker容器存放图片路径为/tmp/static映射到源主机/mydata/project/zhifeng-web/static/）
	location /zhifeng/static/ {
		alias   /usr/share/nginx/html/static/;
		index  index.html index.htm;
	}
}  
```

配置完成之后重启下nginx服务：

```shell
docker restart nginx
```

# 五、上传项目

到/mydata/nginx/html目录下新建三个目录：admin、front、static

+ admin：后台管理系统页面。【进入工作室后台/zhifeng-front，执行命令：`npm run build`，拿到dist目录资源】
+ front：工作室官网。【工作室官网/smart-bee-studio】
+ static：工作室静态资源。【初始成员的照片，也作为之后静态资源的存放路径】

![image-20220602171948669](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/3/202206021719720.png)  



测试：

官网：http://studio.nbdetect.cn/或者http://117.80.186.198/

后台管理系统：http://studio.nbdetect.cn/admin/

**管理员账号**：admin zhifeng2019

# 最后设置定时备份任务—备份sql

①配置文件目录下的backupsql.sh复制到服务器的`/mydata/project/zhifeng-web`目录下

![image-20220606231752295](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/6/202206062317347.png)  

文件内容如下：本质就是备份sql命令

```sh
#!/bin/sh
docker exec mysql mysqldump -uzhifeng -pOec2W12Jsatq zhifeng > /mydata/nginx/html/static/zhifeng.sql
```

②将该脚本文件配置为可执行文件

```shell
# 配置权限
chmod 777 /mydata/project/zhifeng-web/backupsql.sh
```

③设置定时任务：

```sh
# 打开定时任务进行编辑
crontab -uroot -e

# 每天凌晨1点执行 接着esc 输入:wq保存
0 1 * * * /mydata/project/zhifeng-web/backupsql.sh
```

> 说明

若是手动执行的话就会出现报错，这是正常的，定时任务执行就不会报错的：

![image-20220606233839307](https://pictured-bed.oss-cn-beijing.aliyuncs.com/img/2022/6/202206062338351.png)  

两个目录方便切换：

```
/mydata/project/zhifeng-web
/mydata/nginx/html/static
```

---