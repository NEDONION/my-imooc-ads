## 模块拆分与入口

- `ad-eureka`：注册中心，`EurekaApplication` 启动，`application.yml` 单点/多节点配置示例。
- `ad-gateway`：Zuul 网关，`ZuulGatewayApplication` 启动，`PreRequestFilter` 记录请求起始时间，`AccessLogFilter` 在 POST 阶段打印耗时。
- `imooc-ad-service/ad-common`：
  - `advice` 统一异常/响应包装；`annotation` 标记表字段映射与忽略包装；`conf.WebConfiguration` 注册拦截器。
  - `dump` 导出常量与表模型；`exception.AdException`; `vo.CommonResponse`。
- `imooc-ad-service/ad-sponsor`（写入域）：
  - 入口 `SponsorApplication`（Eureka + Feign + Hystrix）。
  - `controller`：用户、计划、单元、创意 CRUD HTTP 接口。
  - `service/impl`：业务校验与事务写库（示例 `AdPlanServiceImpl`）。
  - `dao/entity/vo`：JPA 仓库、实体与请求/响应模型；`constant` 记录状态/错误码；`utils` 时间解析等。
- `imooc-ad-service/ad-search`（检索域）：
  - 入口 `SearchApplication`（Eureka + Feign + HystrixDashboard + LoadBalanced RestTemplate）。
  - `index`：内存索引（广告计划/单元/创意及定向维度的倒排/正排表）。
  - `mysql`：Binlog 解析、模板、监听器、常量；`sender`：Kafka/索引分发；`handler`：增量落地处理。
  - `search`：`ISearch` + `SearchImpl` 完成多维过滤与创意选择。
  - `client`：Feign 访问写侧；`utils.CommonUtils`；`search/vo` 请求/响应与特征建模。
- `imooc-ad-service/ad-dashboard`：Hystrix Dashboard 监控展示，入口 `DashboardApplication`。

