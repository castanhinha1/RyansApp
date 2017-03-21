package ConfigClasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.starter.R;

/**
 * Created by Dylan Castanhinha on 7/22/2015.
 */
public class CurrentClientsCustomList extends ArrayAdapter<String> {
    private String[] names;
    private String[] desc;
    private Activity context;

    public CurrentClientsCustomList(Activity context, String[] names, String[] desc) {
        super(context, R.layout.list_layout_current_clients, names);
        this.context = context;
        this.names = names;
        this.desc = desc;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_current_clients, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
        TextView textViewDesc = (TextView) listViewItem.findViewById(R.id.textViewDesc);

        textViewName.setText(names[position]);
        textViewDesc.setText(desc[position]);
        return  listViewItem;
    }
}