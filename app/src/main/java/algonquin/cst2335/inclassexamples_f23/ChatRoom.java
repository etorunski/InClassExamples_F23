package algonquin.cst2335.inclassexamples_f23;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.MessageDatabase;
import algonquin.cst2335.inclassexamples_f23.data.MessageViewModel;
import algonquin.cst2335.inclassexamples_f23.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.inclassexamples_f23.databinding.ReceiveRowBinding;
import algonquin.cst2335.inclassexamples_f23.databinding.SentRowBinding;

public class ChatRoom extends AppCompatActivity {

    ArrayList<ChatMessage> theMessages = null;

    ActivityChatRoomBinding binding ;
    RecyclerView.Adapter myAdapter = null;

    //Declare the dao here:
    ChatMessageDAO mDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get the data from the ViewModel:
       MessageViewModel chatModel = new ViewModelProvider(this).get(MessageViewModel.class);
        theMessages = chatModel.theMessages;

        //load messages from the database:
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "fileOnYourPhone").build();

        //intialize the variable
        mDao = db.cmDAO(); //get a DAO object to interact with the database

        //load all messages from database:
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute( () -> {

            List<ChatMessage> fromDatabase = mDao.getAllMessages();//return a List
            theMessages.addAll(fromDatabase);//this adds all messages from the database

        });

        //end of loading from database

        binding.addSomething.setOnClickListener( click ->{
            String newMessage = binding.newMessage.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage thisMessage = new ChatMessage(newMessage, currentDateandTime, true);
            theMessages.add(thisMessage);
            binding.newMessage.setText("");//remove what you typed
            //tell the recycle view to update:
            myAdapter.notifyDataSetChanged();//will redraw

            // add to database on another thread:
            Executor thread1 = Executors.newSingleThreadExecutor();
            thread1.execute(( ) -> {
                //this is on a background thread
                thisMessage.id = mDao.insertMessage(thisMessage); //get the ID from the database
                Log.d("TAG", "The id created is:" + thisMessage.id);
            }); //the body of run()
        });


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

                        String forRow = theMessages.get(position).message;
                        holder.message.setText(forRow);
                        holder.time.setText(theMessages.get(position).timeSent);
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

            itemView.setOnClickListener( click -> {
                int rowNum = getAbsoluteAdapterPosition();//which row this is
                ChatMessage toDelete = theMessages.get(rowNum);
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

                builder.setNegativeButton("No" , (btn, obj)->{ /* if no is clicked */  }  );
                builder.setMessage("Do you want to delete this message?");
                builder.setTitle("Delete");

                builder.setPositiveButton("Yes", (p1, p2)-> {
                    /*is yes is clicked*/
                    Executor thread1 = Executors.newSingleThreadExecutor();
                    thread1.execute(( ) -> {
                        //delete from database
                        mDao.deleteThisMessage(toDelete);//which chat message to delete?

                    });
                    theMessages.remove(rowNum);//remove from the array list
                    myAdapter.notifyDataSetChanged();//redraw the list


                    //give feedback:anything on screen
                    Snackbar.make( itemView , "You deleted the row", Snackbar.LENGTH_LONG)
                            .setAction("Undo", (btn) -> {
                                Executor thread2 = Executors.newSingleThreadExecutor();
                                thread2.execute(( ) -> {
                                            mDao.insertMessage(toDelete);
                                        });


                                theMessages.add(rowNum, toDelete);
                                myAdapter.notifyDataSetChanged();//redraw the list
                            })
                            .show();
                });

                builder.create().show(); //this has to be last
            });

            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time); //find the ids from XML to java
        }
    }
}