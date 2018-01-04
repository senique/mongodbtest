package com.mng.domain;

import com.mng.utils.sequence.GeneratedValue;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.util.Date;

public class DataItem {
//    @Id
//    @GeneratedValue
    private Long count;
    private Date createdTime;

    @Id
    @GeneratedValue
    private Long id;

    //    @Id
    private String name;

    //    @Id
    private BigInteger bId;

    //    @Id
    private ObjectId objId;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getbId() {
        return bId;
    }

    public void setbId(BigInteger bId) {
        this.bId = bId;
    }

    public ObjectId getObjId() {
        return objId;
    }

    public void setObjId(ObjectId objId) {
        this.objId = objId;
    }
}
