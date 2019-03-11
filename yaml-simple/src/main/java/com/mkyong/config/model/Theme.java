package com.mkyong.config.model;

public class Theme {

    private String defaultFolder;

    public String getDefaultFolder() {
        return defaultFolder;
    }

    public void setDefaultFolder(String defaultFolder) {
        this.defaultFolder = defaultFolder;
    }

    @Override
    public String toString() {
        return "Theme{" +
                "defaultFolder='" + defaultFolder + '\'' +
                '}';
    }
}
