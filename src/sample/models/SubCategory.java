package sample.models;

import javafx.scene.image.Image;

public class SubCategory {
    private String name;
    private Image icon;
    private String parent;

    public SubCategory(String name, Image icon, String parent) {
        this.name = name;
        this.icon = icon;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
