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

import java.io.File;
import java.io.FileNotFoundException;

import algonquin.cst2335.inclassexamples_f23.databinding.ActivityMainBinding;


/** This class is the starting point of the application
 * @author Etorunski
 * @version  1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     *
      * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());

       setContentView(binding.getRoot());

       binding.loginButton.setOnClickListener(click -> {
           String userInput = binding.passwordText.getText().toString();
           if(containsUpperAndLowerCase(userInput)){
               binding.responseText.setText("Your password has upper and lower case");
           }
           else binding.responseText.setText("Your password is not complex enough");
       });



     }


    /** This function checks str and returns true if str
     * has an upper and lower case letter in it
     * @param str - an unused parameter
     *
     * @return Returns true if str has upper and lower case, otherwise false
     */
    boolean containsUpperAndLowerCase( String str){
        boolean foundUpper = false;
        boolean foundLower = false;

        for(int i = 0; i < str.length(); i++)
        {
            //get each character in the string:
            char c = str.charAt(i);
            if(Character.isLowerCase(c))
                foundLower = true;
            else if(Character.isUpperCase(c))
                foundUpper = true;
        }
        //after looked at every character
        return foundLower && foundUpper;
    }
}