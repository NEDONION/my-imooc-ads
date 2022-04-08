package com.imooc.ad.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitResponse {
    // 返回主键
    private List<Long> ids;
}
