package com.example.deremate.data.network;
import com.example.deremate.data.model.Post;
import com.example.deremate.data.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface ApiService {
    @GET("user")
    Call<User> getUser();

    @GET("posts")
    Call<List<Post>> getPosts();


}
