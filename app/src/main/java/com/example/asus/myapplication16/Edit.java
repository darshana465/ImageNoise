package com.example.asus.myapplication16;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;

import com.example.asus.myapplication16.custom.CustomActivity;

/**
 * The Activity Edit is launched when you taps on main image or on check mark
 * icon in MainActivity. It simply shows a dummy image with some filter options
 * and option for sharing the image. You need to write actual code image
 * processing and filtering.
 * 
 */
public class Edit extends CustomActivity
{

	/* (non-Javadoc)
	 * @see com.newsfeeder.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);

		setTouchNClick(R.id.f1);
		setTouchNClick(R.id.f2);
		setTouchNClick(R.id.btnColor);

		setupActionBar();
	}

	/**
	 * This method will setup the top title bar (Action bar) content and display
	 * values. It will also setup the custom background theme for ActionBar. You
	 * can override this method to change the behavior of ActionBar for
	 * particular Activity
	 */
	protected void setupActionBar()
	{
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle("Edit & Share");
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setLogo(R.drawable.icon);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
