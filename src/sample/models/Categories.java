package sample.models;

import javafx.scene.image.Image;

public class Categories {
    private String name;
    private Image icon;

    public Categories(String name, Image icon) {
        this.name = name;
        this.icon = icon;
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
}
