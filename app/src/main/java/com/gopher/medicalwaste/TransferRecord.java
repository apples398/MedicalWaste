package com.gopher.medicalwaste;

import java.io.Serializable;


public class TransferRecord implements Serializable {

    /**
     * dataTime : 1510815123000
     * handoverdep : 暂存间
     * handoverman : 42010300001000980098
     * handovermanname : 彭国胜
     * hosName : 武汉市中心医院后湖院区
     * id : 00001d7db3ef42b7871ad159fb56cef6
     * labelid : 44000000052647
     * operatorType :
     * operatordep : 产房
     * operatorman : 42010100002000812365
     * operatormanname : 潘慧敏
     * staName : 2号手推车
     * status : 1
     * typeId : 44
     * typename : 感染性废物I(塑料类)
     * weight : 13.13
     */

    private long dataTime;
    private String handoverdep;
    private String handoverman;
    private String handovermanname;
    private String hosName;
    private String id;
    private String labelid;
    private String operatorType;
    private String operatordep;
    private String operatorman;
    private String operatormanname;
    private String staName;
    private String status;
    private int typeId;
    private String typename;
    private String weight;

    public long getDataTime() {
        return dataTime;
    }

    public void setDataTime(long dataTime) {
        this.dataTime = dataTime;
    }

    public String getHandoverdep() {
        return handoverdep;
    }

    public void setHandoverdep(String handoverdep) {
        this.handoverdep = handoverdep;
    }

    public String getHandoverman() {
        return handoverman;
    }

    public void setHandoverman(String handoverman) {
        this.handoverman = handoverman;
    }

    public String getHandovermanname() {
        return handovermanname;
    }

    public void setHandovermanname(String handovermanname) {
        this.handovermanname = handovermanname;
    }

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelid() {
        return labelid;
    }

    public void setLabelid(String labelid) {
        this.labelid = labelid;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getOperatordep() {
        return operatordep;
    }

    public void setOperatordep(String operatordep) {
        this.operatordep = operatordep;
    }

    public String getOperatorman() {
        return operatorman;
    }

    public void setOperatorman(String operatorman) {
        this.operatorman = operatorman;
    }

    public String getOperatormanname() {
        return operatormanname;
    }

    public void setOperatormanname(String operatormanname) {
        this.operatormanname = operatormanname;
    }

    public String getStaName() {
        return staName;
    }

    public void setStaName(String staName) {
        this.staName = staName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
