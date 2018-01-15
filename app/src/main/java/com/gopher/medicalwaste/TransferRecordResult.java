package com.gopher.medicalwaste;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/13.
 */

public class TransferRecordResult implements Serializable {

    /**
     * dataTime : 1484285156000
     * handoverdep : 暂存间
     * handoverman : 42010100001000051370
     * handovermanname : 崔冰
     * id : 1324
     * operatordep : 手术室
     * operatorman : 42010100001000012340
     * operatormanname : 许丽琴
     * staName : 6号手持机
     * status : 1
     * typeId : 44
     * typename : 感染性废物I(塑料类)
     * weight : 8.75
     */
    /**
     * {"dataTime":1484285156000,"handoverdep":"暂存间","handoverman":"42010100001000051370","handovermanname":"天霖暂存间","id":1324,"operatordep":"手术室","operatorman":"42010100001000012340","operatormanname":"许丽琴","status":"1","typeId":44,"typename":"感染性废物I(塑料类)","weight":"8.75"}
     */

    private long dataTime;
    private String handoverdep;
    private String handoverman;
    private String handovermanname;
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TransferRecordResult{" +
                "dataTime=" + dataTime +
                ", handoverdep='" + handoverdep + '\'' +
                ", handoverman='" + handoverman + '\'' +
                ", handovermanname='" + handovermanname + '\'' +
                ", id=" + id +
                ", operatordep='" + operatordep + '\'' +
                ", operatorman='" + operatorman + '\'' +
                ", operatormanname='" + operatormanname + '\'' +
                ", staName='" + staName + '\'' +
                ", status='" + status + '\'' +
                ", typeId=" + typeId +
                ", typename='" + typename + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
