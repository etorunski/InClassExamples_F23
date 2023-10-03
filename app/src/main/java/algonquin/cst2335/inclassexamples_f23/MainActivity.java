package algonquin.cst2335.inclassexamples_f23;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import algonquin.cst2335.inclassexamples_f23.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    //same as public static void main(String args[])
    @Override //app starts here
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        //this is the only function call, loads stuff onto screen
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener( click->{
            //do this when clicked:
           Intent newPage = new Intent(MainActivity.this, SecondActivity.class);

           newPage.putExtra("LoginName", binding.emailField.getText().toString());
           newPage.putExtra("Age", 34.567);

           startActivity(newPage);
            /*        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                startActivity(cameraIntent);//this will go to a new page
            }
            else {
                requestPermissions( new String[] {android.Manifest.permission.CAMERA}, 0);
            }

             */
        } );
    }


    @Override //app is visible
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "HIt onStart");
    }

    @Override  // app now responds to clicks
    protected void onResume() {
        super.onResume();

        Log.e("MainActivity", "HIt onResume");
    }

    @Override //opposite to onResume, no longer getting clicks
    protected void onPause() {
        super.onPause();

        Log.e("MainActivity", "HIt onPause");
    }
    @Override//no longer visible
    protected void onStop() {
        super.onStop();

        Log.e("MainActivity", "HIt onStop");
    }


    @Override //been garbage collected
    protected void onDestroy() {
        super.onDestroy();
    }
}