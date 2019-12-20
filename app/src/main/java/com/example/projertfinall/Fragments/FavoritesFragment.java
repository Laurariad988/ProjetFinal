package com.example.projertfinall.Fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projertfinall.R;
import com.example.projertfinall.model.Pokemon;
import com.example.projertfinall.model.PokemonAdapter;
import com.example.projertfinall.model.PokemonRestApi;
import com.example.projertfinall.model.RestPokemonResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoritesFragment extends Fragment {



    View PView;

    private static final String TAG="POKEDEX";
    private int offset;
    private boolean apc;
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private PokemonAdapter pokemonAdapter;
    public List<RestPokemonResponse> RestPokemonResponse;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter pAdapter;
    private SharedPreferences sharedPreferences;



    @Nullable
    @Override

    public View onCreateView( @Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        PView=inflater.inflate(R.layout.fragment_favorites,container, false);
            super.onCreate(savedInstanceState);
            recyclerView = PView.findViewById(R.id.my_recycler_view);


        recyclerView = (RecyclerView) PView.findViewById(R.id.recyclerView);
        pokemonAdapter = new PokemonAdapter(getContext());
        recyclerView.setAdapter(pokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private void onScrollStated(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy>0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if(apc) {
                        if((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG,"fin");

                            apc = false;
                            offset +=20;
                            obtenirDonnees(offset);
                        }
                    }


                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apc = true;
        offset=0;

        obtenirDonnees(offset);

        return PView;
    }

    private void obtenirDonnees(int offset) {
        PokemonRestApi pokemonRestApi = retrofit.create(PokemonRestApi.class);
        final Call<RestPokemonResponse>pokemonResponseCall=pokemonRestApi.getListPokemon(20,offset);

        pokemonResponseCall.enqueue(new Callback<com.example.projertfinall.model.RestPokemonResponse>() {
            @Override
            public void onResponse(Call<com.example.projertfinall.model.RestPokemonResponse> call, Response<com.example.projertfinall.model.RestPokemonResponse> response) {

                apc = true;
                if (response.isSuccessful()) {

                    RestPokemonResponse restPokemonResponse = response.body();
                    ArrayList<Pokemon> listPokemon = (ArrayList<Pokemon>) restPokemonResponse.getResults();

                    pokemonAdapter.aditionPokemonAdapter(listPokemon);




            } else {
                    Log.e(TAG,"onResponse: " + response.errorBody());

                }
            }

            @Override
            public void onFailure(Call<com.example.projertfinall.model.RestPokemonResponse> call, Throwable t) {
                apc = true;
                Log.e(TAG, "onFailure" + t.getMessage());

            }
        });

    }

    }





