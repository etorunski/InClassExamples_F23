package algonquin.cst2335.inclassexamples_f23.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//data survives rotation changes
public class MainViewModel extends ViewModel {

    //observe this object:
    public MutableLiveData<String> userString = new MutableLiveData("");
}
