package algonquin.cst2335;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.inclassexamples_f23.ChatMessage;
import algonquin.cst2335.inclassexamples_f23.ChatMessageDAO;

@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {

    //only 1function: return the DAO object
    public abstract ChatMessageDAO cmDAO();//name doesn't matter
}
