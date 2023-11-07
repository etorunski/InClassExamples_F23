package algonquin.cst2335.inclassexamples_f23;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import algonquin.cst2335.inclassexamples_f23.databinding.DetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

    ChatMessage message;
    public MessageDetailsFragment(ChatMessage thisMessage){
        message = thisMessage;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate an XML layout for this Fragment

        DetailsLayoutBinding binding = DetailsLayoutBinding.inflate(inflater, container, false);
//set the text views:
        binding.messageId.setText( message.message );
        binding.timeId.setText(message.timeSent);
        binding.sendId.setText(""+ message.sentOrReceive);
        binding.databaseId.setText( Long.toString(  message.id)  );


        return binding.getRoot();
    }
}
