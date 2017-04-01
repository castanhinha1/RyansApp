package ListControllers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.starter.R;

import ConfigClasses.ParseAdapterCustomList;
import Models.User;

/**
 * Created by Dylan Castanhinha on 3/31/2017.
 */

public class NewClients extends ParseAdapterCustomList {

    Context context;

    public NewClients(Context context) {
        super(context, new ParseQueryAdapter.QueryFactory<User>() {
            public ParseQuery<User> create() {
                ParseQuery<User> query = ParseQuery.getQuery(User.class);
                query.whereEqualTo("trainerstatus", false);
                query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                return query;
            }

        });
        this.context = context;
    }

    @Override
    public View getItemView(final User user, View v, ViewGroup parent){
        if (v == null){
            v = View.inflate(getContext(), R.layout.list_layout_new_client, null);
        }
        super.getItemView(user, v, parent);

        //Add the title view
        TextView nameTextView = (TextView) v.findViewById(R.id.textViewUserName);
        nameTextView.setText(user.getFullName());

        //Add the objectid
        TextView objectId = (TextView) v.findViewById(R.id.textViewObjectId);
        objectId.setText(user.getObjectId());

        //On click listener for selection
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, ClientProfileController.class);
//                intent.putExtra("objectId", user.getObjectId());
//                context.startActivity(intent);
            }
        });

        return v;
    }
}


