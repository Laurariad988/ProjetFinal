package com.example.projertfinall.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonRestApi  {


    @GET("pokemon")
    Call<RestPokemonResponse> getListPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
