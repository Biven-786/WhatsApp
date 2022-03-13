package com.example.whatsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Gson will not drop null values
        Gson gson= new GsonBuilder().serializeNulls().create();

        text = findViewById(R.id.textview);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPost();
        //getComment();
        //createPost();
        //updatetPost();
        deletePost();

    }

    private void deletePost() {
        Call<Void> call=jsonPlaceHolderApi.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                text.setText("Code: "+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void updatetPost() {
        Post post =new Post(12,null,"New Text");

        Call<Post> call=jsonPlaceHolderApi.patchPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                } else {
                    Post postResponse = response.body();
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "User ID: " + postResponse.getUserID() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getText() + "\n\n";

                    text.append(content);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        Post post = new Post(23, "New Title", "New Text");

        //for FormUrlEncoded
        //post will be replaced by(23,"New Title","Text");

        //FieldMap
        /*Map<String, String> fields = new HashMap<>();
        fields.put("userId", "1");
        fields.put("_sort", "id");
        fields.put("_order", "desc");
        //Pass fields in createPost*/

        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                } else {
                    Post postResponse = response.body();
                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "ID: " + postResponse.getId() + "\n";
                    content += "User ID: " + postResponse.getUserID() + "\n";
                    content += "Title: " + postResponse.getTitle() + "\n";
                    content += "Text: " + postResponse.getText() + "\n\n";

                    text.append(content);
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }

    private void getComment() {

        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                } else {
                    List<Comment> comments = response.body();
                    for (Comment comment : comments) {
                        String content = "";
                        content += "ID: " + comment.getId() + "\n";
                        content += "Post ID: " + comment.getPostId() + "\n";
                        content += "Name: " + comment.getName() + "\n";
                        content += "Email: " + comment.getEmail() + "\n";
                        content += "Text: " + comment.getText() + "\n\n";

                        text.append(content);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

                text.setText(t.getMessage());

            }
        });
    }

    private void getPost() {
        //MAP
        Map<String, String> parameter = new HashMap<>();
        parameter.put("userId", "1");
        parameter.put("_sort", "id");
        parameter.put("_order", "desc");

        //new Integer[]{2,4,7},null, "desc"
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameter);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (!response.isSuccessful()) {
                    text.setText("Code: " + response.code());
                    return;
                } else {
                    List<Post> posts = response.body();
                    for (Post post : posts) {
                        String content = "";
                        content += "ID: " + post.getId() + "\n";
                        content += "User ID: " + post.getUserID() + "\n";
                        content += "Title: " + post.getTitle() + "\n";
                        content += "Text: " + post.getText() + "\n\n";

                        text.append(content);
                    }
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                text.setText(t.getMessage());
            }
        });
    }
}