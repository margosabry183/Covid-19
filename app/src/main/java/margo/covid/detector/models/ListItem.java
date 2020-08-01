package margo.covid.detector.models;

import androidx.annotation.DrawableRes;

public class ListItem {

    private String name;

    @DrawableRes
    private int image;

    private String mobile;

    private String address;

    public ListItem(String name, int image, String mobile, String address) {
        this.name = name;
        this.image = image;
        this.mobile = mobile;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }
}
