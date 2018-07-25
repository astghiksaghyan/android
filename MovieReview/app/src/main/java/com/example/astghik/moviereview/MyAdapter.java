package com.example.astghik.moviereview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Movie> myMovies;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewTitle;
        public ImageView imageView;
        public TextView textViewDesc;
        public RatingBar ratingBar;
        public ImageButton imageButton;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.image);
            textViewDesc = itemView.findViewById(R.id.description);
            ratingBar = itemView.findViewById(R.id.rating);
            imageButton = itemView.findViewById(R.id.heart);
        }
   }

    public MyAdapter(List<Movie> movies) {
        myMovies= movies;
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Movie movie = myMovies.get(position);
        holder.textViewTitle.setText(movie.getTitle());
        holder.imageView.setImageResource(movie.getImageId());
        holder.textViewDesc.setText(movie.getDescription());
        holder.ratingBar.setRating(movie.getRating());
        if(movie.isHeart()) {
            holder.imageButton.setImageResource(R.drawable.ic_favorite);
        } else {
            holder.imageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return myMovies.size();
    }
}
