package com.imooc.ad.sender;

import com.imooc.ad.mysql.dto.MySqlRowData;

/**
 * 投递增量数据的方法
 */
public interface ISender {

    void sender(MySqlRowData rowData);
}
