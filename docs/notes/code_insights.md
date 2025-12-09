## 核心代码讲解

- 检索主流程：按广告位 positionType 取候选单元，基于 AND/OR 对关键词、地域、兴趣标签做集合过滤，再校验计划/单元状态，映射创意并按尺寸/类型过滤，最后为每个广告位随机返回一个创意。
```46:125:imooc-ad-service/ad-search/src/main/java/com/imooc/ad/search/impl/SearchImpl.java
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        // ...
        Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
        if (relation == FeatureRelation.AND) {
            filterKeywordFeature(adUnitIdSet, keywordFeature);
            filterDistrictFeature(adUnitIdSet, districtFeature);
            filterItTagFeature(adUnitIdSet, itFeature);
        } else {
            targetUnitIdSet = getORRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, itFeature);
        }
        List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
        filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
        List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
        List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);
        filterCreativeByAdSlot(creatives, adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());
        adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creatives));
    }
```

- 推广计划写入：参数校验→用户存在性校验→同名检测→保存；更新/删除同样按用户维度并做软删除，事务保护。
```36:144:imooc-ad-service/ad-sponsor/src/main/java/com/imooc/ad/service/impl/AdPlanServiceImpl.java
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) { throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR); }
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) { throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD); }
        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) { throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR); }
        AdPlan newAdPlan = planRepository.save(new AdPlan(
                request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())));
        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) { throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR); }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) { throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD); }
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }
```

