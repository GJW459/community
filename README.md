## 码匠社区

## 资料
[Spring文档](https://spring.io/guides)<br>
[Spring web](https://spring.io/guides/gs/serving-web-content/)<br>
[Bootstrap](https://v3.bootcss.com/components/#navbar-default)<br>
[Github OAuth Document](https://developer.github.com/apps/building-github-apps/creating-a-github-app/)
## 工具
git<br>
git init:创建仓库<br>
git status:查看可以提交的文件<br>
git add .全部加到缓存库里<br>
git commit -m "提交"<br>
git remote origin 和远程连接<br>
git push 推向github<br>
vp或者visio画时序图<br>
## 所用知识
springboot<br>
mybatis<br>
bootstrap
## 步骤
实现GitHub登录
继承h2数据库
```sql
sql脚本
create table USER
(
  ID           INTEGER default NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_840EA468_2952_4804_885B_528E019C0F51"
    primary key,
  ACCOUNT_ID   VARCHAR(100),
  NAME         VARCHAR(50),
  TOKEN        VARCHAR(50),
  GMT_CREATE   BIGINT,
  GMT_MODIFIED BIGINT
  # 通过这个创建用户名和密码
  create user if not exists sa password '123';
    alter user sa admin true
);
```
## 第一个功能
实现GitHub的第三方登录<br>
1. 通过向GitHub获取权限，获取权限后调用GitHub的user接口，
通过okhttp返回一个json字符串再由fastjson将这个json对象装换成GitHubUser对象这是一个数据传输对象
，再通过在控制器里面将GitHubUser对象的属性封装到User对象中，最后存入MySql数据库
2. Cookie和Session对象
* 首先Cookie不是Servlet中的九大内置对象，需要实列化，session对象是内置对象不需要实列化
* 从GitHub中获取到User对象存入数据库后，我们可以HttpServletResponse对象返回一个自定义Cookie对象给客户端
* java Servlet API引入session 机制来跟踪客户的状态，session指的是在一段时间内，单个客户和web服务器之间一连串的交互过程，在一个session中，一个客户可能会多次请求同一个网页，也可能请求多个不同服务器资源，例如：在一个邮件系统应用中，从一个客户登录到邮件系统，到写信，收信和发信等，到最后退出邮件系统，整个过程为一个session；再例如：大家在网上购物的时候，从购物到最后的付款，整个过程也是一个session 。
* session对像是jsp中的内置对象，可以直接使用；在Servlet中使用session时，必须先创建出该对象，Servlet中创建session的方法：
HttpSession session=request.getSession();或   HttpSession session=request.getSession(boolean value);
在服务器上，通过session ID来区分每一个请求服务器的用户，用户只要一连接到服务器，服务器就会为之分配一个唯一的不会重复的session ID，session ID由服务器统一管理，人为不能控制
web容器关闭或重启，session会死亡调用session.invalidate();方法，强制session死亡
前后两次请求超过了session指定的生命周期时间，默认为30分钟
* Cookie和Session的区别
session将信息保存在服务器上，cookie保存在客户端上
session比cookie更安全，session比cookie更占资源
session使用cookie的机制，如果cookie被禁用，那么session也无法使用，因为session ID是以cookie的形式保存在客户端的内存当中
1. 通过本地Cookie实现自动登录
```java 
 Cookie[] cookies = request.getCookies();
        //获取所有的Cookie
        if (cookies!=null){
            for (Cookie cookie : cookies) {

                //key 为token的Cookie
                if (cookie.getName().equals("token")){

                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if (user!=null){
                        //向服务端传session对象
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
```
## 初识flyway
* flyway是一个数据库迁移工具[flyway学习链接](https://www.e-learn.cn/content/qita/779480)
## 第二个功能:发布问题
* bootstrap 实现页面
* controller 处理请求
* thymeleaf 模板引擎渲染页面
## 初识lombok
lombok是一个插件：可以简化代码操作，通过注解生成get，set，toString，构造函数，hashcode方法
* 优点：简化代码
* 确定：不能实现多参构造，只能实现全参和无参，代码可读写差
* 使用：IDEA下载lombok插件，maven引入lombok依赖
常见注解
1. @Data:自动生成所有的getset方法
2. @Getter/@Setter:自动生成get/set方法
3. @NonNull：校验参数，能够防止空指针异常
4. @Cleanup：自动调用close()方法，关闭流，常用来文件的读写
```java 
 @Cleanup InputStream in = new FileInputStream(args[0]);
 @Cleanup OutputStream out = new FileOutputStream("asdaads");
```
* @ToString：生成 toString()方法
* @NoArgsConstructor, @RequiredArgsConstructor and @AllArgsConstructor参数（Args）构造器（constructor）