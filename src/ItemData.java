public class ItemData {
    private String name;
    private String description;
    private String image3D; // 画像のパス
    private boolean isHolding = false; // 持っているかどうか

    public ItemData(String name, String description, String image3D) {
        this.name = name;
        this.description = description;
        this.image3D = image3D;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage3D() {
        return image3D;
    }

    public boolean getIsHolding() {
        return isHolding;
    }

    public void setIsHolding(boolean holding) {
        isHolding = holding;
    }
}
