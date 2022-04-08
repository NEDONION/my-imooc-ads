package com.imooc.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {

    private Long adId; // 广告Id
    private Long unitId; // 推广单元Id

    // adId-unitId 结合到1起构造为String，可作为唯一key
}
