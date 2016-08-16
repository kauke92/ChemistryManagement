package com.example.jason.mooo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.jason.mooo.Model.Cow;
import com.example.jason.mooo.R;
import com.example.jason.mooo.template_pages;

import java.util.ArrayList;

/**
 * Created by apple on 9/13/15.
 */

public class getCowsAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<Cow> myCows  = new ArrayList<Cow>();
    private Context context;
    public getCowsAdapter(ArrayList<Cow> cows,Context context)
    {
        myCows = cows;
        this.context = context;
    }

    public void addCow(Cow cow)
    {
        myCows.add(cow);
    }


    @Override
    public int getCount() {
        return myCows.size();
    }

    @Override
    public Object getItem(int position) {
        return myCows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.getcows_listview_template,parent,false);
        }

        TextView cow = (TextView)convertView.findViewById(R.id.cow);
        cow.setText(myCows.get(position).getId());

        Button administerBtn = (Button) convertView.findViewById(R.id.administer);

        administerBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context,template_pages.class);
                context.startActivity(myIntent);
            }
        });
        return null;
    }
}
