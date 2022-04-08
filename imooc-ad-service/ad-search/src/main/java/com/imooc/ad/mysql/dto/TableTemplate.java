package com.imooc.ad.mysql.dto;

import com.imooc.ad.mysql.constant.OpType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ParseTemplate作为一个转换器，返回一个ParseTemplate对象，而其中的map数据封装了json文件的一些格式信息，
 * TableTemplate相当于存储信息的一个载体。其中ParseTemplate中的map以< 数据库名，数据库内部数据>存储。
 * TableTemplate中的map则封装了< binglog 中的操作类型，修改的数据>形式存储。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    private String tableName;
    private String level;

    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引 -> 字段名
     * */
    private Map<Integer, String> posMap = new HashMap<>();
}
