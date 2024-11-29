package com.ssafy.enjoytrip.trip.model;

import java.math.BigDecimal;

public class SearchResponseDto {

    private int contentId; // pk
    private int contentType;
    private String title;
    private String addr1;
    private String addr2;
    private String firstImage;
    private BigDecimal mapY;
    private BigDecimal mapX;

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public BigDecimal getMapY() {
        return mapY;
    }

    public void setMapY(BigDecimal mapY) {
        this.mapY = mapY;
    }

    public BigDecimal getMapX() {
        return mapX;
    }

    public void setMapX(BigDecimal mapX) {
        this.mapX = mapX;
    }

    @Override
    public String toString() {
        return "SearchResponseDto{" +
            "contentId=" + contentId +
            ", contentType=" + contentType +
            ", title='" + title + '\'' +
            ", addr1='" + addr1 + '\'' +
            ", addr2='" + addr2 + '\'' +
            ", firstImage='" + firstImage + '\'' +
            ", mapY=" + mapY +
            ", mapX=" + mapX +
            '}';
    }
}
