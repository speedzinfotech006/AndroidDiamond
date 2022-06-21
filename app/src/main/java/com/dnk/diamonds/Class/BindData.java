package com.dnk.shairugems.Class;

public class BindData {
    private String Key;
    private String Value;
    private String Type;
    private String ImageUrl;

    public BindData(String id, String name) {
        this.Key = id;
        this.Value = name;
    }

    public BindData(String id, String name, String type, String image_url) {
        this.Key = id;
        this.Value = name;
        this.Type = type;
        this.ImageUrl = image_url;
    }

    public String getId() {
        return Key;
    }

    public void setId(String id) {
        this.Key = id;
    }

    public String getName() {
        return Value;
    }

    public void setName(String name) {
        this.Value = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String id) {
        this.Type = id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }


    @Override
    public String toString() {
        return Value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BindData) {
            BindData c = (BindData) obj;
            if (c.getName().equals(Value) && c.getId() == Key) return true;
        }

        return false;
    }
}
