package it.rutigliano.iremembermycrypto.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import it.rutigliano.iremembermycrypto.ApiConfig;
import it.rutigliano.iremembermycrypto.CryptoAdapter;
import it.rutigliano.iremembermycrypto.OnItemClickListener;
import it.rutigliano.iremembermycrypto.R;
import it.rutigliano.iremembermycrypto.model.Crypto;


public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private CryptoAdapter cryptoAdapter;
    private ArrayList<Crypto> cryptos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        recyclerView = findViewById(R.id.cryptos);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiConfig.API_URL, null, response -> {
            cryptos = ApiConfig.GSON.fromJson(response.toString(), new TypeToken<List<Crypto>>() {}.getType());
            progressBar.setVisibility(View.INVISIBLE);

            cryptoAdapter = new CryptoAdapter(cryptos, new OnItemClickListener() {
                @Override
                public void onItemClick(Crypto crypto, int position) {
                    crypto.setFavorite(!crypto.isFavorite());
                    //Collections.swap(cryptos, position, 0);
                    //cryptoAdapter.notifyItemMoved(position, 0);
                    //cryptos.remove(position);
                    //cryptos.add(0, crypto);
                    /*Comparator<Crypto> compareByName = Comparator
                            .comparing(Crypto::getPrice)
                            .thenComparing(Crypto::getName);

                    cryptos = cryptos.stream()
                            .sorted(compareByName)
                            .collect(Collectors.toCollection(ArrayList::new));*/
                    //cryptoAdapter.notifyDataSetChanged();
                    //recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, new RecyclerView.State(),0);
                    Intent cryptoTradeIntent = new Intent(getApplicationContext(), CryptoTradeActivity.class);
                    cryptoTradeIntent.putExtra("crypto_trade_intention", crypto.getSymbol());
                    startActivity(cryptoTradeIntent);
                }
            });
            recyclerView.setAdapter(cryptoAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }, error -> Log.e("CRYPTO", "Error " + error.getMessage()));

        Volley.newRequestQueue(this).add(request);


    }


}