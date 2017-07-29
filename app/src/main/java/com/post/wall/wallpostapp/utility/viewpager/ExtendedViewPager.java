package com.post.wall.wallpostapp.utility.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class ExtendedViewPager extends ViewPager {
	private boolean swipeable = true;

	public ExtendedViewPager(Context context) {
	    super(context);
	}
	
	public ExtendedViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	}
	
	@Override
	protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
	    if (v instanceof TouchImageView) {
	    	//
	    	// canScrollHorizontally is not supported for Api < 14. To get around this issue,
	    	// ViewPager is extended and canScrollHorizontallyFroyo, a wrapper around
	    	// canScrollHorizontally supporting Api >= 8, is called.
	    	//
	        return ((TouchImageView) v).canScrollHorizontallyFroyo(-dx);
	        
	    } else {
	        return super.canScroll(v, checkV, dx, x, y);
	    }
	}
	
	public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (this.swipeable) {
			return super.onTouchEvent(event);
		}

		return false;
	}

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return (this.swipeable) ? super.onInterceptTouchEvent(arg0) : false; 
    }

}
