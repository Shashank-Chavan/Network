package com.example.networkandperistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


    public class RecyclerAdapter2 extends RecyclerView.Adapter {
        List<Movie> movieList;
        String msg;
        public RecyclerAdapter2(List<Movie> movieList){
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
            View view =layoutInflater.inflate(R.layout.card2,parent,false);

            return new RecyclerHolder2(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final Movie movie = movieList.get(position);
            RecyclerHolder2 recyclerHolder2 = (RecyclerHolder2)holder;
            recyclerHolder2.title.setText(movie.getTitle());
            recyclerHolder2.description.setText(movie.getDescription());
            recyclerHolder2.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(recyclerHolder2.itemView.getContext(),"Liked this catrgory",Toast.LENGTH_SHORT).show();
                    msg = movie.getDescription();
                    /*AppCompatActivity activity = (AppCompatActivity)view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.Container,new LoginPage()).addToBackStack(null).commit();*/
                }
            });
            class ImageAsync extends AsyncTask<String,String, Bitmap> {
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
                        ((RecyclerHolder2) holder).image.setImageBitmap(bitmap);
                    }
                }
            }
            new ImageAsync().execute(movie.getImageLink());
        }

        @Override
        public int getItemCount() {
            return movieList.size();
        }
        public class RecyclerHolder2 extends RecyclerView.ViewHolder{
            TextView title;
            TextView description;
            ImageView image;
            Button like;
            public RecyclerHolder2(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.Title);
                description = itemView.findViewById(R.id.Description);
                image = itemView.findViewById(R.id.Title_Image);
                like = itemView.findViewById(R.id.Liked);
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences shrd = itemView.getContext().getSharedPreferences("recon", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shrd.edit();
                        Movie movie = new Movie();
                        editor.putString("key",msg);
                    }
                });
            }


        }
    }


