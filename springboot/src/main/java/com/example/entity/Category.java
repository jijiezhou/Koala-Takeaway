package com.example.entity;

/**
 * @Classname Category of Business
 * @Description TODO
 * @Author zjj
 * @Date 11/8/23 8:33PM
 */
public class Category {
    /**
     *
     */
    private Integer id;
    /**
     * business name
     */
    private String name;
    /**
     * businessId
     */
    private String businessId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
