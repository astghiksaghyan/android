package instigatemobile.com.cookmaster.models;

public class TeamMember {
    private int image;
    private int name;

    public TeamMember(int image, int name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}