package com.dazk.common.util.excel.work;

import com.alibaba.fastjson.JSON;
import com.dazk.common.util.CodeUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.common.util.StrUtil;
import com.dazk.common.util.excel.ExcelReader;
import com.dazk.db.model.Building;
import com.dazk.db.model.HouseHolder;
import com.dazk.db.model.HouseValve;
import com.dazk.db.model.ImportException;
import com.dazk.service.AddDeviceService;
import com.dazk.service.ImportExceptionService;
import com.dazk.service.QueryBuildService;
import com.dazk.service.QueryDeviceService;
import com.dazk.validator.JsonParamValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2017/8/13.
 */
@Service("houseValveInfoDetail")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HouseValveInfoDetail extends ExcelReader {
    @Autowired
    private QueryDeviceService queryDeviceService;
    @Autowired
    private AddDeviceService addDeviceService;
    @Autowired
    private QueryBuildService queryBuildService;
    @Autowired
    private ImportExceptionService importExceptionService;

    private List<HouseValve> houseValves = new ArrayList<HouseValve>();
    private ImportException importException;
    private List<String> codes = new ArrayList<String>();

    @Override
    public void optRow(int sheetIndex, int curRow, List<String> rowList, List<String> title, Map<String, Object> data) throws Exception {
        if (filter(curRow)) {
            List<String[]> list = changeList(rowList, title);
            HouseValve model = new HouseValve();

            // 1、EXCEL的数据导入
            for (String[] strings : list) {
                generation(model, map.get(filterAlphabet(strings[0])), strings[1]);
            }

            // 2、页面数据的处理
            importException = new ImportException();
            importException.setBatchid((String) data.get("batchid"));
            importException.setUser_id( data.get("user_id").toString());
            importException.setLine_tag(curRow + 1 + "行,户通断阀：" + model.getHouse_code());
            importException.setError_msg("");

            List<String> housecodes = (List<String>) data.get("housecodes");
            List<String> valvescodes = (List<String>) data.get("valvescodes");

            if (StringUtils.isBlank(model.getHouse_code()) && StringUtils.isBlank(model.getComm_address()) && model.getType() == null ) {

            } else {
                check(model,housecodes,valvescodes);
                model.setIsdel(0);
                model.setCreated_at(System.currentTimeMillis() / 1000);
//				model.setBuilding_code(community_code + CodeUtil.getTwoDigitCode(Integer.parseInt(model.getCode())));
//				model.setCode(model.getBuilding_code()+model.getUnit_code()+CodeUtil.getTwoDigitCode(houseHolderService.getHotStationId()));
                houseValves.add(model);
            }
        }
    }

    // 读取完EXCEL时检查
    @Override
    public void callback() {
        Example example = new Example(ImportException.class);
        example.createCriteria().andEqualTo("user_id", importException.getUser_id())
                .andEqualTo("batchid", importException.getBatchid())
                .andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_HVALVE);
        if (importExceptionService.countImportException(example) < 1) {
            insert();
        }
    }

    // 处理插入问题！
    private void insert() {
        try {
            for (HouseValve model : houseValves) {
                if (StringUtils.isBlank(model.getHouse_code()) && StringUtils.isBlank(model.getComm_address()) && model.getType() == null) {

                } else {
                    importException = new ImportException();
                    addDeviceService.addHouseValve(model);
                }
            }
        } catch (Exception ex) {
            String error = ex.toString();
            // 格式化错误信息：最大为4096长度信息。
            error = (StringUtils.isNotEmpty(error) && StringUtils.length(error) > 2048)
                    ? StringUtils.substring(error, 0, 2048) : error;
            importException.setError_msg(error);
            importException.setImport_type(CodeUtil.IMPORT_DATA_TYPE_HVALVE);
            importExceptionService.insert(importException);
        }
    }

    private void check(HouseValve model,List<String> housecodes,List<String> valvescodes) {
        System.out.println(JSON.toJSONString(model));
        boolean flag = true;
        //校验住户编号
        String house_code = model.getHouse_code();
        if(!JsonParamValidator.isHouseCode(house_code)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "住户编号："+model.getHouse_code()+"错误，请核查后提交！");
        }else if(!StrUtil.hasPerIn(house_code,housecodes)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "住户："+model.getHouse_code()+"不存在或无此住户操作权限，请核查后提交！");
        }else if(StrUtil.hasPerIn(house_code,valvescodes)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "住户："+model.getHouse_code()+"已存在此设备，请核查后提交！");
        }
        if(model.getType() == null || ( model.getType() != 0 && model.getType() != 1)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "设备类型错误，请核查后提交！");
        }

        if(!JsonParamValidator.isCommAddress(model.getComm_address())){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "通信地址错误，请核查后提交！");
        }

        if(model.getPower_type() != null && model.getPower_type() != 0 && model.getPower_type() != 1){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "供电类型错误，请核查后提交！");
        }

        if(model.getVersion() != null && model.getVersion().length() > 32){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "版本最多32字符，请核查后提交！");
        }

        if(model.getRemark() != null && model.getRemark().length() > 128){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "备注最多128字符，请核查后提交！");
        }
        if (!flag) {
            importException.setImport_type(CodeUtil.IMPORT_DATA_TYPE_HVALVE);
            importExceptionService.insert(importException);
        }
    }

    protected boolean filter(int curRow) {
        return curRow > 0;
    }

    protected static final Map<String, String> map = new ConcurrentHashMap<String, String>();

    static {
        map.put("A", "house_code");
        map.put("B", "type");
        map.put("C", "comm_address");
        map.put("D", "power_type");
        map.put("E", "version");
        map.put("F", "remark");
        map.put("G","address");
    }
}
