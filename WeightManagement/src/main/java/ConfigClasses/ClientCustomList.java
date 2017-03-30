package ConfigClasses;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.parse.starter.R;
import com.parse.starter.ViewControllers.ClientProfileController;

import java.util.ArrayList;
import java.util.List;

import Models.Client;

/**
 * Created by Dylan Castanhinha on 3/20/2017.
 */

public class ClientCustomList extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    int currentListView;
    private List<Client> clientList = null;
    private ArrayList<Client> arrayList;

    public ClientCustomList(Context context, List<Client> clientList, int currentListView){

        this.context = context;
        this.clientList = clientList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Client>();
        this.arrayList.addAll(clientList);
        this.currentListView = currentListView;
    }

    public class ViewHolder {
        TextView objectId;
        TextView name;
    }

    @Override
    public int getCount() {
        return clientList.size();
    }

    @Override
    public Object getItem(int position) {
        return clientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null && currentListView == 1){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_layout_new_client, null);
            holder.objectId = (TextView) convertView.findViewById(R.id.textViewObjectId);
            holder.name = (TextView) convertView.findViewById(R.id.textViewUserName);
            convertView.setTag(holder);
        } else if (convertView == null && currentListView == 0){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_layout_current_clients, null);
            holder.objectId = (TextView) convertView.findViewById(R.id.current_client_object_id);
            holder.name = (TextView) convertView.findViewById(R.id.current_client_text_view_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.objectId.setText(clientList.get(position).getObjectId());
        holder.name.setText(clientList.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClientProfileController.class);
                intent.putExtra("objectId", clientList.get(position).getObjectId());
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}