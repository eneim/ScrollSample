package im.ene.lab.scrollsample;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class MainActivity extends AppCompatActivity implements ObservableScrollViewCallbacks {

    /**
     * our custom Actionbar Toolbar
     */
    private Toolbar mToolbar;

    /**
     * this colordrawable is used to change toolbar's color alpha
     */
    private ColorDrawable mToolbarColorDrawable;

    /**
     * Fragment which is the mainview of SlidingUpPanel
     * We will dynamically observe scroll action in our fragment by this instance;
     */
    private MainActivityFragment mFragment;

    /**
     * This is height of visible image view inside mFragment
     */
    private int mParallaxImageHeight;

    /**
     * Bonus view: as you want to use with SlidingUpPanelLayout library
     */
    private SlidingUpPanelLayout mSlidingLayout;
    /**
     * keep track of toolbar alpha, assume that you want to change toolbar's alpha along with SlidingPanel's state too
     */
    private int mLastToolbarColorAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize toolbar, transparent
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarColorDrawable = new ColorDrawable(getResources().getColor(R.color.colorPrimary));
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbarColorDrawable.setAlpha(0);
            mToolbar.setBackground(mToolbarColorDrawable);
        }

        // get attached fragment here
        mFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        // this value is universally used between MainActivity and MainActivityFragment
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);

        // bonus: observe state of this SlidingPanel
        mSlidingLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        // Don't use this anymore
        // mSlidingLayout.setPanelSlideListener(this);
    }

    /**
     * BONUS: change the toolbar's alpha on show/hide SlidingUpPanelLayout, remove it if you don't want
     */

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // alpha should be between 0.0f and 1.0f
        float alpha = Math.max(0, Math.min(1, (float) scrollY / mParallaxImageHeight));
        mLastToolbarColorAlpha = (int) (alpha * 255);
        // my preferred way to change toolbar alpha
        mToolbarColorDrawable.setAlpha(mLastToolbarColorAlpha);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mToolbar.setBackground(mToolbarColorDrawable);
        } else {
            mToolbar.setBackgroundDrawable(mToolbarColorDrawable);
        }

        // dynamically observe the scrolled mount inside fragment
        // refer to Google Iosched application for dynamically call Fragment's public method
        // note: carefully check our fragment view
        if (mFragment != null && mFragment.getView() != null) {
            mFragment.onScrollChanged(scrollY, firstScroll, dragging);
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

}
