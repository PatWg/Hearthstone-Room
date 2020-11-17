package fr.isep.ii3510.hearthstonecardsdatabase.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.isep.ii3510.hearthstonecardsdatabase.db.dao.CardDao;
import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;

@Database(entities = Card.class, version = 1)
public abstract class CardDatabase extends RoomDatabase {
    private static final String DB_NAME = "cardDatabase.db";
    private static final int NUMBER_OF_THREADS = 4;

    private static volatile CardDatabase INSTANCE;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CardDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CardDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CardDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public CardDatabase() {}

    public abstract CardDao cardDao();
}
