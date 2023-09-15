package algonquin.cst2335.inclassexamples_f23;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //same as public static void main(String args[])
    @Override //app starts here
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//no ids loaded yet here
        //this is the only function call, loads stuff onto screen
        setContentView(R.layout.activity_main);


        TextView tv = findViewById(R.id.myTextView); //same as getElementById() in HTML

        Button b =  findViewById(R.id.myButton);

        EditText et = findViewById(R.id.myEditText);

        //OnClickListener     //anonymnous class
        b.setOnClickListener(v -> {
            //only run when you click the button
            tv.setText("You clicked the button");
            et.setText("You clicked the button");
            b.setText("You clicked the button");
        });
    }
}