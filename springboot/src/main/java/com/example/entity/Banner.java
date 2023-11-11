package com.example.entity;

/**
 * @Classname Banner
 * @Description TODO
 * @Author zjj
 * @Date 11/11/23 4:50PM
 */
public class Banner {
    /** ID */
    private Integer id;
    /** image */
    private String img;
    /** businessID */
    private Integer businessId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}
