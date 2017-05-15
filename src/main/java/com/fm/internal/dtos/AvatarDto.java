package com.fm.internal.dtos;

public class AvatarDto {
    private long userId;
    private byte[] image;

    public AvatarDto() {
    }

    public AvatarDto(long userId, byte[] image) {
        this.userId = userId;
        this.image = image;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
