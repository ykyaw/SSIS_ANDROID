package iss.team1.ad.ssis_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import iss.team1.ad.ssis_android.R;
import iss.team1.ad.ssis_android.components.MenuItem;

public class MenuItemAdapter extends ArrayAdapter<MenuItem> {
    private int resourceId;
    private int mSelect = 0; // select item

    public MenuItemAdapter(Context context, int textViewResourceId, List<MenuItem> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuItem menuItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_text = (TextView) view.findViewById (R.id.item_text);
            view.setTag(viewHolder); // store ViewHolder into View
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // retrieve ViewHolder
        }
        viewHolder.item_text.setText(menuItem.getItem_text());
        if (mSelect == position) {
            view.findViewById(R.id.mark).setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            viewHolder.item_text.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            view.findViewById(R.id.mark).setBackgroundColor(Color.TRANSPARENT);
            viewHolder.item_text.setTextColor(getContext().getResources().getColor(R.color.gray));
        }
        return view;
    }

    public void changeSelected(int position) {
        if (position != mSelect) {
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    class ViewHolder {
        TextView item_text;
    }
}
