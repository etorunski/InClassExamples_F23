package algonquin.cst2335.inclassexamples_f23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/** This class is the first page of the application
 * @author Eric Torunski
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /** This represents the string message at the top*/
    private TextView theMessage;

    /** THis holds the login button object */
    private Button loginButton;

    /**this holds the text field to write the password */
    private EditText passwordText;
    /**  This is the starting point for MainActivity
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override //app starts here
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //this is the only function call, loads stuff onto screen
        setContentView(R.layout.activity_main);

        theMessage = findViewById(R.id.textView);
        loginButton = findViewById(R.id.loginButton);
        passwordText = findViewById(R.id.loginText);

        loginButton.setOnClickListener( click -> {
            String password = passwordText.getText().toString();
            boolean isComplex = isComplexEnough(password);

            if(isComplex)
                theMessage.setText("It has upper and lower case");
            else theMessage.setText("You are missing something in your password");
        });
    }


    /** This function scans a string and returns true if it has an upper and lower
     * case character.
     *
     * @param text This is the string to search
     * @return True if text has upper case, lower case
     */
    private boolean isComplexEnough(String text){
        boolean result = false;
        boolean foundUpperCase= false, foundLowerCase =false;
        for(int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if(Character.isUpperCase(c))
                foundUpperCase = true;
            else if(Character.isLowerCase(c))
                foundLowerCase = true;
        }

        return (foundLowerCase && foundUpperCase);
    }
}