package algonquin.cst2335.inclassexamples_f23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import algonquin.cst2335.inclassexamples_f23.data.MessageViewModel;
import algonquin.cst2335.inclassexamples_f23.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.inclassexamples_f23.databinding.ReceiveRowBinding;
import algonquin.cst2335.inclassexamples_f23.databinding.SentRowBinding;

public class ChatRoom extends AppCompatActivity {

    ArrayList<String> theMessages = null;

    ActivityChatRoomBinding binding ;
    RecyclerView.Adapter myAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the data from the ViewModel:
       MessageViewModel chatModel = new ViewModelProvider(this).get(MessageViewModel.class);
        theMessages = chatModel.theMessages;

        binding.addSomething.setOnClickListener( click ->{
            String newMessage = binding.newMessage.getText().toString();
            theMessages.add(newMessage);
            binding.newMessage.setText("");//remove what you typed
            //tell the recycle view to update:
            myAdapter.notifyDataSetChanged();//will redraw
        });

                                //creates rows 0 to 50
        binding.myRecyclerView.setAdapter(
                myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

                    //just inflate the XML
                    @NonNull @Override                                              // implement multiple layouts
                    public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        //viewType will be 0 for the first 3 rows, 1 for everything after

                        if(viewType == 0) {
                            SentRowBinding rowBinding = SentRowBinding.inflate(getLayoutInflater(), parent, false);
                            return new MyRowHolder(rowBinding.getRoot()); //call your constructor below
                        }
                        else {  //after row 3
                            ReceiveRowBinding rowBinding = ReceiveRowBinding.inflate(getLayoutInflater(), parent, false);
                            return new MyRowHolder(rowBinding.getRoot());
                        }
                    }

                    @Override
                    public int getItemViewType(int position) {
                        //given the row, return an layout id for that row

                        if(position < 3)
                            return 0;
                        else
                             return 1;
                    }

                    @Override
                    public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                        //replace the default text with text at row position

                        String forRow = theMessages.get(position);
                        holder.message.setText(forRow);
                        holder.time.setText("time for row " + position);
                    }

                    //number of rows you want
                    @Override
                    public int getItemCount() {
                        return theMessages.size();
                    }
                                           }
        ); //populate the list

        binding.myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }


    //this represents a single row on the list
    class MyRowHolder extends RecyclerView.ViewHolder {

        public TextView message;
        public TextView time;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            //like onCreate above
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time); //find the ids from XML to java
        }
    }
}