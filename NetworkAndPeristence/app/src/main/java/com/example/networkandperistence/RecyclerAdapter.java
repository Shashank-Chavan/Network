package com.example.networkandperistence;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter {
    List<Movie> movieList;
    public RecyclerAdapter(List<Movie> movieList){
        this.movieList = movieList;
    }
    void updateQuizList(List<Movie> movieList) {
        this.movieList.clear();
        this.movieList= movieList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.card,parent,false);

        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        RecyclerHolder recyclerHolder = (RecyclerHolder)holder;
        recyclerHolder.title.setText(movie.getTitle());
        recyclerHolder.description.setText(movie.getDescription());
        class ImageAsync extends AsyncTask<String,String,Bitmap>{

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    return BitmapFactory.decodeStream(is, null, options);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                if(bitmap!=null){
                    ((RecyclerHolder) holder).image.setImageBitmap(bitmap);
                }
            }
        }
        new ImageAsync().execute(movie.getImageLink());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
    public class RecyclerHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView image;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.Title);
            description = itemView.findViewById(R.id.Description);
            image = itemView.findViewById(R.id.Title_Image);
        }
    }
}
