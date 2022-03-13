package com.example.whatsapp;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

   /* @GET("posts")
    Call<List<Post>> getPosts(

            //Integer is for nullable
            *//*@Query("userId")  Integer userId,
            @Query("userId")  Integer userId2,*//*
            @Query("userId") Integer[] userId,
            @Query("_sort")  String sort,
            @Query("_order")  String order
    );*/

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postID);

    @GET("posts")
    Call<List<Post>> getPosts(@QueryMap Map<String,String> parameter);

    @GET
    Call<List<Comment>> getComments(@Url String url);

    //Send new data
    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //sending data to Server
    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text
    );
    @FormUrlEncoded
    @POST("posts")
    Call<List<Post>> createPosts(@FieldMap Map<String, String> fields);


    //Update the data on Server
    @PUT("posts/{id}")
    Call<Post> putPost(@Path("id") int id,@Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patchPost(@Path("id") int id,@Body Post post);

    //Delete Data    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id") int id);
}
