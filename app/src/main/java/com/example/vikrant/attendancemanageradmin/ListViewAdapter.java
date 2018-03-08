package com.example.vikrant.attendancemanageradmin;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter implements OnItemClickListener{

	Context context;
	ArrayList<String> name,imageURL,URL;
	public ListViewAdapter(Context context) {
		this.context=context;
	}

	@Override
	public int getCount() {
		
		return name.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row=convertView;
		if(row==null)
		{
			LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row=layoutInflater.inflate(R.layout.myrow,parent, false);
		}
		ImageView imageView=(ImageView)row.findViewById(R.id.imageView1);

		TextView textView=(TextView)row.findViewById(R.id.textView1);
		textView.setText(""+name.get(position));
		return row;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		
	}
}
