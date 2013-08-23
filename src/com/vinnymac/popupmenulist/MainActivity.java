package com.vinnymac.popupmenulist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnDismissListener {

	private ImageButton ib;

	private ListPopupWindow mListPopupWindow;

	private static final String[] STRINGS = {"Option 1","Option 2","Option 3","Option 4"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ListView list = (ListView) findViewById(R.id.listView);
		list.setAdapter(buildDummyData());
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Toast.makeText(MainActivity.this, "Item " + arg2, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public ListAdapter buildDummyData() {
		final int SIZE = 20;
		String[] values = new String[SIZE];
		for (int i = 0; i < SIZE; i++) {
			values[i] = "Item " + i;
		}
		return new PopAdapter(this, R.layout.row, R.id.text, values);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public static class PopAdapter extends ArrayAdapter<String> implements OnClickListener, OnDismissListener {

		private final Context context;
		private int resource;
		private int textViewResourceId;
		private String[] objects;
		private ListPopupWindow mListPopupWindow;
		private static final String[] STRINGS = {"Option1","Option2","Option3","Option4"};
		
		TextView name;
		ImageView icon;
		ImageButton menu;

		private LayoutInflater mInflater;

		public PopAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
			super(context, resource, textViewResourceId, objects);
			this.context = context;
			this.resource = resource;
			this.textViewResourceId = textViewResourceId;
			this.objects = objects;
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			super.getView(position, convertView, parent);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.row, parent, false);
				
				name = (TextView) convertView.findViewById(R.id.text);
				icon = (ImageView) convertView.findViewById(R.id.imageView1);
				menu = (ImageButton) convertView.findViewById(R.id.imageButton);

				name.setText(objects[position]);
				menu.setTag(position);
				menu.setOnClickListener(this);
				menu.setFocusable(false);
			} 
			return convertView;
		}

		@Override
		public void onClick(View v) {
			Toast.makeText(context, "You Clicked", Toast.LENGTH_SHORT).show();
			
			mListPopupWindow = new ListPopupWindow(getContext());
			mListPopupWindow.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, STRINGS));
			mListPopupWindow.setAnchorView(v.findViewById(R.id.imageButton));
			mListPopupWindow.setWidth(200);
			mListPopupWindow.setHeight(325);
			mListPopupWindow.setModal(true);
			mListPopupWindow.setOnDismissListener(this);
			mListPopupWindow.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                Toast.makeText(getContext(), "Clicked item " + position, Toast.LENGTH_SHORT).show();
	            }
	        });
			mListPopupWindow.show();
		}

		@Override
		public void onDismiss() {
			mListPopupWindow.dismiss();
		}
	}

	@Override
	public void onDismiss() {
		mListPopupWindow.dismiss();
	}
}