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
    private Integer businessId;

    private String businessName;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

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

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}
