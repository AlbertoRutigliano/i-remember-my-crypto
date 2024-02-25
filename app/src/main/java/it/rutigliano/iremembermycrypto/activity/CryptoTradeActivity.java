package it.rutigliano.iremembermycrypto.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import it.rutigliano.iremembermycrypto.ApiConfig;
import it.rutigliano.iremembermycrypto.R;
import it.rutigliano.iremembermycrypto.model.Crypto;

public class CryptoTradeActivity extends AppCompatActivity {

    private EditText inputCryptoTrade;
    private TextView eurTradeValue;
    private TextView cryptoToTradeLabel;
    private Button confirmTradeButton;

    private ArrayList<Crypto> cryptos;
    private Crypto selectedCrypto = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_trade);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.wallet_preferences), Context.MODE_PRIVATE);

        String cryptoToTradeSymbol = getIntent().getStringExtra("crypto_trade_intention");

        cryptoToTradeLabel = findViewById(R.id.crypto_to_trade_label);
        inputCryptoTrade = findViewById(R.id.input_crypto_trade);
        eurTradeValue = findViewById(R.id.eur_trade_value);
        confirmTradeButton = findViewById(R.id.button_confirm_trade);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ApiConfig.API_URL, null, response -> {
            cryptos = ApiConfig.GSON.fromJson(response.toString(), new TypeToken<List<Crypto>>() {}.getType());
            selectedCrypto = findCryptoBySymbol(cryptoToTradeSymbol);
            cryptoToTradeLabel.setText(selectedCrypto.getSymbol());

            inputCryptoTrade.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (TextUtils.isEmpty(s.toString().trim())) {
                        eurTradeValue.setText("0.00");
                    } else {
                        if (selectedCrypto != null) {
                            Double portfolioValue = Double.parseDouble(s.toString());
                            selectedCrypto.setOwnedValue(portfolioValue);
                            eurTradeValue.setText(String.valueOf(selectedCrypto.getPrice() * portfolioValue));
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            confirmTradeButton.setOnClickListener(v -> {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(selectedCrypto.getSymbol(), selectedCrypto.getOwnedValue().toString());
                editor.apply();
                finish();
            });

        }, error -> Log.e("CRYPTO", "Error " + error.getMessage()));

        Volley.newRequestQueue(this).add(request);



    }

    public Crypto findCryptoBySymbol(String symbol) {
        for(Crypto c : cryptos) {
            if(c.getSymbol().equalsIgnoreCase(symbol)) {
                return c;
            }
        }
        return null;
    }

}