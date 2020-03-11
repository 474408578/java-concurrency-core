### 1、数据库
#### 远程数据库

#### 本地数据库

#### 创建数据库

##### 创建全新的数据库

##### 复制远程数据库


### 2、修改记录的提交


### 3、工作树和索引
在数据库和工作树之间有索引，索引是为了向数据库提交作准备的区域。


### 4、命令
git init

git status

git add .

git commit -m "first commit"

git log # 查看提交记录


# 5、远程数据库
git remote add <name> <url>   # 添加远程数据库，并给它取一个别名name
git remote add origin https://[your_space_id].backlogtool.com/git/[your_project_key]/tutorial.git

git push [origin]
git push <repository> <refspec>...

运行以下命令便可向远程数据库‘origin’进行推送。
当执行命令时，如果您指定了-u选项，那么下一次推送时就可以省略分支名称了。
但是，首次运行指令向空的远程数据库推送时，必须指定远程数据库名称和分支名称。
```
$ git push -u origin master
Username: <用户名>
Password: <密码>
Counting objects: 3, done.
Writing objects: 100% (3/3), 245 bytes, done.
Total 3 (delta 0), reused 0 (delta 0)
To https://nulab.backlog.jp/git/BLG/tutorial.git
 * [new branch]      master -> master
```

git pull <repository> <refspec>...  refspec分支


git clone
git pull


### 6、整合修改记录
>在执行pull之后，进行下一次push之前，如果其他人进行了推送内容到远程数据库的话，那么你的push将被拒绝。
>这是因为，如果不进行合并就试图覆盖已有的变更记录的话，其他人push的变更（图中的提交C）就会丢失。

git pull
git push


### 7、分支（branch）
在数据库进行最初的提交后, Git会创建一个名为master的分支。因此之后的提交，在切换分支之前都会添加到master分支里。




