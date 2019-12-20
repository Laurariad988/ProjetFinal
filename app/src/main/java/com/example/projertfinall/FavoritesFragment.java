package com.example.projertfinall;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projertfinall.model.Pokemon;
import com.example.projertfinall.model.PokemonAdapter;
import com.example.projertfinall.model.PokemonRestApi;
import com.example.projertfinall.model.RestPokemonResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;




import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesFragment extends Fragment {

    View PView;

    private RecyclerView recyclerView;
    public List<RestPokemonResponse> RestPokemonResponse;
    private RecyclerView.LayoutManager layoutManager;
    private PokemonAdapter adapter;
    private RecyclerView.Adapter pAdapter;
    private SharedPreferences sharedPreferences;


    public FavoritesFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        PView=inflater.inflate(R.layout.fragment_favorites,container, false);


            super.onCreate(savedInstanceState);

            recyclerView = PView.findViewById(R.id.my_recycler_view);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PokemonRestApi pokemonRestApi = retrofit.create(PokemonRestApi.class);

        Call<RestPokemonResponse> call = pokemonRestApi.getListPokemon();
        call.enqueue(new Callback<RestPokemonResponse>() {
            @Override
            public void onResponse(Call<RestPokemonResponse> call, Response<RestPokemonResponse> response) {
                RestPokemonResponse restPokemonResponse = response.body();
                Gson gson = new Gson();
                List<Pokemon> listPokemon = restPokemonResponse.getResults();
            }

            @Override
            public void onFailure(Call<RestPokemonResponse> call, Throwable t) {
                Log.d("Erreur", "API ERROR");

            }
        });


     return PView;

    }



        public void showList(List<RestPokemonResponse> list) {



        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(pAdapter);
    }
}








