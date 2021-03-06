package com.dazk.common.util.excel.work;

import com.alibaba.fastjson.JSON;
import com.dazk.common.util.CodeUtil;
import com.dazk.common.util.RegexUtil;
import com.dazk.common.util.StrUtil;
import com.dazk.common.util.excel.ExcelReader;
import com.dazk.db.model.BuildingValve;
import com.dazk.db.model.Concentrator;
import com.dazk.db.model.ImportException;
import com.dazk.service.AddDeviceService;
import com.dazk.service.ImportExceptionService;
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
 * Created by Administrator on 2017/8/14.
 */
@Service("concentratorInfoDetail")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ConcentratorInfoDetail extends ExcelReader {
    @Autowired
    private AddDeviceService addDeviceService;
    @Autowired
    private ImportExceptionService importExceptionService;

    private List<Concentrator> deviceList = new ArrayList<Concentrator>();
    private ImportException importException;
    private List<String> codes = new ArrayList<String>();

    @Override
    public void optRow(int sheetIndex, int curRow, List<String> rowList, List<String> title, Map<String, Object> data) throws Exception {
        if (filter(curRow)) {
            List<String[]> list = changeList(rowList, title);
            Concentrator model = new Concentrator();

            // 1、EXCEL的数据导入
            for (String[] strings : list) {
                generation(model, map.get(filterAlphabet(strings[0])), strings[1]);
            }

            // 2、页面数据的处理
            importException = new ImportException();
            importException.setBatchid((String) data.get("batchid"));
            importException.setUser_id( data.get("user_id").toString());
            importException.setLine_tag(curRow + 1 + "行,采集器：" + model.getBuilding_unique_code());
            importException.setError_msg("");

            List<String> housecodes = (List<String>) data.get("housecodes");
            List<String> devicecodes = (List<String>) data.get("devicecodes");

            if (StringUtils.isBlank(model.getBuilding_unique_code()) && StringUtils.isBlank(model.getCode()) && model.getProtocol_type() == null ) {

            } else {
                check(model,housecodes,devicecodes);
                model.setIsdel(0);
                model.setCreated_at(System.currentTimeMillis() / 1000);
//				model.setBuilding_code(community_code + CodeUtil.getTwoDigitCode(Integer.parseInt(model.getCode())));
//				model.setCode(model.getBuilding_code()+model.getUnit_code()+CodeUtil.getTwoDigitCode(houseHolderService.getHotStationId()));
                deviceList.add(model);
            }
        }
    }

    // 读取完EXCEL时检查
    @Override
    public void callback() {
        Example example = new Example(ImportException.class);
        example.createCriteria().andEqualTo("user_id", importException.getUser_id())
                .andEqualTo("batchid", importException.getBatchid())
                .andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_CONCENTRATOR);
        if (importExceptionService.countImportException(example) < 1) {
            insert();
        }
    }

    // 处理插入问题！
    private void insert() {
        try {
            for (Concentrator model : deviceList) {
                if (StringUtils.isBlank(model.getBuilding_unique_code()) && StringUtils.isBlank(model.getCode()) && model.getProtocol_type() == null) {

                } else {
                    importException = new ImportException();
                    addDeviceService.addConcentratorByObj(model);
                }
            }
        } catch (Exception ex) {
            String error = ex.toString();
            // 格式化错误信息：最大为4096长度信息。
            error = (StringUtils.isNotEmpty(error) && StringUtils.length(error) > 2048)
                    ? StringUtils.substring(error, 0, 2048) : error;
            importException.setError_msg(error);
            importException.setImport_type(CodeUtil.IMPORT_DATA_TYPE_CONCENTRATOR);
            importExceptionService.insert(importException);
        }
    }

    private void check(Concentrator model,List<String> housecodes,List<String> devicecodes) {
        boolean flag = true;
        //校验编号
        String house_code = model.getBuilding_unique_code();
        if(!JsonParamValidator.isBuildingUniqueCode(house_code)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "楼栋唯一编号："+model.getBuilding_unique_code()+"错误，请核查后提交！");
        }else if(!StrUtil.hasPerIn(house_code,housecodes)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "楼栋："+model.getBuilding_unique_code()+"不存在或无此住户操作权限，请核查后提交！");
        }else if(StrUtil.hasPerIn(house_code,devicecodes)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "住户："+model.getBuilding_unique_code()+"已存在此设备，请核查后提交！");
        }
        if(model.getCode() == null || !RegexUtil.isHex(model.getCode()) || model.getCode().length() > 32){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "设备编号错误，请核查后提交！");
        }

        if(model.getRemark() != null && model.getRemark().length() > 128){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "备注最多128字符，请核查后提交！");
        }

        if(model.getAddress() != null && model.getAddress().length() > 128){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "地址最多128字符，请核查后提交！");
        }

        if(model.getProtocol_type() == null){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "协议类型，请核查后提交！");
        }

        if(model.getGprsid() != null && (!RegexUtil.isDigits(model.getGprsid()) || model.getGprsid().length() > 32)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "sim卡号错误，请核查后提交！");
        }

        if(model.getSim_code() != null && (!RegexUtil.isHex(model.getSim_code()) || model.getSim_code().length() > 32)){
            flag = false;
            importException.setError_msg(importException.getError_msg() + "sim卡串号错误，请核查后提交！");
        }

        if (!flag) {
            importException.setImport_type(CodeUtil.IMPORT_DATA_TYPE_CONCENTRATOR);
            importExceptionService.insert(importException);
        }
    }

    protected boolean filter(int curRow) {
        return curRow > 0;
    }

    protected static final Map<String, String> map = new ConcurrentHashMap<String, String>();

    static {
        map.put("A", "building_unique_code");
        map.put("B", "name");
        map.put("C", "code");
        map.put("D", "gprsid");
        map.put("E", "sim_code");
        map.put("F", "protocol_type");
        map.put("G", "debug_status");
        map.put("H", "remark");
    }
}
