package ConfigClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

import Models.Client;

/**
 * Created by Dylan Castanhinha on 3/20/2017.
 */

public class NewClientCustomList extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Client> clients = new ArrayList<Client>();

    public NewClientCustomList(Context context, List<Client> clients) {

        this.clients = clients;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Client client = (Client) getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.list_layout_new_client, null);
        }
        TextView name = (TextView) view.findViewById(R.id.textViewUserName);
        name.setText(client.name);
        TextView clientID = (TextView) view.findViewById(R.id.textViewObjectId);
        clientID.setText(client.objectId);
        return view;
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int position) {
        return clients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setClients(List<Client> data) {
        clients.addAll(data);
        notifyDataSetChanged();
    }

}