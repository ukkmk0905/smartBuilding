package com.team.smart.vo;

import com.google.gson.annotations.SerializedName;

public class FoodVO extends BaseRespon {
    @SerializedName("f_name")
    private String f_name;//메뉴명
    @SerializedName("f_ImageUrl")
    private String f_ImageUrl;//이미지
    @SerializedName("f_price")
    private String f_price;//가격
    @SerializedName("f_icon")
    private String icon; //아이콘
    @SerializedName("star")
    private String star;//별점
    @SerializedName("comp_org")
    private String comp_org;//업장 명
    @SerializedName("comp_coment")
    private String comp_coment;//업장 한줄 소개
    @SerializedName("reviewCnt")
    private String reviewCnt;//리뷰 수

    public String getStar() {
        return star;
    }

    public String getF_ImageUrl() { return f_ImageUrl; }

    public String getComp_org() { return comp_org; }

    public String getF_name() { return f_name; }

    public String getF_price() { return f_price; }

    public String getIcon() {
        return icon;
    }

    public String getComp_coment() {
        return comp_coment;
    }

    public String getReviewCnt() {
        return reviewCnt;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public void setF_ImageUrl(String f_ImageUrl) {
        this.f_ImageUrl = f_ImageUrl;
    }

    public void setComp_org(String comp_org) {
        this.comp_org = comp_org;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public void setF_price(String f_price) {
        this.f_price = f_price;
    }

    public void setComp_coment(String comp_coment) { this.comp_coment = comp_coment;}

    public void setReviewCnt(String reviewCnt) {this.reviewCnt = reviewCnt;}
}
