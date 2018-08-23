package instigatemobile.com.cookmaster.models;

public class TodayMenuItem {
    private String mMealName;
    private String mMealImageURL;

    public TodayMenuItem(String mealName, String mealImageURL) {
        this.mMealName = mealName;
        this.mMealImageURL = mealImageURL;
    }

    public TodayMenuItem() {
    }

    public String getmMealName() {
        return mMealName;
    }

    public String getmMealImageURL() {
        return mMealImageURL;
    }
}