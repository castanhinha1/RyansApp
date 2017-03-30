package Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Dylan Castanhinha on 3/20/2017.
 */

@ParseClassName("Client")
public class Client extends ParseObject {

    public String getObjectId() {
        return getString("objectId");
    }
    public void setObjectId(String value) {
        put("objectId", value);
    }

    public String getName() {
        return getString("username");
    }
    public void setName(String value) {
        put("username", value);
    }
}

