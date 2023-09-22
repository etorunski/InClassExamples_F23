package algonquin.cst2335.inclassexamples_f23.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import algonquin.cst2335.inclassexamples_f23.R;
import algonquin.cst2335.inclassexamples_f23.data.MainViewModel;
import algonquin.cst2335.inclassexamples_f23.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    //same as public static void main(String args[])
    @Override //app starts here
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate( getLayoutInflater() );

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        //this is the only function call, loads stuff onto screen
        setContentView( binding.getRoot() );



        CheckBox cb =binding.myCheckbox; //same as getElementById() in HTML
        Switch sw =  binding.mySwitch; //must not be null
        RadioButton et =binding.myRadioButton;
                                        //what was clicked,  onOrOff
        cb.setOnCheckedChangeListener( (buttonView, isChecked) ->{
            viewModel.onOrOff.postValue( isChecked );
        });
        sw.setOnCheckedChangeListener( (btn, onOrOff) -> {
            viewModel.onOrOff.postValue( onOrOff );
        });
        et.setOnCheckedChangeListener( (btn, onOrOff) -> {
            viewModel.onOrOff.postValue( onOrOff );
        });

        viewModel.onOrOff.observe(this, newBool -> {
            cb.setChecked(newBool);
            sw.setChecked(newBool);
            et.setChecked(newBool);
        });
    }
}