package photo_multipart.photo_multipart.data;

public class OutputData {
    private String menu;
    private int count;

    public OutputData(String menu, int count) {
        this.menu = menu;
        this.count = count;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
