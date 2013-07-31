package com.vinnymac.popupmenulist;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.widget.ListView;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.internal.widget.IcsListPopupWindow;

public class MainActivity extends Activity implements OnDismissListener {

	private ImageButton ib;

	private IcsListPopupWindow mListPopupWindow;

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
				showListPopupWindow(arg1);
			}
		});
	}
	
	public void showListPopupWindow(View anchor){
		mListPopupWindow = new IcsListPopupWindow(MainActivity.this);
		mListPopupWindow.setAnchorView(anchor);
		mListPopupWindow.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, STRINGS));
		mListPopupWindow.setModal(true);
		Drawable draw = getResources().getDrawable(R.drawable.background_list_row);
		mListPopupWindow.setBackgroundDrawable(draw);		
		mListPopupWindow.setOnDismissListener(this);
		mListPopupWindow.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicked item " + position, Toast.LENGTH_SHORT).show();
            }
        });
		//mListPopupWindow.setOnItemClickListener(this);
		mListPopupWindow.show();
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
		private IcsListPopupWindow mListPopupWindow;
		private ViewHolder holder = null;
		private static final String[] STRINGS = {"Option1","Option2","Option3","Option4"};

		public PopAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
			super(context, resource, textViewResourceId, objects);
			this.context = context;
			this.resource = resource;
			this.textViewResourceId = textViewResourceId;
			this.objects = objects;

		}

		static class ViewHolder {
			TextView name;
			ImageView icon;
			ImageButton menu;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = super.getView(position, convertView, parent);
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = li.inflate(R.layout.row, parent, false);
				holder = new ViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.text);
				holder.name.setText(objects[position]);
				holder.icon = (ImageView) convertView.findViewById(R.id.imageView1);
				holder.menu = (ImageButton) convertView.findViewById(R.id.imageButton);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// View row = li.inflate(R.layout.row, parent, false);

			holder.menu.setTag(position);

			holder.menu.setOnClickListener(this);
			holder.menu.setFocusable(false);
			
			return convertView;
		}

		@Override
		public void onClick(View v) {
			int getPosition = (Integer) v.getTag();

			Log.v("X: ", "" + v.getX());
			Log.v("Y: ", "" + v.getY());

			Toast.makeText(context, "You Clicked", Toast.LENGTH_SHORT).show();
			//showListPopupWindow(holder.menu);
			
			
			
			/*PopupWindow popW = new PopupWindow();
			
			PopupMenu pop = new PopupMenu(context, v);
			pop.getMenuInflater().inflate(R.menu.main, pop.getMenu());

			pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem item) {
					Toast.makeText(context, "You Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
					return true;
				}

			});*/

		}
		
		/*public void showListPopupWindow(View anchor){
			mListPopupWindow = new IcsListPopupWindow(context);
			mListPopupWindow.setAnchorView(anchor);
			mListPopupWindow.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, STRINGS));
			mListPopupWindow.setModal(true);
			mListPopupWindow.setOnDismissListener(this);
			mListPopupWindow.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                Toast.makeText(context, "Clicked item " + position, Toast.LENGTH_SHORT).show();
	            }
	        });
			//mListPopupWindow.setOnItemClickListener(this);
			mListPopupWindow.show();
		}*/

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
