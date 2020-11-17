package fr.isep.ii3510.hearthstonecardsdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import fr.isep.ii3510.hearthstonecardsdatabase.db.entity.Card;
import fr.isep.ii3510.hearthstonecardsdatabase.ui.CustomAdapter;
import fr.isep.ii3510.hearthstonecardsdatabase.viewmodel.CardViewModel;

public class MainActivity extends AppCompatActivity {
    public static final String DATABASE_PREFERENCES = "db_prefs";
    public static final String DATABASE_NEEDS_INITIALISATION = "db_initialised";
    private CardViewModel cardViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);

        // Is it the first time that I launch the app?
        if (databaseNeedsInitialisation()) {
            // initialise the database
            cardViewModel.initialiseDatabase();
        }
    }

    public boolean databaseNeedsInitialisation() {
        SharedPreferences prefs = getSharedPreferences(DATABASE_PREFERENCES, MODE_PRIVATE);
        return prefs.getBoolean(DATABASE_NEEDS_INITIALISATION, true);
    }

    public void searchDatabase(View view) {
        cardViewModel.getCards().observe(MainActivity.this, cards -> {
            CustomAdapter adapter = new CustomAdapter(
                    cards.toArray(new Card[0]),
                    (v, position) -> showDetails(cards, position)
            );
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            recyclerView.setAdapter(adapter);
        });
    }

    private void showDetails(List<Card> cards, int position) {
        Intent intent = new Intent(MainActivity.this, CardActivity.class);
        intent.putExtra("card", cards.get(position));
        startActivity(intent);
    }
}