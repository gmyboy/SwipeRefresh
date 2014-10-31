package com.gmy.test;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.gmy.widget.swiperefresh.SwipeRefreshLayout;
import com.gmy.widget.swiperefresh.SwipeRefreshLayout.OnLoadListener;
import com.gmy.widget.swiperefresh.SwipeRefreshLayout.OnRefreshListener;

public class MainActivity extends Activity implements OnRefreshListener, OnLoadListener {
	private SwipeRefreshLayout refreshView;
	private ListView listView;
	private List<String> data;
	private ArrayAdapter<String> adapter;
	private ProgressBar progressBar;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter.notifyDataSetChanged();
				refreshView.setRefreshing(false);
				progressBar.setVisibility(View.GONE);
			}
			if (msg.what == 2) {
				adapter.notifyDataSetChanged();
				refreshView.setLoading(false);
				progressBar.setVisibility(View.GONE);
			}
		};
	};

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refreshView = (SwipeRefreshLayout) this.findViewById(R.id.swipe);
		listView = (ListView) this.findViewById(R.id.list);
		progressBar = (ProgressBar) this.findViewById(R.id.main_search_person);
		// progressBar.sta
		data = new ArrayList<String>();
		for (int i = 0; i < 50; i++) {
			data.add("sdsadasdasd");
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
		refreshView.setTopColor(android.R.color.holo_red_light, android.R.color.holo_green_light,
				android.R.color.holo_blue_light, android.R.color.holo_orange_light);
		refreshView.setBottomColor(android.R.color.holo_red_light,
				android.R.color.holo_green_light, android.R.color.holo_blue_light,
				android.R.color.holo_orange_light);
		listView.setAdapter(adapter);
		refreshView.setOnRefreshListener(this);
		refreshView.setOnLoadListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void onRefresh() {
		progressBar.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				try {
					data.add(0, "after refresh...");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(1);
			};
		}.start();
	}

	@Override
	public void onLoad() {
		progressBar.setVisibility(View.VISIBLE);
		new Thread() {
			public void run() {
				try {
					data.add("after load...");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(2);
			};
		}.start();
	}
}
