package de.whs.stapp.presentation.views;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import de.whs.stapp.R;

/**
 * Stellt eine Guided Tour durch die Anwendung bereit.
 * 
 * @author Thomas
 * 
 */
public class GuidedTourActivity extends Activity {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;

	private ImageView imageSwitcher;
	private Integer[] imageList = { R.drawable.gt_3, R.drawable.gt_1, R.drawable.gt_2,
			R.drawable.gt_5, R.drawable.gt_4,  R.drawable.gt_6,
			R.drawable.gt_7, };
	private int curIndex = 0;

	private int width = 100;
	private int height = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_guided_tour);

		imageSwitcher = (ImageView) findViewById(R.id.guidedTourImageView);

		ViewTreeObserver vto = imageSwitcher.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
		    public boolean onPreDraw() {
		        height = imageSwitcher.getMeasuredHeight()/2;
		        width = imageSwitcher.getMeasuredWidth()/2;
				loadCurrentImage();
		        return true;
		    }
		});

		final GestureDetector gdt = new GestureDetector(this,
				new GestureListener());
		imageSwitcher.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				gdt.onTouchEvent(event);
				return true;
			}
		});
	}

	@Override
	public void onBackPressed() {

		finish();
	}

	private void loadCurrentImage() {

		imageSwitcher.setImageBitmap(decodeSampledBitmapFromResource(
				getResources(), imageList[curIndex], width, height));
	}

	private int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	private Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * Behandelt alle Gestures die auf dem ImageSwitcher ausgeführt werden.
	 * 
	 * @author Thomas
	 * 
	 */
	private class GestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				if (curIndex + 1 == imageList.length) {
					GuidedTourActivity.this.finish();
				} else {
					curIndex++;
					loadCurrentImage();
				}
				return false;
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				curIndex = curIndex > 0 ? --curIndex : 0;
				loadCurrentImage();
				return false;
			}
			return false;
		}
	}
}
