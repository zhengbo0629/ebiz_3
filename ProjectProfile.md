#  [Ebiz项目简介](http://loalhost:8080/) 
##### 业务描述： 　 
　 这里仅介绍项目的技术相关内容，业务部分见README.md
>## 技术架构简介： 　 
>  1 本项目使用的技术有SpringBoot、Maven、Mybatis、Druid、Bootstrap、JQuery、HTML5
>  
>  2 后台提供Restful风格的API，前台使用Ajax请求获取数据并解析显示。
>  
>  3 项目开发工具为IDEA。
***
> ## 运行方式　 
> ### 测试开发运行　
>  找到ebiz->src->main->java->com.ebiz.EastEbiz，运行main函数，浏览器输入http://localhost:8080即可
> ### 生产运行
>  1 修改配置文件:修改服务端口，数据库用户名密码等
>  
>  2 打包项目:IDEA右侧Maven面板，选择`Ebiz父项目`，`跳过测试`，点击`package`
>  
>  3 找到项目目录下的jar文件 `ebiz\target\ebiz-1.0.jar` 上传至服务器
>  
>  4 运行项目:`nohup java -jar ebiz-1.0.jar &>/dev/null &`
>  
>  5 运行环境:jdk1.8及以上

***
> ## 项目目录结构
> ![项目结构](https://zhr-main.oss-cn-beijing.aliyuncs.com/ebiz/parent.jpg)
>
> 本项目为Maven父子项目，common存放通用的逻辑，Ebiz存放业务相关逻辑
>
> ![项目结构](https://zhr-main.oss-cn-beijing.aliyuncs.com/ebiz/sub.jpg)
> 
> ebiz 子项目中，java目录中存放代码，resources目录存放静态资源,application.yml中存放配置
> 
> ![项目结构](https://zhr-main.oss-cn-beijing.aliyuncs.com/ebiz/src.jpg)
> 
> com.ebiz目录中，aop目录存放全局业务相关AOP，如登录控制
>
> common目录存放枚举、工具方法、常量等
>
> config目录存放注入的类
>
> controller目录存放Restful控制器
>> 下面的Model目录存放前台交互需要的实体类
>> 控制器的命名目前与菜单一致，一个大菜单对应一个控制器
>
> service 中存放复杂或者通用业务逻辑
>> 命名以数据库表或者实体类一致，即一张表一个service
>
> dao存放数据库相关接口文件
>
> model存放数据库相关实体类和example
>
> dao、model、和resources中的mapping基本由MybatisGenerator生成
>
> ![项目结构](https://zhr-main.oss-cn-beijing.aliyuncs.com/ebiz/res.jpg)
>
> mapping 目录存放 MybatisGenerator 生成的SQL语句映射文件
>
> static 目录下存放静态资源，后期将页面全部存在这个目录下的view目录
>
> templates 目录下的文件为页面文件，目前没有完全前后分离，页面暂时放在这里
>
> index.html 文件为网站的入口
>





