package com.bookstore.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ComputerActivity extends FragmentActivity {

	public static final String EXTRA_COMPUTER_ID = "computer_id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		
		if (fragment == null) {
			Intent i = getIntent();
			int computerId = (Integer)i.getIntExtra(EXTRA_COMPUTER_ID, -1);

			fragment = ComputerFragment.newInstance(computerId);

			fm.beginTransaction()
			  .add(R.id.fragmentContainer, fragment)
			  .commit();
		}
	}
}
