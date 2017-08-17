package com.dazk.db.param;

import com.dazk.common.util.ClassCopyer;
import com.dazk.db.model.Concentrator;
import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/8/15.
 */
public class ConcentratorDetailParam extends Concentrator {
    private String community_name;
    private String building_name;

    public ConcentratorDetailParam(Concentrator parent,String community_name,String building_name) {
        ClassCopyer.fatherToChild(parent,this);
        this.community_name = community_name;
        this.building_name = building_name;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }
}
