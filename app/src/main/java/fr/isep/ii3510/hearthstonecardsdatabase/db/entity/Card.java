package fr.isep.ii3510.hearthstonecardsdatabase.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "card")
public class Card implements Parcelable {
    // Define the attributes to match the HTTP response
    @PrimaryKey @NonNull
    public String cardid = "";
    public String dbfid;
    public String name;
    public String cardset;
    public String type;
    public String rarity;
    public String health;
    public String playerclass;
    public String cost;
    public String description;
    public String attack;
    public String race;
    public String img;
    public String imggold;

    public Card() {
    }

    protected Card(Parcel in) {
        cardid = in.readString();
        dbfid = in.readString();
        name = in.readString();
        cardset = in.readString();
        type = in.readString();
        rarity = in.readString();
        health = in.readString();
        playerclass = in.readString();
        cost = in.readString();
        description = in.readString();
        attack = in.readString();
        race = in.readString();
        img = in.readString();
        imggold = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cardid);
        parcel.writeString(dbfid);
        parcel.writeString(name);
        parcel.writeString(cardset);
        parcel.writeString(type);
        parcel.writeString(rarity);
        parcel.writeString(health);
        parcel.writeString(playerclass);
        parcel.writeString(cost);
        parcel.writeString(description);
        parcel.writeString(attack);
        parcel.writeString(race);
        parcel.writeString(img);
        parcel.writeString(imggold);
    }
}
