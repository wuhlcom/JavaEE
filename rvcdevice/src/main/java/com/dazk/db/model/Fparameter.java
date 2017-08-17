package com.dazk.db.model;

import javax.persistence.Table;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7.
 */
@Table(name = "fparameter")
public class Fparameter extends BaseEntity{
    private Integer building_valve_id;
    private Integer opening1;
    private Integer opening2;
    private Integer opening3;
    private Integer opening4;
    private Integer opening5;
    private Integer opening6;
    private Integer opening7;
    private Integer opening8;
    private Integer opening9;
    private Integer opening10;
    private Integer opening11;
    private Integer opening12;
    private Integer opening13;
    private Integer opening14;
    private Integer opening15;
    private Integer opening16;
    private Integer opening17;
    private Integer opening18;
    private Integer opening19;
    private Integer opening20;
    private Integer opening21;
    private Integer opening22;
    private Integer opening23;
    private Integer opening24;
    private Long created_at;
    private Integer isdel;

    public void initOpen(Map<String,String> map){
        opening1 = Integer.parseInt(map.get("opening1"));
        opening2 = Integer.parseInt(map.get("opening2"));
        opening3 = Integer.parseInt(map.get("opening3"));
        opening4 = Integer.parseInt(map.get("opening4"));
        opening5 = Integer.parseInt(map.get("opening5"));
        opening6 = Integer.parseInt(map.get("opening6"));
        opening7 = Integer.parseInt(map.get("opening7"));
        opening8 = Integer.parseInt(map.get("opening8"));
        opening9 = Integer.parseInt(map.get("opening9"));
        opening10 = Integer.parseInt(map.get("opening10"));
        opening11 = Integer.parseInt(map.get("opening11"));
        opening12 = Integer.parseInt(map.get("opening12"));
        opening13 = Integer.parseInt(map.get("opening13"));
        opening14 = Integer.parseInt(map.get("opening14"));
        opening15 = Integer.parseInt(map.get("opening15"));
        opening16 = Integer.parseInt(map.get("opening16"));
        opening17 = Integer.parseInt(map.get("opening17"));
        opening18 = Integer.parseInt(map.get("opening18"));
        opening19 = Integer.parseInt(map.get("opening19"));
        opening20 = Integer.parseInt(map.get("opening20"));
        opening21 = Integer.parseInt(map.get("opening21"));
        opening22 = Integer.parseInt(map.get("opening22"));
        opening23 = Integer.parseInt(map.get("opening23"));
        opening24 = Integer.parseInt(map.get("opening24"));
    }

    public Integer getBuilding_valve_id() {
        return building_valve_id;
    }

    public void setBuilding_valve_id(Integer building_valve_id) {
        this.building_valve_id = building_valve_id;
    }

    public Integer getOpening1() {
        return opening1;
    }

    public void setOpening1(Integer opening1) {
        this.opening1 = opening1;
    }

    public Integer getOpening2() {
        return opening2;
    }

    public void setOpening2(Integer opening2) {
        this.opening2 = opening2;
    }

    public Integer getOpening3() {
        return opening3;
    }

    public void setOpening3(Integer opening3) {
        this.opening3 = opening3;
    }

    public Integer getOpening4() {
        return opening4;
    }

    public void setOpening4(Integer opening4) {
        this.opening4 = opening4;
    }

    public Integer getOpening5() {
        return opening5;
    }

    public void setOpening5(Integer opening5) {
        this.opening5 = opening5;
    }

    public Integer getOpening6() {
        return opening6;
    }

    public void setOpening6(Integer opening6) {
        this.opening6 = opening6;
    }

    public Integer getOpening7() {
        return opening7;
    }

    public void setOpening7(Integer opening7) {
        this.opening7 = opening7;
    }

    public Integer getOpening8() {
        return opening8;
    }

    public void setOpening8(Integer opening8) {
        this.opening8 = opening8;
    }

    public Integer getOpening9() {
        return opening9;
    }

    public void setOpening9(Integer opening9) {
        this.opening9 = opening9;
    }

    public Integer getOpening10() {
        return opening10;
    }

    public void setOpening10(Integer opening10) {
        this.opening10 = opening10;
    }

    public Integer getOpening11() {
        return opening11;
    }

    public void setOpening11(Integer opening11) {
        this.opening11 = opening11;
    }

    public Integer getOpening12() {
        return opening12;
    }

    public void setOpening12(Integer opening12) {
        this.opening12 = opening12;
    }

    public Integer getOpening13() {
        return opening13;
    }

    public void setOpening13(Integer opening13) {
        this.opening13 = opening13;
    }

    public Integer getOpening14() {
        return opening14;
    }

    public void setOpening14(Integer opening14) {
        this.opening14 = opening14;
    }

    public Integer getOpening15() {
        return opening15;
    }

    public void setOpening15(Integer opening15) {
        this.opening15 = opening15;
    }

    public Integer getOpening16() {
        return opening16;
    }

    public void setOpening16(Integer opening16) {
        this.opening16 = opening16;
    }

    public Integer getOpening17() {
        return opening17;
    }

    public void setOpening17(Integer opening17) {
        this.opening17 = opening17;
    }

    public Integer getOpening18() {
        return opening18;
    }

    public void setOpening18(Integer opening18) {
        this.opening18 = opening18;
    }

    public Integer getOpening19() {
        return opening19;
    }

    public void setOpening19(Integer opening19) {
        this.opening19 = opening19;
    }

    public Integer getOpening20() {
        return opening20;
    }

    public void setOpening20(Integer opening20) {
        this.opening20 = opening20;
    }

    public Integer getOpening21() {
        return opening21;
    }

    public void setOpening21(Integer opening21) {
        this.opening21 = opening21;
    }

    public Integer getOpening22() {
        return opening22;
    }

    public void setOpening22(Integer opening22) {
        this.opening22 = opening22;
    }

    public Integer getOpening23() {
        return opening23;
    }

    public void setOpening23(Integer opening23) {
        this.opening23 = opening23;
    }

    public Integer getOpening24() {
        return opening24;
    }

    public void setOpening24(Integer opening24) {
        this.opening24 = opening24;
    }

    public Long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Long created_at) {
        this.created_at = created_at;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}
