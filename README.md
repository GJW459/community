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