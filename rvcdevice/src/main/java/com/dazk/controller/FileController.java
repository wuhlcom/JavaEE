package com.dazk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dazk.common.DeviceError;
import com.dazk.common.ErrCode;
import com.dazk.common.SystemConfig;
import com.dazk.common.util.*;
import com.dazk.common.util.excel.work.*;
import com.dazk.db.model.*;
import com.dazk.db.param.HouseHolderValveParam;
import com.dazk.service.*;
import com.dazk.validator.JsonParamValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private HouseValveInfoDetail houseValveInfoDetail;


    @Autowired
    private ImportExceptionService importExceptionService;

    @Autowired
    private RedisService redisService;

    @Resource
    private DataPermService dataPermService;

    @Resource
    private QueryDeviceService queryDeviceService;

    @Resource
    private QueryBuildService queryBuildService;

    @Resource
    private BuildingValveInfoDetail buildingValveInfoDetail;

    @Resource
    private BuildingCalorimeterInfoDetail buildingCalorimeterInfoDetail;

    @Resource
    private ConcentratorInfoDetail concentratorInfoDetail;

    @Resource
    private GatewayInfoDetail gatewayInfoDetail;


    @RequestMapping(value = "/importValve", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importValve(MultipartHttpServletRequest request,@RequestHeader(value="token") String token, HttpServletResponse response){
        try{
            JSONObject resultObj = new JSONObject();
            Integer userid =  dataPermService.getUserid(token,resultObj);
            List<String> codes = dataPermService.getAll(userid, SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg", "用户无权限！");
            }

            List<HouseHolder> holders = queryDeviceService.getHouseholderByCodes(codes);
            List<String> housecodes = new ArrayList<String>();
            for(HouseHolder obj : holders){
                housecodes.add(obj.getCode());
            }

            List<HouseValve> valves = queryDeviceService.getHouseValveByCodes(codes);
            List<String> valvescodes = new ArrayList<String>();
            for(HouseValve obj : valves){
                valvescodes.add(obj.getHouse_code());
            }

            MultipartFile file = request.getFile("file");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("codes", codes);
            data.put("housecodes",housecodes);
            data.put("valvescodes",valvescodes);
            data.put("user_id", userid);
            data.put("batchid", String.valueOf(System.currentTimeMillis()));
            houseValveInfoDetail.process(file,data);

            Example example = new Example(ImportException.class);
            example.createCriteria().andEqualTo("user_id", userid).andEqualTo("batchid", data.get("batchid")).andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_HVALVE);
            int exceptions = importExceptionService.countImportException(example );//出现异常
            if(exceptions>0){
                resultObj.put("errcode", ErrCode.IMPORT_ERR);
                resultObj.put("msg", "导入异常，具体异常信息参考异常明细！");
            }else{
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("result", "导入成功!");
            }
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("errcode", ErrCode.routineErr);
            result.put("msg", e.toString());
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/exportValve", produces="text/plain;charset=UTF-8")
    public String exportValve(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody,@RequestHeader(value = "token") String token){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.valveQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }

            //用户校验及权限验证
            int isLiberty  =  dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //查询条件
            HouseValve condition = JSON.parseObject(parameter.toJSONString(), HouseValve.class);
            //数据查询，成功后返回.
            List<HouseValve> result = queryDeviceService.queryValve(codes,null,null,null);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }

            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("户通断阀数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.HOUSE_VALVE_TITLE,wb);//添加标题行

            houseValveOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("户通断阀数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void houseValveOut(Sheet sheet, List<HouseValve> list,CellStyle style) {
        // 表格内容{"热力公司","热站名称","小区名称","楼栋名称","住户编号","户主","缴费号","单元号","采暖面积","缴费状态","联系电话"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            HouseValve obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getHouse_code());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getType().toString());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getComm_address());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(obj.getPower_type());

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(obj.getVersion());

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(obj.getRemark());

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getAddress());
        }
    }

    @RequestMapping(value = "/importHouseCalorimeter", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importHouseCalorimeter(MultipartHttpServletRequest request,@RequestHeader(value="token") String token, HttpServletResponse response){
        try{
            JSONObject resultObj = new JSONObject();
            Integer userid =  dataPermService.getUserid(token,resultObj);
            List<String> codes = dataPermService.getAll(userid, SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg", "用户无权限！");
            }

            List<HouseHolder> holders = queryDeviceService.getHouseholderByCodes(codes);
            List<String> housecodes = new ArrayList<String>();
            for(HouseHolder obj : holders){
                housecodes.add(obj.getCode());
            }

            List<HouseCalorimeter> devices = queryDeviceService.getHouseCalorimeterByCodes(codes);
            List<String> devicecodes = new ArrayList<String>();
            for(HouseCalorimeter obj : devices){
                devicecodes.add(obj.getHouse_code());
            }

            MultipartFile file = request.getFile("file");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("codes", codes);
            data.put("housecodes",housecodes);
            data.put("devicecodes",devicecodes);
            data.put("user_id", userid);
            data.put("batchid", String.valueOf(System.currentTimeMillis()));
            houseValveInfoDetail.process(file,data);

            Example example = new Example(ImportException.class);
            example.createCriteria().andEqualTo("user_id", userid).andEqualTo("batchid", data.get("batchid")).andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_HVALVE);
            int exceptions = importExceptionService.countImportException(example );//出现异常
            if(exceptions>0){
                resultObj.put("errcode", ErrCode.IMPORT_ERR);
                resultObj.put("msg", "导入异常，具体异常信息参考异常明细！");
            }else{
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("result", "导入成功!");
            }
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("errcode", ErrCode.routineErr);
            result.put("msg", e.toString());
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/exportHouseCalorimeter", produces="text/plain;charset=UTF-8")
    public String exportHouseCalorimeter(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="token") String token, @RequestBody String requestBody){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.houseCalorimeterQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证
            int isLiberty  =  dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));
            //查询条件
            HouseCalorimeter condition = JSON.parseObject(parameter.toJSONString(), HouseCalorimeter.class);
            //数据查询，成功后返回.
            List<HouseCalorimeter> result = queryDeviceService.queryHouseCalorimeter(codes,page,listRows,condition);
            condition = JSON.parseObject(parameter.toJSONString(), HouseCalorimeter.class);
            int totalRows = queryDeviceService.queryHouseCalorimeterCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }

            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("户用热表数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.HOUSE_CALORIMETER_TITLE,wb);//添加标题行

            houseCalorimeterOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("户用热表数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void houseCalorimeterOut(Sheet sheet, List<HouseCalorimeter> list,CellStyle style) {
        // 表格内容{"热力公司","热站名称","小区名称","楼栋名称","住户编号","户主","缴费号","单元号","采暖面积","缴费状态","联系电话"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            HouseCalorimeter obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getHouse_code());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getType().toString());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getComm_address());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(obj.getPro_type());

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(obj.getPipe_size());

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(obj.getRemark());

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getAddress());
        }
    }

    @RequestMapping(value = "/importBuildingValve", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importBuildingValve(MultipartHttpServletRequest request,@RequestHeader(value="token") String token, HttpServletResponse response){
        try{
            JSONObject resultObj = new JSONObject();
            Integer userid =  dataPermService.getUserid(token,resultObj);
            List<String> codes = dataPermService.getAll(userid, SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg", "用户无权限！");
            }

            List<Building> buildings = queryBuildService.getBuildingByCodes(codes);
            List<String> buildingcodes = new ArrayList<String>();
            for(Building obj : buildings){
                buildingcodes.add(obj.getUnique_code());
            }

            List<BuildingValve> devices = queryDeviceService.getBuildingValveByCodes(codes);
            List<String> devicecodes = new ArrayList<String>();
            for(BuildingValve obj : devices){
                devicecodes.add(obj.getBuilding_unique_code());
            }

            MultipartFile file = request.getFile("file");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("codes", codes);
            data.put("buildingcodes",buildingcodes);
            data.put("devicecodes",devicecodes);
            data.put("user_id", userid);
            data.put("batchid", String.valueOf(System.currentTimeMillis()));
            buildingValveInfoDetail.process(file,data);

            Example example = new Example(ImportException.class);
            example.createCriteria().andEqualTo("user_id", userid).andEqualTo("batchid", data.get("batchid")).andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_BVALVE);
            int exceptions = importExceptionService.countImportException(example );//出现异常
            if(exceptions>0){
                resultObj.put("errcode", ErrCode.IMPORT_ERR);
                resultObj.put("msg", "导入异常，具体异常信息参考异常明细！");
            }else{
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("result", "导入成功!");
            }
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("errcode", ErrCode.routineErr);
            result.put("msg", e.toString());
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/exportBuildingValve", produces="text/plain;charset=UTF-8")
    public String exportBuildingValve(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="token") String token, @RequestBody String requestBody){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.buildingValveQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证

            int isLiberty  =  dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            BuildingValve condition = JSON.parseObject(parameter.toJSONString(), BuildingValve.class);
            //数据查询，成功后返回.
            List<BuildingValve> result = queryDeviceService.queryBuildingValve(codes,page,listRows,condition);

            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData
            condition = JSON.parseObject(parameter.toJSONString(), BuildingValve.class);
            int totalRows = queryDeviceService.queryBuildingValveCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }

            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("楼栋调节阀数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.BUILDING_VALVE_TITLE,wb);//添加标题行

            buildingValveOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("楼栋通调节阀数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buildingValveOut(Sheet sheet, List<BuildingValve> list,CellStyle style) {
        // 表格内容{"楼栋唯一编号","设备类型","通信地址","名称","在线状态","故障","mbus地址","管径","备注","位置"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            BuildingValve obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getBuilding_unique_code());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getType().toString());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getComm_address());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(obj.getName());

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(obj.getOnline() == 1?"在线":"离线");

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(DeviceError.getErrStr(obj.getErr_code()));

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getMbus());

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style);
            cell7.setCellValue(obj.getPipe_size());

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style);
            cell8.setCellValue(obj.getRemark());

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style);
            cell9.setCellValue(obj.getAddress());
        }
    }

    @RequestMapping(value = "/importBuildingCalorimeter", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importBuildingCalorimeter(MultipartHttpServletRequest request,@RequestHeader(value="token") String token, HttpServletResponse response){
        try{
            JSONObject resultObj = new JSONObject();
            Integer userid =  dataPermService.getUserid(token,resultObj);
            List<String> codes = dataPermService.getAll(userid, SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg", "用户无权限！");
            }

            List<Building> buildings = queryBuildService.getBuildingByCodes(codes);
            List<String> buildingcodes = new ArrayList<String>();
            for(Building obj : buildings){
                buildingcodes.add(obj.getUnique_code());
            }

            List<BuildingCalorimeter> devices = queryDeviceService.getBuildingCalorimeterByCodes(codes);
            List<String> devicecodes = new ArrayList<String>();
            for(BuildingCalorimeter obj : devices){
                devicecodes.add(obj.getBuilding_unique_code());
            }

            MultipartFile file = request.getFile("file");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("codes", codes);
            data.put("buildingcodes",buildingcodes);
            data.put("devicecodes",devicecodes);
            data.put("user_id", userid);
            data.put("batchid", String.valueOf(System.currentTimeMillis()));
            buildingCalorimeterInfoDetail.process(file,data);

            Example example = new Example(ImportException.class);
            example.createCriteria().andEqualTo("user_id", userid).andEqualTo("batchid", data.get("batchid")).andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_BCALORIMETER);
            int exceptions = importExceptionService.countImportException(example );//出现异常
            if(exceptions>0){
                resultObj.put("errcode", ErrCode.IMPORT_ERR);
                resultObj.put("msg", "导入异常，具体异常信息参考异常明细！");
            }else{
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("result", "导入成功!");
            }
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("errcode", ErrCode.routineErr);
            result.put("msg", e.toString());
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/exportBuildingCalorimeter", produces="text/plain;charset=UTF-8")
    public String exportBuildingCalorimeter(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="token") String token, @RequestBody String requestBody){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.buildingCalorimeterQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证

            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            BuildingCalorimeter condition = JSON.parseObject(parameter.toJSONString(), BuildingCalorimeter.class);
            //数据查询，成功后返回.
            List<BuildingCalorimeter> result = queryDeviceService.queryBuildingCalorimeter(codes,page,listRows,condition);
            condition = JSON.parseObject(parameter.toJSONString(), BuildingCalorimeter.class);
            int totalRows = queryDeviceService.queryBuildingCalorimeterCount(codes,condition);

            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }

            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("楼栋热表数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.BUILDING_CALORIMETER_TITLE,wb);//添加标题行

            buildingCalorimeterOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("楼栋热表数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void buildingCalorimeterOut(Sheet sheet, List<BuildingCalorimeter> list,CellStyle style) {
        // 表格内容{"楼栋唯一编号","设备类型","通信地址","名称","在线状态","故障","mbus地址","管径","备注","位置"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            BuildingCalorimeter obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getBuilding_unique_code());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getType().toString());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getComm_address());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(obj.getName());

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(obj.getOnline() == 1?"在线":"离线");

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(DeviceError.getErrStr(obj.getErr_code()));

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getMbus());

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style);
            cell7.setCellValue(obj.getPro_type());

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style);
            cell8.setCellValue(obj.getPipe_size());

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style);
            cell9.setCellValue(obj.getAddress());

            Cell cell10 = row.createCell(10);
            cell10.setCellStyle(style);
            cell10.setCellValue(obj.getRemark());
        }
    }

    @RequestMapping(value = "/importConcentrator", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importConcentrator(MultipartHttpServletRequest request,@RequestHeader(value="token") String token, HttpServletResponse response){
        try{
            JSONObject resultObj = new JSONObject();
            Integer userid =  dataPermService.getUserid(token,resultObj);
            List<String> codes = dataPermService.getAll(userid, SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg", "用户无权限！");
            }

            List<Building> buildings = queryBuildService.getBuildingByCodes(codes);
            List<String> buildingcodes = new ArrayList<String>();
            for(Building obj : buildings){
                buildingcodes.add(obj.getUnique_code());
            }

            List<Concentrator> devices = queryDeviceService.getConcentratorByCodes(codes);
            List<String> devicecodes = new ArrayList<String>();
            for(Concentrator obj : devices){
                devicecodes.add(obj.getBuilding_unique_code());
            }

            MultipartFile file = request.getFile("file");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("codes", codes);
            data.put("buildingcodes",buildingcodes);
            data.put("devicecodes",devicecodes);
            data.put("user_id", userid);
            data.put("batchid", String.valueOf(System.currentTimeMillis()));
            concentratorInfoDetail.process(file,data);

            Example example = new Example(ImportException.class);
            example.createCriteria().andEqualTo("user_id", userid).andEqualTo("batchid", data.get("batchid")).andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_CONCENTRATOR);
            int exceptions = importExceptionService.countImportException(example );//出现异常
            if(exceptions>0){
                resultObj.put("errcode", ErrCode.IMPORT_ERR);
                resultObj.put("msg", "导入异常，具体异常信息参考异常明细！");
            }else{
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("result", "导入成功!");
            }
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("errcode", ErrCode.routineErr);
            result.put("msg", e.toString());
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/exportConcentrator", produces="text/plain;charset=UTF-8")
    public String exportConcentrator(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="token") String token, @RequestBody String requestBody){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.concentratorQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }
            //用户校验及权限验证
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));

            //查询条件
            Concentrator condition = JSON.parseObject(parameter.toJSONString(), Concentrator.class);
            //数据查询，成功后返回.
            List<Concentrator> result = queryDeviceService.queryConcentrator(codes,page,listRows,condition);

            //调用知路接口得到 阀门状态，进水温度，回水温度 后存入HouseValveData
            condition = JSON.parseObject(parameter.toJSONString(), Concentrator.class);
            int totalRows = queryDeviceService.queryConcentratorCount(codes,condition);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }

            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("采集器数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.CONCENTRATOR_TITLE,wb);//添加标题行

            concentratorOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("采集器数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void concentratorOut(Sheet sheet, List<Concentrator> list,CellStyle style) {
        // 表格内容{"楼栋唯一编号","设备类型","通信地址","名称","在线状态","故障","mbus地址","管径","备注","位置"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            Concentrator obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getBuilding_unique_code());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getCode());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getName());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(obj.getOnline() == 1?"在线":"离线");

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(DeviceError.getErrStr(obj.getErr_code()));

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(obj.getGprsid());

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getSim_code());

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style);
            String protocolStr = null;
            if(obj.getProtocol_type() == 1) {protocolStr = "通断面积法";}else if(obj.getProtocol_type() == 2){protocolStr = "温度面积法";}else if(obj.getProtocol_type() == 3){protocolStr = "户用热表法";}
            cell7.setCellValue(protocolStr);

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style);
            String debugStr = null;
            if(obj.getDebug_status() == 0) {debugStr = "未插卡";}else if(obj.getDebug_status() == 1){debugStr = "已插卡";}else if(obj.getDebug_status() == 2){debugStr = "已调试";}
            cell8.setCellValue(obj.getDebug_status());

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style);
            cell9.setCellValue(obj.getRemark());

            Cell cell10 = row.createCell(10);
            cell10.setCellStyle(style);
            cell10.setCellValue(obj.getAddress());
        }
    }

    @RequestMapping(value = "/importGateway", produces="text/plain;charset=UTF-8")
    @ResponseBody
    public String importGateway(MultipartHttpServletRequest request,@RequestHeader(value="token") String token, HttpServletResponse response){
        try{
            JSONObject resultObj = new JSONObject();
            Integer userid =  dataPermService.getUserid(token,resultObj);
            List<String> codes = dataPermService.getAll(userid, SystemConfig.dataTypeUpdate);
            if(codes.size() == 0){
                resultObj.put("errcode", ErrCode.noPermission);
                resultObj.put("msg", "用户无权限！");
            }

            List<Company> buildings = queryBuildService.getCompanyByCodes(codes);
            List<String> buildingcodes = new ArrayList<String>();
            for(Company obj : buildings){
                buildingcodes.add(obj.getCode());
            }

            List<Gateway> devices = queryDeviceService.getGatewayByCodes(codes);
            List<String> devicecodes = new ArrayList<String>();
            for(Gateway obj : devices){
                devicecodes.add(obj.getMac());
            }

            MultipartFile file = request.getFile("file");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("codes", codes);
            data.put("buildingcodes",buildingcodes);
            data.put("devicecodes",devicecodes);
            data.put("user_id", userid);
            data.put("batchid", String.valueOf(System.currentTimeMillis()));
            gatewayInfoDetail.process(file,data);

            Example example = new Example(ImportException.class);
            example.createCriteria().andEqualTo("user_id", userid).andEqualTo("batchid", data.get("batchid")).andEqualTo("import_type", CodeUtil.IMPORT_DATA_TYPE_GATEWAY);
            int exceptions = importExceptionService.countImportException(example);//出现异常
            if(exceptions>0){
                resultObj.put("errcode", ErrCode.IMPORT_ERR);
                resultObj.put("msg", "导入异常，具体异常信息参考异常明细！");
            }else{
                resultObj.put("errcode", ErrCode.success);
                resultObj.put("result", "导入成功!");
            }
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject result = new JSONObject();
            result.put("errcode", ErrCode.routineErr);
            result.put("msg", e.toString());
            return result.toJSONString();
        }
    }

    @RequestMapping(value = "/exportGateway", produces="text/plain;charset=UTF-8")
    public String exportGateway(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="token") String token, @RequestBody String requestBody){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            //数据校验
            if(!JsonParamValidator.gatewayQueryVal(parameter,resultObj)){
                resultObj.put("errcode", ErrCode.parameErr);
                return resultObj.toJSONString();
            }

            //根据token 获取用户id，之后获取用户权限列表，再判断是否有此功能权限，若无则直接返回errocode，有则继续
            Integer userid = dataPermService.getUserid(token,resultObj);
            if(userid == -1){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            List<Gateway> result = queryDeviceService.queryGateway(parameter);
            for(int i = 0;i < result.size();i++){
                result.get(i).setIsdel(null);
                result.get(i).setCreated_at(null);
                result.get(i).setListRows(null);
                result.get(i).setPage(null);
                result.get(i).setOnline(null);
                result.get(i).setErr_code(null);
            }

            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("基站数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.GATEWAY_TITLE,wb);//添加标题行

            gatewayOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("基站数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void gatewayOut(Sheet sheet, List<Gateway> list,CellStyle style) {
        // 表格内容{"楼栋唯一编号","设备类型","通信地址","名称","在线状态","故障","mbus地址","管径","备注","位置"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            Gateway obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getName());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getMac());

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getOnline() == 0?"离线":"在线");

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(DeviceError.getErrStr(obj.getErr_code()));

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(obj.getGprsid());

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(obj.getSim_code());

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getSim_code());

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style);
            String debugStr = null;
            if(obj.getDebug_status() == 0) {debugStr = "未插卡";}else if(obj.getDebug_status() == 1){debugStr = "已插卡";}else if(obj.getDebug_status() == 2){debugStr = "已调试";}
            cell7.setCellValue(debugStr);

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style);
            cell8.setCellValue(obj.getRemark());

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style);
            cell9.setCellValue(obj.getVersion());

            Cell cell10 = row.createCell(10);
            cell10.setCellStyle(style);
            cell10.setCellValue(obj.getAddress());
        }
    }

    @RequestMapping(value = "/exportHouseValve", produces="text/plain;charset=UTF-8")
    public String exportHouseValve(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="token") String token, @RequestBody String requestBody){
        Workbook wb = null;
        OutputStream out = null;
        try{
            JSONObject resultObj = new JSONObject();
            JSONObject parameter = JSON.parseObject(requestBody);JsonUtil.filterNull(parameter);
            List<String> codes = new ArrayList<String>();
            //数据校验
            if(!JsonParamValidator.houseHolderValveQueryVal(parameter,resultObj)){
                return resultObj.toJSONString();
            }

            //用户校验及权限验证
            Integer page = JsonParamValidator.str2Digits(parameter.getString("page"));
            Integer listRows = JsonParamValidator.str2Digits(parameter.getString("listRows"));
            int isLiberty  = dataPermService.userPerm(codes,token,parameter,resultObj);

            if(isLiberty < 0){
                return resultObj.toJSONString();
            }

            //数据查询，成功后返回.
            Integer error = null;
            String errorstr = parameter.getString("error");
            if(errorstr != null){
                error = Integer.parseInt(errorstr);
            }

            Integer opening = null;
            String openingstr = parameter.getString("opening");
            if(openingstr != null){
                opening = Integer.parseInt(openingstr);
            }

            Integer mes_status = null;
            String mes_statusstr = parameter.getString("mes_status");
            if(mes_statusstr != null){
                mes_status = Integer.parseInt(mes_statusstr);
            }

            String id_card = parameter.getString("id_card");
            String err_code = parameter.getString("err_code");

            List<HouseHolderValveParam> result = queryDeviceService.queryHouseHolderValve(codes,  error,  opening,  mes_status,  id_card,err_code,  page,  listRows);


            wb = new XSSFWorkbook(); // --->创建了一个excel文件
            Sheet sheet = wb.createSheet("阀门数据"); //创建了一个工作簿

            sheet.setDefaultRowHeight((short) 300); // 设置统一单元格的高度
            // 设置字体格式
            CellStyle style = wb.createCellStyle(); // 样式对象
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setBorderTop((short) BorderStyle.THIN.ordinal());
            style.setBorderBottom((short) BorderStyle.THIN.ordinal());
            style.setBorderLeft((short) BorderStyle.THIN.ordinal());
            style.setBorderRight((short) BorderStyle.THIN.ordinal());

            addSheetTitle(sheet,ExportTitle.HOUSE_VALVE_CTRL_TITLE,wb);//添加标题行

            houseValveCtrlOut(sheet,result,style);//填充表格数据

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msexcel");
            String fileName = java.net.URLEncoder.encode("阀门数据", "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "GBK") + ".xlsx");
            out = response.getOutputStream();
            wb.write(out);
            out.flush();
            resultObj.put("errcode", ErrCode.success);
            return resultObj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject resultObj = new JSONObject();
            resultObj.put("errcode", ErrCode.routineErr);
            resultObj.put("msg",e.getMessage());
            return resultObj.toJSONString();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (wb != null) {
                    wb.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void houseValveCtrlOut(Sheet sheet, List<HouseHolderValveParam> list,CellStyle style) {
        // 表格内容{"楼栋唯一编号","设备类型","通信地址","名称","在线状态","故障","mbus地址","管径","备注","位置"};
        for (int i = 0; i < list.size(); i++) {

            Row row = sheet.createRow(i + 1);
            row.setHeightInPoints(18);
            HouseHolderValveParam obj = list.get(i);
            Cell cell0 = row.createCell(0);
            cell0.setCellStyle(style);
            cell0.setCellValue(obj.getCode());

            Cell cell1 = row.createCell(1);
            cell1.setCellStyle(style);
            cell1.setCellValue(obj.getType()==0?"Lora":"有线");

            Cell cell2 = row.createCell(2);
            cell2.setCellStyle(style);
            cell2.setCellValue(obj.getName());

            Cell cell3 = row.createCell(3);
            cell3.setCellStyle(style);
            cell3.setCellValue(obj.getComm_address());

            Cell cell4 = row.createCell(4);
            cell4.setCellStyle(style);
            cell4.setCellValue(obj.getOpen() > 0?"开":"关");

            Cell cell5 = row.createCell(5);
            cell5.setCellStyle(style);
            cell5.setCellValue(obj.getOpening() > 0?"开":"关");

            Cell cell6 = row.createCell(6);
            cell6.setCellStyle(style);
            cell6.setCellValue(obj.getMes_status() == 0?"未缴费":"已缴费");

            Cell cell7 = row.createCell(7);
            cell7.setCellStyle(style);
            cell7.setCellValue(obj.getId_card());

            Cell cell8 = row.createCell(8);
            cell8.setCellStyle(style);
            cell8.setCellValue(DeviceError.getErrStr(obj.getErr_code()));

            Cell cell9 = row.createCell(9);
            cell9.setCellStyle(style);
            cell9.setCellValue(obj.getOnline()==0?"离线":"在线");
        }
    }

    private void addSheetTitle(Sheet sheet,String[] titles,Workbook wb) {
        CellStyle style = wb.createCellStyle(); // 样式对象
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        style.setFillForegroundColor(HSSFColor.YELLOW.index);    //填充的背景颜色
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderTop((short) BorderStyle.THIN.ordinal());
        style.setBorderBottom((short) BorderStyle.THIN.ordinal());
        style.setBorderLeft((short) BorderStyle.THIN.ordinal());
        style.setBorderRight((short) BorderStyle.THIN.ordinal());

        Row row = sheet.createRow(0);
        row.setHeightInPoints(25);

        for(int i=0;i<titles.length;i++){
            Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(titles[i]);

            sheet.setColumnWidth(i, 5000);//设置列宽
        }
    }
}
