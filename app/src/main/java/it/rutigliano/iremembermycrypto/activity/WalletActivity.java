package it.rutigliano.iremembermycrypto.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import it.rutigliano.iremembermycrypto.ApiConfig;
import it.rutigliano.iremembermycrypto.CryptoWalletAdapter;
import it.rutigliano.iremembermycrypto.R;
import it.rutigliano.iremembermycrypto.model.Crypto;

public class WalletActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Crypto> cryptos;
    private FloatingActionButton addWalletCryptoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        recyclerView = findViewById(R.id.list_crypto_wallet);
        addWalletCryptoButton = findViewById(R.id.button_crypto_wallet_add);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiConfig.API_URL, null, response -> {
            cryptos = ApiConfig.GSON.fromJson(response.toString(), new TypeToken<List<Crypto>>() {}.getType());
            setOwnedCryptos();
        }, error -> Log.e("CRYPTO", "Error " + error.getMessage()));

        Volley.newRequestQueue(this).add(request);

        addWalletCryptoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    public void setOwnedCryptos(){
        ArrayList<Crypto> ownedCryptos = new ArrayList<>();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.wallet_preferences), Context.MODE_PRIVATE);
        for(Crypto c : cryptos) {
            String ownedValue = sharedPref.getString(c.getSymbol(), null);
            if (ownedValue != null) {
                c.setOwnedValue(Double.parseDouble(ownedValue));
                ownedCryptos.add(c);
            }
        }
        CryptoWalletAdapter cryptoAdapter = new CryptoWalletAdapter(ownedCryptos);
        recyclerView.setAdapter(cryptoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(WalletActivity.this));
    }
}