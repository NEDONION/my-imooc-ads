## 项目架构与技术栈

- 项目类型：基于 Spring Cloud Finchley 的广告平台后端，微服务化拆分（注册中心、网关、业务、检索、监控）。
- 技术栈：Spring Boot 2.0.2、Spring Cloud（Eureka、Zuul、Feign、Ribbon、Hystrix/Hystrix Dashboard）、Kafka（增量同步）、MySQL + Binlog（增量索引）、Lombok、Fastjson。
- 模块拓扑：
  - `ad-eureka`：服务注册中心。
  - `ad-gateway`：Zuul 网关，承载前置过滤与日志。
  - `imooc-ad-service/ad-common`：通用组件与模型（统一响应/异常、注解、索引/导出常量、Web 配置）。
  - `imooc-ad-service/ad-sponsor`：广告主侧 CRUD（用户/计划/单元/创意与定向维度），负责写侧与数据源。
  - `imooc-ad-service/ad-search`：内存倒排索引与检索服务，消费 Binlog/Kafka 增量更新。
  - `imooc-ad-service/ad-dashboard`：Hystrix Dashboard 监控面板。
- 运行形态：各模块独立 Spring Boot 应用，通过 Eureka 注册/发现，Zuul 提供统一入口；ad-sponsor 写 MySQL，ad-search 在 JVM 内维护索引，通过异步增量保持同步。

### 形态示意
```
Client -> Zuul(ad-gateway) -> ad-sponsor (写入MySQL, 触发Binlog/Kafka)
                                  \
                                   -> ad-search (消费增量, 内存索引, 提供检索)
Dashboard(Hystrix) 监控各微服务
Eureka 提供服务发现
```

