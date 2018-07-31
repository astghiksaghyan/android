package com.example.astghik.searchexample;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    public static List<User> users = new ArrayList<>();


    public static void fillUsers() {
        String url_Gevorg = "https://3.bp.blogspot.com/-9Iu3-Xnvtig/Wntv1F6CKII/AAAAAAAAAR0/CvCLy-jFSUIfM9vjj6UdjZigK7LKBnSPgCLcBGAs/s1600/2.jpg";
        String url_Astghik = "https://as.ftcdn.net/r/v1/pics/ea2e0032c156b2d3b52fa9a05fe30dedcb0c47e3/landing/images_photos.jpg";
        String template = "\tLorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown" +
                " printer took a galley of type and scrambled it to make a type specimen book. It has survived " +
                "not only five centuries, but also the leap into electronic typesetting, remaining essentially " +
                "unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem " +
                "Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including" +
                " versions of Lorem Ipsum.\n";

        users.add(new User(url_Astghik, "Astghik", template, "astghik@gmail.com", "069770500"));
        users.add(new User(url_Gevorg, "Gevorg", template, "gevorg94@gmail.com", "069900500"));
        users.add(new User(url_Gevorg, "Gevorg", template, "gevorg94@gmail.com", "069900500"));
        users.add(new User(url_Astghik, "Gevorg", template, "gevorg94@gmail.com", "069900500"));
        users.add(new User(url_Gevorg, "Gevorg", template, "gevorg94@gmail.com", "069900500"));
        users.add(new User(url_Gevorg, "Gevorg", template, "gevorg94@gmail.com", "069900500"));
    }
}
