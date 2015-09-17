鸿瑞云视频pispower-java-sdk
===================
>版权所有 （C）广州鸿瑞网络有限公司

>https://video.cloudak47.com

环境要求
-------------
>J2SE Development Kit(JDK)7.0及以上版本
>必须注册有鸿瑞云视频帐号(登录-服务设置-RestfulAPI支持,获取AccessKey,AccessSecret)

提供了如下功能:
>视频目录:对视频目录的操作
>视频:对视频的操作
> 断点续传:支持对视频的断点续传
>广告:对广告的操作
>广告投放:广告投放操作

结构
-------------
```
pispower-java-sdk
   |
   |---src
   |    |
   |    |---com.pispower.video.api.catalog   	catalog操作源代码
   |    |
   |    |---com.pispower.video.api.internal   	内部访问源代码
   |    |
   |    |---com.pispower.video.api.video      	video操作源代码
   |    |     |
   |    |     |---multipartupload   			断点续传源代码
   |    |     |
   |    |     |---others						video操作
   |    |
   |    |---com.pispower.video.api.ad			广告操作源代码
   |    |
   |    |---com.pispower.video.api.advertise    广告投放源代码
   |
   |---demo			   							示例源代码
   |    |
   |    |---com.pispower.video.api.video
   |    |
   |    |---com.pispower.video.api.catalog
   |
   |---src/test
        |
        |---com.pispower.video.api.ad           广告操作单元测试
        |
        |---com.pispower.video.api.advertise    广州投放单元测试
```

详细参考文档
-------------
开发者支持-RestfulAPI

联系我们
-------------
客服热线：400-668-1010
技术支持：support.video@onecloud.cn