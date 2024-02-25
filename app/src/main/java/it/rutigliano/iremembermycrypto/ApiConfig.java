package it.rutigliano.iremembermycrypto;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ApiConfig {
    public final static Gson GSON = new Gson();
    public final static String API_URL = "https://api.youngplatform.com/api/v3/currencies";

}
