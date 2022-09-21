package com.example.networkandperistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recommended#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recommended extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Recommended() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recommended.
     */
    // TODO: Rename and change types and number of parameters
    public static Recommended newInstance(String param1, String param2) {
        Recommended fragment = new Recommended();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private RecyclerAdapter recyclerAdapter;
    Button button;
    Button Logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recommended, container, false);
        button = view.findViewById(R.id.list1_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.Container,new Recommendation()).addToBackStack(null).commit();
            }
        });
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie("Avengers","Superhero movie","gab"));
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerAdapter = new RecyclerAdapter(movies);
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        new MovieAsync().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fanimation%2Ffeed");
        new MovieAsync().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fhollywood%2Ffeed");
        Logout = view.findViewById(R.id.Logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences s = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = s.edit();
                editor.putBoolean("logged in",false);
                editor.apply();
                getFragmentManager().beginTransaction().replace(R.id.Container,new LoginPage()).commit();
            }
        });
        return view;
    }
    private class MovieAsync extends AsyncTask<String,String,List<Movie>>{

        @Override
        protected List<Movie> doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            List<Movie>movies = new ArrayList<>();
            try {
                URL url = new URL(strings[0]);
                urlConnection  = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                BufferedReader br =new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while((line=br.readLine())!=null)
                {
                    json.append(line);
                }
                JSONObject jsonObject = new JSONObject(json.toString());
                JSONArray jsonArray =jsonObject.getJSONArray("items");
                int count = 0;
                String t,o,i;
                while (count<jsonArray.length()){
                    JSONObject QuizObject = jsonArray.getJSONObject(count);
                    t = QuizObject.getString("title").replaceAll("<.*?>","");;
                    o = QuizObject.getString("categories").replaceAll("<.*?>","");;
                    i = QuizObject.getString("thumbnail");
                    Movie movie1 = new Movie(t,o,i);
                    movies.add(movie1);
                    count++;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);
            if(movieList!=null){
                recyclerAdapter.updateQuizList(movieList);
            }
        }
    }
}