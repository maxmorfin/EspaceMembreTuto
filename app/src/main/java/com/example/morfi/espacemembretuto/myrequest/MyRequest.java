package com.example.morfi.espacemembretuto.myrequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {

    private Context context;
    private RequestQueue queue;

    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void register (final String pseudo, final String email, final String password, final String password2, final RegisterCallBack callBack){
        String url = "http://10.10.13.119/tuto_espace_membre/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Map<String, String> errors = new HashMap<>();

                try {

                    JSONObject json = new JSONObject(response);

                    Boolean error = json.getBoolean("error");

                    if (!error){

                        callBack.onSuccess("Vous etes bien inscrit");

                    }else{

                        JSONObject message = json.getJSONObject("message");
                        if (message.has("pseudo")){
                            errors.put("pseudo", message.getString("pseudo"));
                        }
                        if (message.has("email")){
                            errors.put("email", message.getString("email"));
                        }
                        if (message.has("password")){
                            errors.put("password", message.getString("password"));
                        }
                        callBack.inputErrors(errors);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("APP", response );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callBack.onError("impossible de se connecter");
                }else if(error instanceof VolleyError){
                    callBack.onError("une erreur s'est produite");
                }
                Log.d("APP", "ERROR = " + error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo);
                map.put("email", email);
                map.put("password", password);
                map.put("password2", password2);


                return map;
            }
        };

        queue.add(request);

    }

    public interface RegisterCallBack{

        void onSuccess (String message);
        void inputErrors (Map<String, String> errors);
        void onError(String message);
    }

    public void connection(final String pseudo, final String password, final loginCallback callBack){

        String url = "http://10.10.13.119/tuto_espace_membre/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){

                        String id = json.getString("id");
                        String pseudo = json.getString("pseudo");
                        callBack.onSuccess(id, pseudo);

                    }else{

                        callBack.onError(json.getString("message"));

                    }

                } catch (JSONException e) {

                    callBack.onError("Une erreur s'est produite");
                    e.printStackTrace();

                }

                Log.d("APP", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError){
                    callBack.onError("impossible de se connecter");
                }else if(error instanceof VolleyError){
                    callBack.onError("une erreur s'est produite");
                }
                Log.d("APP", "ERROR = " + error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo);
                map.put("password", password);


                return map;
            }
        };

        queue.add(request);


    }

    public interface loginCallback{
        void onSuccess(String id, String pseudo);
        void onError(String message);
    }
}
