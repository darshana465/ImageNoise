package com.example.asus.myapplication16;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.asus.myapplication16.custom.CustomActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Activity MainActivity is the Main screen of the app. It simply shows a
 * dummy image with some filter options. You need to write actual code image
 * processing and filtering.
 * 
 */
public class MainActivity extends CustomActivity
{
	private static int RESULT_LOAD_IMG = 1;
	String impDeclarableString;
	public Bitmap bmp = null;
	/** The selected filter icon. */
	private View selected;
	ImageButton Median_Filter, Canny_Filter, Mirror_Filter;
	ImageView output;
	//RecyclerView mRecyclerView;
	//RecyclerView.LayoutManager mLayoutManager;
	//RecyclerView.Adapter mAdapter;
	//ArrayList<String> alName;
	//ArrayList<Integer> alImage;

	/** The actual imageview. */
	private ImageButton img;

	/* (non-Javadoc)
	 * @see com.newsfeeder.custom.CustomActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setTouchNClick(R.id.cross);
		setTouchNClick(R .id.check);
		selected = setTouchNClick(R.id.eff1);
		//setTouchNClick(R.id.eff2);
		//setTouchNClick(R.id.eff3);
		//setTouchNClick(R.id.eff4);
		img = (ImageButton) findViewById(R.id.img);
		Median_Filter = (ImageButton) findViewById(R.id.Median_Filter);
		//output = (ImageButton) findViewById(R.id.outputImage);
		Mirror_Filter = (ImageButton) findViewById(R.id.Mirror_Filter);
		//scrollView = (ProgressBar) findViewById(R.id.scrollView);
		Canny_Filter = (ImageButton) findViewById(R.id.Canny_Filter);


		img = (ImageButton) setTouchNClick(R.id.img);
		setupActionBar();
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				loadImageryGallery();

			}
		});
		Median_Filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bmp != null) {
					img.setImageBitmap(medianFilterAlgorithm(bmp));
					saveImage(bmp, "fotazo");
				} else {
					Toast.makeText(getApplicationContext(), "No has seleccionado ninguna foto", Toast.LENGTH_LONG)
							.show();
				}
			}
		});


		Canny_Filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bmp != null) {
					cannyFilterAlg(bmp);
				} else {
					Toast.makeText(getApplicationContext(), "No has seleccionado ninguna foto", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
		Mirror_Filter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bmp != null) {
					img.setImageBitmap(applyGaussianBlur(bmp));
				} else {
					Toast.makeText(getApplicationContext(), "No has seleccionado ninguna foto", Toast.LENGTH_LONG)
							.show();
				}
			}
		});
	}

	/**
	 * This method will setup the top title bar (Action bar) content and display
	 * values. It will also setup the custom background theme for ActionBar. You
	 * can override this method to change the behavior of ActionBar for
	 * particular Activity
	 */
	private void saveImage(Bitmap finalBitmap, String image_name) {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root);
		myDir.mkdirs();
		String fname = "Image-" + image_name + ".jpg";
		File file = new File(myDir, fname);
		if (file.exists()) file.delete();
		Log.i("LOAD", root + fname);
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void loadImageryGallery() {
		Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = {MediaStore.Images.Media.DATA};
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				impDeclarableString = cursor.getString(columnIndex);
				cursor.close();
				Bitmap copy = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(impDeclarableString),
						714,
						438,
						false);
				img.setImageBitmap(copy);
				Bitmap b = BitmapFactory.decodeFile(impDeclarableString);
				final Bitmap resizable = Bitmap.createScaledBitmap(b, 714, 438, false);
				bmp = Bitmap.createScaledBitmap(resizable, 714, 438, false);

			}
		} catch (Exception e) {
			Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
					.show();
		}

	}
	protected void setupActionBar()
	{
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setLogo(R.drawable.icon);
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setHomeButtonEnabled(false);
	}

	/* (non-Javadoc)
	 * @see com.activity.custom.CustomActivity#onClick(android.view.View)
	 */
//	@Override
/*	public void onClick(View v)
	{
		super.onClick(v);
		if (v.getId() == R.id.eff1 || v.getId() == R.id.eff2
				|| v.getId() == R.id.eff3 || v.getId() == R.id.eff4)
		{
			selected.setBackgroundResource(0);
			selected = v;
			selected.setBackgroundColor(getResources().getColor(
					R.color.main_pink));

			if (v.getId() == R.id.eff1)
				img.setImageResource(R.drawable.img1);
			else if (v.getId() == R.id.eff2)
				img.setImageResource(R.drawable.img2);
			else if (v.getId() == R.id.eff3)
				img.setImageResource(R.drawable.img3);
			else if (v.getId() == R.id.eff4)
				img.setImageResource(R.drawable.img4);
		}
		else if (v.getId() == R.id.check || v == img)
		{
			startActivity(new Intent(this, Edit.class));
		}
	}*/
	private Bitmap medianFilterAlgorithm(Bitmap bitmap) {
		int[] pixel = new int[9];
		int[] R = new int[9];
		int[] G = new int[9];
		int[] B = new int[9];
		Bitmap out = bitmap;
		for (int i = 1; i < bitmap.getWidth() - 1; i++) {
			for (int j = 1; j < bitmap.getHeight() - 1; j++) {

				pixel[0] = bitmap.getPixel(i - 1, j - 1);
				pixel[1] = bitmap.getPixel(i - 1, j);
				pixel[2] = bitmap.getPixel(i - 1, j + 1);
				pixel[3] = bitmap.getPixel(i, j + 1);
				pixel[4] = bitmap.getPixel(i + 1, j + 1);
				pixel[5] = bitmap.getPixel(i + 1, j);
				pixel[6] = bitmap.getPixel(i + 1, j - 1);
				pixel[7] = bitmap.getPixel(i, j - 1);
				pixel[8] = bitmap.getPixel(i, j);

				for (int k = 0; k < 9; k++) {
					R[k] = Color.red(pixel[k]);
					G[k] = Color.green(pixel[k]);
					B[k] = Color.blue(pixel[k]);
				}

				Arrays.sort(R);
				Arrays.sort(G);
				Arrays.sort(B);
				out.setPixel(i, j, Color.rgb(R[4], G[4], B[4]));
			}
		}
		//Intent intent = new Intent(this, NextActivity.class);
		//intent.putExtra("BitmapImage", out);
		return out;
	}

	/*private Bitmap mirrorFilterAlgorithm(Bitmap photo) {
		int r, g, b;
		int r1, g1, b1;
		int width = photo.getWidth() - 1;
		int color;
		int color1;
		Bitmap out = photo;
		for (int i = 0; i < (photo.getWidth()) / 2; i++) {
			for (int j = 0; j < photo.getHeight(); j++) {

				color1 = photo.getPixel(i, j);
				color = photo.getPixel(width, j);

				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);

				r1 = Color.red(color1);
				g1 = Color.green(color1);
				b1 = Color.blue(color1);

				out.setPixel(i, j, Color.rgb(r, g, b));
				out.setPixel(width, j, Color.rgb(r1, g1, b1));
			}
			width--;
		}
		Log.d("FUNCIONA", "FUNCIONA");
		return out;
	}*/


	private Bitmap cannyFilterAlg(Bitmap Image) {
		Canny myCanny = new Canny();
		img.setImageBitmap(myCanny.process(Image));
		return Image;
	}
	public static Bitmap applyGaussianBlur(Bitmap src) {
		double[][] GaussianBlurConfig = new double[][] {
				{ 1, 2, 1 },
				{ 2, 4, 2 },
				{ 1, 2, 1 }
		};
		ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
		convMatrix.applyConfig(GaussianBlurConfig);
		convMatrix.Factor = 16;
		convMatrix.Offset = 0;
		return ConvolutionMatrix.computeConvolution3x3(src, convMatrix);
	}
}
