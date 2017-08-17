package com.dazk.db.dao;

import com.dazk.common.util.DeviceMapper;
import com.dazk.db.model.DatePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
public interface DatePermissionMapper {
    List<String> getPerm(@Param("userid") Integer userid, @Param("codes") List<String> codes,@Param("datatype") Integer datatype);
}
