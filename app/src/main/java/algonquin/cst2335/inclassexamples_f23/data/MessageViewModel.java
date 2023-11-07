package algonquin.cst2335.inclassexamples_f23.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.inclassexamples_f23.ChatMessage;

public class MessageViewModel extends ViewModel {
    public    ArrayList<ChatMessage> theMessages = new java.util.ArrayList<>();

    public MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData<>();
}
