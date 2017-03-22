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

import java.util.ArrayList;
import java.util.List;

import Models.Client;

/**
 * Created by Dylan Castanhinha on 3/20/2017.
 */

public class NewClientCustomList extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Client> clientList = null;
    private ArrayList<Client> arrayList;

    public NewClientCustomList(Context context, List<Client> clientList){

        this.context = context;
        this.clientList = clientList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Client>();
        this.arrayList.addAll(clientList);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.list_layout_new_client, null);
            holder.objectId = (TextView) convertView.findViewById(R.id.textViewObjectId);
            holder.name = (TextView) convertView.findViewById(R.id.textViewUserName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.objectId.setText(clientList.get(position).getObjectId());
        holder.name.setText(clientList.get(position).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AppInfo", "Client Pressed");
            }
        });

        return convertView;
    }
}