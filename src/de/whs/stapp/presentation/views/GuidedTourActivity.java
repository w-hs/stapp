package de.whs.stapp.presentation.views;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
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
	private Integer[] imageList = { R.drawable.gt_1, R.drawable.gt_2,
			R.drawable.gt_3, R.drawable.gt_4, R.drawable.gt_5, R.drawable.gt_6,
			R.drawable.gt_7, };
	private int curIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_guided_tour);

		imageSwitcher = (ImageView) findViewById(R.id.guidedTourImageView);

		loadCurrentImage();

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

		imageSwitcher.setImageResource(imageList[curIndex]);
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
