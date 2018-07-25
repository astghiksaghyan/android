package com.example.astghik.moviereview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fillList();
        final RecyclerView recyclerView = findViewById(R.id.recView);
        recyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final MyAdapter adapter = new MyAdapter(movies);
        recyclerView.setAdapter(adapter);
    }

    public void fillList() {
        Movie movie1 = new Movie("Titanic", R.drawable.titanic,
                "Description of Titanic", 2, false,
                "https://en.wikipedia.org/wiki/Titanic_(1997_film)");
        this.movies.add(movie1);
        Movie movie2 = new Movie("Avatar", R.drawable.avatar,
                "Description of Avatar", 3, false,
                "https://en.wikipedia.org/wiki/Titanic_(1997_film)");
        this.movies.add(movie2);
        Movie movie3 = new Movie("Superman", R.drawable.superman,
                "Description of Superman", 4, true,
                "https://en.wikipedia.org/wiki/Titanic_(1997_film)");
        this.movies.add(movie3);
        Movie movie4 = new Movie("Titanic", R.drawable.titanic,
                "Description of Titanic", 3, false,
                "https://en.wikipedia.org/wiki/Titanic_(1997_film)");
        this.movies.add(movie4);
        Movie movie5 = new Movie("Avatar", R.drawable.avatar,
                "Description of Avatar", 5, true,
                "https://en.wikipedia.org/wiki/Titanic_(1997_film)");
        this.movies.add(movie5);
        Movie movie6 = new Movie("Superman", R.drawable.superman,
                "Description of Superman", 1, false,
                "https://en.wikipedia.org/wiki/Titanic_(1997_film)");
        this.movies.add(movie6);

    }
}
