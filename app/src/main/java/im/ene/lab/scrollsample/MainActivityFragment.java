package im.ene.lab.scrollsample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.nineoldandroids.view.ViewHelper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    /**
     * pass the callback from Activity to which this Fragment is attached
     */
    private ObservableScrollViewCallbacks mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // best practice for getting interface from Activity to which this fragment is attached
        if (activity instanceof ObservableScrollViewCallbacks) {
            mCallback = (ObservableScrollViewCallbacks) activity;
        }
    }

    /**
     * Main Scrollview of this fragment
     */
    private ObservableScrollView mScrollView;

    /**
     * Image View that receives parallax effect
     */
    private View mImageView;

    /**
     * Anchor view :-?
     */
    private View mAnchorView;

    /**
     * Visible height of image view
     */
    private int mParallaxImageHeight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // using onViewCreated is just my habit. You can always do all those stuff below inside onCreateView
    // before returning the main view of this Fragment
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // some fragment may have null view (No UI Fragments)
        // this is just for template and default coding habit
        if (view != null) {
            mScrollView = (ObservableScrollView) view.findViewById(R.id.scroller);
            // setting callback for our Scrollview here, so our main Activity can handle scroll effect
            // in respect to this scrollview's scroll mount
            mScrollView.setScrollViewCallbacks(mCallback);
            // the rest parts
            mImageView = view.findViewById(R.id.image);
            mAnchorView = view.findViewById(R.id.anchor);
        }
    }

    /**
     * This method is to dynamically observe scroll effect from outside of this fragment
     * When this Fragment is attached to an Activity which implements ObservableScrollViewCallbacks,
     * call this method from inside Activity#onScrollChanged() to activate scroll effect of this fragment
     *
     * @param scrollY
     * @param firstScroll
     * @param dragging
     */
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        // Be carefully to fragment's life cycle. our view is not always created at runtime. It takes sometime.
        if (mImageView != null) {
            ViewHelper.setTranslationY(mImageView, scrollY / 2);
        }
    }
}
