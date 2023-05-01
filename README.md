# utool

集成一些杂七杂八的小工具，初衷是为了方便，能在webshell上执行的东西何必下个工具再打开命令行执行呢

## 已支持的功能

### 以下功能只在本地做过简单测试

1. 致远oa数据库解密

2. 用友nc数据库解密

3. 注入suo5   // 暂时存在一点小问题，先不发布

4. ssh命令执行

5. fscan结果批量命令执行

## 致远oa数据库解密

提供数据库配置文件路径 如 D:/Seeyon/A8/base/conf/datasourceCtp.properties

支持version2.4 和 1.0解密

![seeyon1.png](img%2Fseeyon1.png)

![seeyon2.png](img%2Fseeyon2.png)

## 用友nc数据库解密

提供数据库配置文件路径 如 c:/home/ierp/bin/prop.xml

![nc.png](img%2Fnc.png)

## 注入suo5

![suo.png](img%2Fsuo.png)

## 执行单条ssh命令

格式如 root@127.0.0.1:22@password

或 root@127.0.0.1@password

![ssh.png](img%2Fssh.png)

## fscan结果批量执行并写入文件

可将fscan ssh结果直接导入，批量执行命令

![ssh3.png](img%2Fssh3.png)

![ssh2.png](img%2Fssh2.png)

## fscan结果数据库批量连接


