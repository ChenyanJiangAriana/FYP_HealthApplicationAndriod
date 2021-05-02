---
typora-copy-images-to: img
---





**Thanks those developer for their formal efforts！！！！ Thanks a lot for their work inspirit me a lot !!**



### Reference

Heartbeat measurement principle  https://www.zhihu.com/question/20410686

Gradle setting  http://blog.csdn.net/fuchaosz/article/details/51567808

Footstep algorithm reference  https://github.com/linglongxin24/DylanStepCount/blob/master/README.md

Demo video: https://www.youtube.com/channel/UCh7jDl6GD1MTks989Wj3i0A



### How to run this application

*Step 1: go to server side (jkapp server) run it*  

You perhaps want to run in your own database, but it can be used directly because it already deployed to the cloud database.

But if you want you can go to **<u>jkapp_server\src\main\resources\application.yml</u>** to change it **(line 9 -16)**

*Step 2: go to client side find this **<u>app\src\main\java\com\jk\dayu\jkapp\service\Service.java</u>***

```
public class Service {

    //change to your ip address 
  
    private static String url ="http://192.168.0.105:8080";
    public static final String verifyAccountUrl = url+"/user/verifyAccount";
```

cmd

```
ipconfig
// find your ip address 
```

run this application 



Database file can be found in github: 

Client-side：

https://github.com/ChenyanJiangAriana/FYP_HealthApplicationAndriod

Server-side 

https://github.com/ChenyanJiangAriana/FYP_HealthApplication_Server

APK:

can be found in github

test picture

1. Login page

<img src="img\Login.png" alt="login" style="zoom:33%;" />

2. main page

   <img src="img\Main Home.png" style="zoom:33%;" />

   

3. main

   <img src="img/clock in.png" alt="Mine" style="zoom:33%;" />

4. tips

   <img src="img\Tips.png" alt="tips" style="zoom:33%;" />

5. heat beat

![heat Beat](img\heat Beat.png)

6. advanced test 

   <img src="img\basic.png" alt="health report" style="zoom:33%;" />

7. foot step

   <img src="D:\Users\jiang\HealthApp\final\jkapp (4)\img\Footstep.png" style="zoom:33%;" />

8. doctor select

<img src="img\Doctor list for choose.png" alt="doc" style="zoom:33%;" />

9. inquiry

   <img src="img\inquiry 2.png" alt="inquiry" style="zoom:33%;" />

10. inquiry 2

    <img src="img\inquiry 2.png" alt="inquiry" style="zoom:33%;" />

