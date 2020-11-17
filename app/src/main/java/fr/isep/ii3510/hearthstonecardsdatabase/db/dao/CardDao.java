package fr.isep.ii3510.hearthstonecardsdatabase.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;

@Dao
public interface CardDao {
    // We are going to have INSERT to insert all the cards into the database
    // We are going to have SELECT to query the database with all sorts of parameters

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    List<Long> insert(Card... cards);

    @Query("SELECT * FROM card")
    LiveData<List<Card>> getCards();
}
