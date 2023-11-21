package algonquin.cst2335.inclassexamples_f23;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.inclassexamples_f23.databinding.ActivityMainBinding;


/** This class is the starting point of the application
 * @author Etorunski
 * @version  1.0
 */
public class MainActivity extends AppCompatActivity {
    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        //This part goes at the top of the onCreate function:
        queue = Volley.newRequestQueue(this);
       setContentView(binding.getRoot());

       binding.loginButton.setOnClickListener( click -> {

           //get the forecast       replace " ", / with +
           String cityName = null;
           try {

               cityName = URLEncoder.encode( binding.passwordText.getText().toString() ,"UTF-8");


                String url = "https://api.openweathermap.org/data/2.5/weather?q="+ cityName   +"&appid=7e943c97096a9784391a981c4d878b22&units=metric";

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                  ( response) ->{
                    try {
                        JSONObject mainObj = response.getJSONObject("main");
                        double temperature = mainObj.getDouble("temp");

                        binding.textView.setText("The temperature is:" + temperature);
                        binding.textView.setVisibility(View.VISIBLE);

                        String iconName = "";
                      JSONArray weatherArray = response.getJSONArray("weather");

                      for(int i = 0; i < weatherArray.length(); i++)
                      {
                        JSONObject thisObj = weatherArray.getJSONObject(i);
                        iconName  = thisObj.getString("icon");
                      }
                      //now that we have the iconName:
                      //2nd query for the image

                        File imageFIle = new File(iconName + ".png");


                        String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
                        ImageRequest imgReq = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                // Do something with loaded bitmap...
                                binding.weatherImage.setImageBitmap(bitmap);
                                Log.d("Image recieved", "Got the image");
                            }
                        }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                (error ) -> {
                            Log.d("Error", "NO image downloaded");
                        });

                        queue.add(imgReq); //fetches from the server
                    }catch(JSONException e){


                    }

                    Log.d("Response", "Received " + response.toString());
                    /*this gets called if the server responded*/ },
                  (error) -> {  /*this gets called if there was an error or no response*/  }
                );

                queue.add(request); //fetches from the server

           } catch (Exception e) {
            throw new RuntimeException(e);
        }

       });


     }


}