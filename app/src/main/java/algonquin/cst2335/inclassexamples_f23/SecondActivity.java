package algonquin.cst2335.inclassexamples_f23;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import algonquin.cst2335.inclassexamples_f23.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
String TAG = "SecondActivity";
    @Override //second page in app
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get the intent that launched you:
        Intent nextPage = getIntent();
        String userName = nextPage.getStringExtra("UserName"); //if not found, give null
        int address = nextPage.getIntExtra("Address", 55);



        double age = nextPage.getDoubleExtra("Age", 0.0);
        ActivitySecondBinding binding = ActivitySecondBinding.inflate( getLayoutInflater());
        setContentView( binding.getRoot() );

        binding.textView.setText("Welcome back "+ userName);

        binding.button.setOnClickListener( click -> {
Log.i(TAG, "clicked button to go back to page 1");
            finish(); //go back one page
        });
    }
}