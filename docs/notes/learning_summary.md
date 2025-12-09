## 学习总结

- 设计思路：以 Spring Cloud 微服务拆分注册中心、网关、写侧、读侧、监控，写侧负责一致性与约束，读侧将数据投影为内存倒排索引，借助 Binlog/Kafka 做准实时同步，读写解耦。
- 优点可借鉴：索引分层（计划/单元/创意/定向维度）+ AND/OR 过滤组合；软删除避免脏读；请求/响应 VO 清晰；Hystrix + Dashboard 提供可观测性。
- 潜在优化：fallback 当前返回 null，可完善降级结果；索引过滤只选取单个随机创意，可加入多样性和排序策略；增量链路可加入幂等与重放测试；配置与密钥建议集中化管理。
- 启动要点：先起 `ad-eureka`，再起 `ad-gateway`、`ad-sponsor`、`ad-search`、`ad-dashboard`；保证 MySQL/Kafka/Binlog 配置与 `application.yml` 一致。

