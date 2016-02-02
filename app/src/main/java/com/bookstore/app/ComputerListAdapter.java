package com.bookstore.app;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ComputerListAdapter extends ArrayAdapter<Computer>
                             implements ListAdapter {

	private Activity mContext;
	private List<Computer> mComputers;
	
	public ComputerListAdapter(Context context, List<Computer> computers) {
		super(context, R.layout.list_item_book, computers);
		mContext = (Activity)context;
		mComputers = computers;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// If we weren't given a view, inflate one
        if (convertView == null) {
        	LayoutInflater inflater = mContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item_book, null);
        }

        // Configure the view for this Crime
        Computer bk = mComputers.get(position);

        TextView titleTextView = (TextView)convertView.findViewById(
        		R.id.list_item_book_title_textView);
        titleTextView.setText(bk.getMake());
        
        TextView yearTextView = (TextView)convertView.findViewById(
        		R.id.list_item_book_year_textView);
        yearTextView.setText("" + bk.getQuantity());
        
        TextView authorTextView = (TextView)convertView.findViewById(
        		R.id.list_item_book_author_textView);
        authorTextView.setText(bk.getModel());

        return convertView;
	}
}