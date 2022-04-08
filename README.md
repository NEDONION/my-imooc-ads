# 基于SpringCloud的广告系统
实现广告系统中最为核心的两个模块：广告投放系统与广告检索系统，并测试它们的可用性，学习广告系统的设计思想，实现方法。

- 基于SpringCloud框架开发，使用了Eureka、Zuul、Ribbon、Feign 以及 Hystrix组件. Eureka 用于服务的注册和服务信息的获取，Zull 和 Feign 都依赖于 Eureka 中存储的服务信息；Zuul 是网关，是整个工程的入口；Ribbon 和 Feign 用于访问其他的微服务，其实和你使用 RestTemplate 去访问没有实质上的区别，只是框架把它们封装的更加易于使用；Hystrix 用于熔断和降级，接口出错的时候，可以对接口的访问实现兜底.
- 在JVM中构造索引，使用倒排索引加速了检索的过程.
- 使用了Binlog作为增量索引的更新工具，监听和解析Binlog的过程.
- 使用了Kafka对增量数据的更新过程进行优化，减轻MySQL的压力.
