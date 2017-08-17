package com.dazk.db.dao;

import com.dazk.db.param.HouseHolderValveParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */
public interface HouseHolderValveMapper {
    List<HouseHolderValveParam> queryHouseHolderValve(@Param("codes") List<String> codes,@Param("error") Integer error,@Param("opening") Integer opening,@Param("mes_status") Integer mes_status,@Param("id_card") String id_card,@Param("err_code") String err_code,@Param("start") Integer sart,@Param("listRows") Integer listRows);
    Integer queryHouseHolderValveCount(@Param("codes") List<String> codes,@Param("error") Integer error,@Param("opening") Integer opening,@Param("mes_status") Integer mes_status,@Param("id_card") String id_card,@Param("err_code") String err_code,@Param("start") Integer sart,@Param("listRows") Integer listRows);
}
