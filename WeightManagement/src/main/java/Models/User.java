package Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dylan Castanhinha on 3/30/2017.
 */

@ParseClassName("_User")
public class User extends ParseUser {

    public User() {
        super();
    }

    public String getFullName() {
        return getString("fullname");
    }
    public void setFullName(String value) {
        put("fullname", value);
    }

    public boolean getTrainerStatus(){
        return getBoolean("trainerstatus");
    }
    public void setTrainerStatus(boolean value){
        put("trainerstatus", value);
    }

    public String getLocation(){
        return getString("location");
    }

    public void setLocation(String value){
        put("location", value);
    }

    public String getFirstName(){
        return getString("firstname");
    }
    public void setFirstName(String value){
        put("firstname", value);
    }
    public String getLastName(){
        return getString("lastname");
    }
    public void setLastName(String value){
        put("lastname", value);
    }

    public int getCalories(){
        return getInt("calories");
    }
    public void setCalories(int value){
        put("calories", value);
    }

    public ParseRelation<User> getClients() {
        return getRelation("client");
    }

    public void setClients(ParseRelation<User> clients) {
        put("client", clients);
    }

    public ParseFile getProfilePictureFile(){
        return getParseFile("profilepicture");
    }

    public Bitmap getProfilePicture(){
        Bitmap bmp = null;
        ParseFile profilePicture = getProfilePictureFile();
        try {
            bmp = BitmapFactory.decodeStream(profilePicture.getDataStream());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bmp;
    }
    public void setProfilePicture(byte[] value){
        final ParseFile profilePicture = new ParseFile("profilepicture.png", value);
        profilePicture.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("AppInfo", "Photo saved");
                    User currentUser = (User) ParseUser.getCurrentUser();
                    currentUser.put("profilepicture", profilePicture);
                    currentUser.saveInBackground();
                } else {
                    Log.i("AppInfo", e.getMessage());
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {
                Log.i("AppInfo", "Percent Done: "+percentDone.toString());
            }
        });
    }

}

