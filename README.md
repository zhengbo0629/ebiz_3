#[ebiz](http://www.chaosrobot.com/) 
#####业务描述： 　 
这个网站业务性比较强，为了方便大家理解，我这里大概讲一下 
　 
>##当前生意模式： 　 
> #####主要角色介绍 
　 
> 医生：就是reseller，生意的组织者。组织护士下单，拿到产品再卖给病人。医生也可以给护士相应的权限，招聘护士担任不同的生意里的角色。

> 护士：就是帮助医生，在医生的指导下下单的人。医生也可以给护士相应的管理权限，从事公司的其他业务；
 
> 病人：就是最终购买商品的人。 　 

> 医生在得知商品的折扣信息后，自己进行分析后，如果觉得某些商品有利可图，就开始进货，因为很多商家折扣的时候进行限购，所以把这些信息分享给护士，让护士们帮忙进货，比如：沃尔玛有款电脑促销，原价400，现价300，限购每人3台。医生得到消息后，觉得有利可图，就把这个消息告诉护士，加价每台20块收。护士每台消费300。护士拿到货以后，按照医生的要求邮寄出去，最后医生给每个护士打款每台320。医生等到促销结束后，再把电脑卖给病人从而盈利； 

>目前本网站开发的主要是医生和护士之间的协作功能，希望能够把这个网站开放给其他的医生。提供收费服务从而盈利。 

>医生要注册主账号，同时注册一家公司，医生账号和一个公司账号绑定，每个公司只能有一个医生；为收费账号 
护士注册子账号，要注册到一家公司名下。目前每个护士只能服务一家公司，未来希望每个护士可以服务多家公司的功能。为免费账号； 
独立账号就是没有护士的医生账号，有些功能被限制。为免费账号。 

>医生可以给护士相应的管理权限，护士也可以为医生进行管理方面的工作，规模大的时候，医生可以招聘多个护士同时进行同一种工作，为了防止重复工作，所以每项工作添加了领取工作一个步骤，护士要先领取处于待领取的工作，领取后别的护士就看不到了。然后护士才能开始相应的工作，所以要注意加锁 
数据库操作注意加锁，特别是需要对多条记录同时进行修改的，为了防止多人同时操作。 
能翻墙的，欢迎注册护士账号，进行下单，我这边会提供信用卡，可以按照要求下单。也会支付相应的报酬。 

>这个生意的核心是商家的折扣信息，就是产品价格信息，我们的服务里面打算添加商品的价格信息作为我们服务的特色；我后台有个网络爬虫提供价格服务，目前还没有开放给大家。将来也是大家工作的一部分。 

***
> #下面对网站上的主要功能进行介绍 　 
>> ###网站业务管理 　
>> 超级管理员的功能 

>> ###主账号管理 　  
>> 对主账号进行管理 

>> ###收费管理 　    
>> 对主账号收费进行管理 

>> ###系统Deal订阅管理 　
>> 主要是系统deal的管理
>> * #####系统推荐deal 　 
>>   只有医生具有这个功能，为收费服务，爬虫自动扫描将近一万种货物的价格，进行分析，将有价值的信息实时自动群发给订购用户 
>> * ##### 热门产品 
>>  　 我方服务人员筛选出热门产品，医生具有查看和订阅功能，护士可以通过医生赋予相应的权限来获得查看热门产品的能力，这个是免费服务，用户可以在该功能里面设定自己的价格，当商品价格低于设定价格后，每当价格变化，爬虫系统会自动发邮件给公司邮件 
>> * ##### 系统推荐产品
>>  　 我方系统推荐的产品，医生具有查看和订阅功能，护士可以通过医生赋予相应的权限来获得查看热门产品的能力，这个是免费服务，用户可以在该功能里面设定自己的价格，当商品价格低于设定价格后，每当价格变化，爬虫系统会自动发邮件给公司邮件 
>> * ##### 所有产品
>>      我方爬虫爬出来的所有产品；医生具有查看和订阅功能，护士可以通过医生赋予相应的权限来获得查看热门产品的能力，这个是免费服务，用户可以在该功能里面设定自己的价格，当商品价格低于设定价格后，每当价格变化，爬虫系统会自动发邮件给公司邮件 
>> * ##### 我的订阅产品 
>>   医生账户的订阅产品，每当商品价格变动时，若低于医生制定的价格，系统会自动发邮件到医生的公司邮箱； 
　 　 

>> ###Deal市场 
>>  未来把deal交易市场开放给所有的医生，让医生也有机会在这个平台上出售自己的deal，别的医生来订阅。我们收取相关的费用。医生需要从管理远获取权限之后才能获得此功能，医生获得此功能后可以授权给护士进行管理
>> * ##### 我的deal管理 　 
>>  这里医生可以发布，修改，删除 deal 信息。 
>> * ##### 公共deal(2天延迟) 　 
>>   所以医生的deal，过了延迟期以后，默认两天. 就会公开到公共deal市场上，让所有医生查看来决定是否有价值，从而决定是否来订阅。 
>> * ##### 我的deal订阅设置
>>  　 设置收费标准和延迟公开时间等等。 
>> * ##### 我订阅的用户 　 
>>   我订阅的用户列表 
>> * ##### 订阅我的用户
>>  订阅我的用户列表 
>> * ##### 我订阅的deal 　 
>>  我订阅的用户发布的deal信息。 

>> ###公司信息管理 　 
>>  用管理公司的基本信息
>> * ##### 公司账户管理 　
>>  医生具有改权限，护士可以从医生那里获得改权限，公司的信息地址的基本管理，医生要定期支付护士钱，要设置支付周期，医生可以设定三个仓库地址，通过邮件告诉护士下单下到那个地址，医生可以通过系统群发邮件给所有护士，所以这里工作邮箱和密码。每个医生的交易规则和打包等规则要求不一样，所以每个医生可以在这里填写自己的用户手册。 
>> * ##### 公司财务 　 
>>  医生具有改权限，护士可以从医生那里获得改权限，医生账余额以及充值记录等都在这里 
>> * ##### 公司账户充值 　 
>>  医生具有改权限，护士可以从医生那里获得改权限，给医生账户充值 
>> * ##### 用户管理 　 
>>  医生具有改权限，护士可以从医生那里获得改权限，对所有公司子账户（护士账户进行管理）修改基本信息和权限。 
>> * ##### 删除用户 　 
>>  医生具有改权限，护士可以从医生那里获得改权限，可以删除子账户。 
>> * ##### 用户分析 　 
>>  医生具有改权限，护士可以从医生那里获得改权限，目前在doctorbody.jsp 文件里屏蔽掉了。要开放出来，需要的功能，在这里对护士的数据进行分析，比如说护士的上单率，上货量，跑单率等等。 
　 　 

>> ###公司产品管理 　 
>>  用来管理公司的业务 
>> * ##### 产品管理
>>   医生具有改权限，护士可以从医生那里获得改权限，可以在这里添加，修改产品信息。 
>> * ##### Deal管理
>>   医生具有改权限，护士可以从医生那里获得改权限，可以在这里选取产品，群发给相应的护士群。（目前系统分类了客户群，未来也要给医生自己分类客户群的功能。医生根据资金的要求决定总数量，发放相应数量的票。为了防止有人恶意领票。也设定每个人可以领取的票数限制。 
>> * ##### 包裹管理
>>    医生具有改权限，护士可以从医生那里获得改权限，确认，修改，删除包裹的信息。如果有问题也可以直接在这里发邮件给护士。 
>> * ##### 库存管理
>>   医生具有改权限，护士可以从医生那里获得改权限，可以在这里查看库存信息，每个地址处于不同状态的货物的数量 
>> * ##### 对单
>>   医生具有改权限，护士可以从医生那里获得改权限，护士下单后，下到仓库的货物，护士要提交单号数量和产品名称，管理人员找仓库对单。下到家里的货物，管理人员要发快递单，护士邮寄出去，到达目的地后，管理人员对单确认，对单后根据包裹的状态调整为complete or unmatch的状态 
>> * ##### 发送Lable
>>   医生具有改权限，护士可以从医生那里获得改权限，护士下货到家里的，管理人员要发送快递单给护士，发送label以后，状态调整为emailedlabel。同时上传快递单到网站，方便护士下载 
>> * ##### 支付用户
>>   医生具有改权限，护士可以从医生那里获得改权限，给护士支付钱，根据护士填写的支付方式进行。 
>> * ##### 发邮件
>>    医生具有改权限，护士可以从医生那里获得改权限，通过系统发邮件。 
　 　 

>> ###任务操作 　 
>> 医生和护士都默认具有这个权限，主要是自己的包裹 
>> * #####领票/预报 　 
>>  护士收到医生给的deal信息以后，根据要求，来到这里领票，领票后可下单，领票后生成一个包裹，包裹状态是unreceived 
>> * #####求收购/议价 　 
>>  如果护士对医生的收货价格不满意，可以来这里进行议价，或者有医生没有列出来的货物想出售给医生，也可以来这里填写，填写好以后生成包裹，包裹为unconfirm 状态 
>> * #####更新包裹信息 　 
>>  护士可以在这里对unreceived 状态的包裹信息进行修改。也可以删除包裹。下到家里的包裹，收到包裹后需要修改成instock 状态，下到仓库的货物，商家发货后，护士要在这里填写单号，方便公司管理人员找仓库对单。 
>> * #####更新信用卡信息 　 
>>   未支付的包裹可以在这里填写信用卡信息，方便公司管理人员进行支付 
>> * #####打包包裹 　 
>>   公司管理人员发送label给护士以后，护士可以在这里进行查看那些包裹需要打包，也可以在这里下载快递单。 
>> * #####正在收购 　 
>>  改权限需要医生授权才可以看到。该处显示医生正在收购的货物，有些货物医生长期收购，就用live deal的形式发布出来。 


>> ###查看包裹 　 
>>   护士可以在这里查看自己所有的包裹 
>> * #####所有包裹 　 
>> * #####未确认包裹 　 
>> * #####未发货包裹 　 
>> * #####途中包裹 　 
>> * #####在家包裹 　 
>> * #####待结算包裹 　 
>> * #####已结算包裹 　 
>> * #####邮寄完成包裹 　 
>> * #####不匹配包裹 　 
　 　 
>> ### 代购业务管理 　 
>>  我目前的业务主要是在美国进行买卖。这里准备开发代购业务。我在美国有几百个注册买手，我认识的一些医生，每个人也有几百个护士，为了整合资源，希望这里能把代购业务做起来，开放国内买家账户注册，国内买家注册账户需要绑定到一个公司，实行买家发布任务，买手接任务，买手遇到好的商品推荐给国内买家的模式，未来这个服务会提供给所有的医生，为收费业务。 
>> * #####接受任务(任务市场)
 >>    护士可以获得改权限，任务市场，可以看到公司内部的所有买家发布的任务。拥有权限的护士可以在任务市场接受任务 
>> * #####已接受任务
 >>    护士可以获得改权限，护士已经接受的任务列表 
>> * #####已完成任务
 >>   护士可以获得改权限，护士已经完成的任务列表 

>> ###推荐产品管理 　 
>>  护士可以获得改权限，护士推荐产品给买家的管理，护士推荐的产品，如果客户要发布任务，该护士具有优先权。护士可以再推荐的时候设置自己接受还是发布到公共市场 
>> * #####所有推荐的产品
 >>    护士可以获得改权限，公司范围内所有所有护士推荐的产品列表 
>> * #####自己推荐的产品 
>>    护士可以获得改权限，护士自己推荐给买家的产品，可以在这里进行修改，删除等操作。 
>> * #####查看推荐产品
 >>    买家可以获得改权限，查看公司范围内所有的推荐产品，点击发布任务直接进入发布任务页面，页面自动加载任务信息 
>> * #####发布任务
 >>    买家可以获得改权限，可以自己填写产品信息，发布到公司市场上 
>> * #####所有个人任务 
>>    买家可以获得改权限，公司范围内所有的买家发布的任务。 
>> * #####已发布的任务 
>>    买家可以获得改权限，买家自己发布的任务，可以在这里对任务进行删除或者修改 
>> * #####已被接受任务
 >>    买家可以获得改权限，查看已经被护士接受的任务。 
>> * #####已被完成任务
 >>    买家可以获得改权限，查看已经被护士完成的任务。 
>> * #####个人账户充值
 >>    买家可以获得改权限，给个人账户充值。
 
>> ###仓库管理 　
>>   授权给仓库的拥有人，这里开发给医生进行仓库管理（还没想好） 
>> * #####接受包裹 
>>    仓库工作员工可以获得此权限，主要负责接收包裹，扫描单号，记录产品upc，数量，包裹上的公司名称等 
>> * #####打包 
>>    仓库工作员工可以获得此权限，主要负责按照要求打包发货 
>> * #####库存管理 
>>    仓库工作员工可以获得此权限，主要负责库存管理 
>> * #####仓库订单管理 
>>    仓库工作员工可以获得此权限，主要负责订单管理 
>> * #####仓库包裹管理 
>>    仓库工作员工可以获得此权限，主要负责包裹管理（还没想好） 
***
尽快完善现在的服务以后，要和我的网络爬虫结合，开发deal订阅功能，然后推出我们的deal订阅服务。紧接着是开发代购和deal交易市场两项功能。最后开发卖货功能，看能不能把我自己的一部分商品再这个网站上卖 
