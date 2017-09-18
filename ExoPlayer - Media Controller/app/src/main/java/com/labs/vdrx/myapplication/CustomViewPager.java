package com.labs.vdrx.myapplication;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Use this to remove the bug in the onTouchEvent for the support libraries related to this
 * https://code.google.com/p/android/issues/detail?id=18990 Solution derived from this:
 * http://stackoverflow.com/q/18074029/346309 and this http://stackoverflow.com/q/16459196/346309
 * 
 * @author jmatthews
 * 
 */
public class CustomViewPager extends ViewPager {
	private boolean enabled = true;

	/**
	 * Constructor that just points to its super constructor ViewPager
	 * 
	 * @param context
	 *            - activity context to use to attach to
	 */
	public CustomViewPager(Context context) {
		super(context);
	}

	/**
	 * Constructor that just points to its super constructor ViewPager
	 * 
	 * @param context
	 *            - activity context to use to attach to
	 * @param attrs
	 *            - AttributeSet to pass to superclass ViewPager
	 */
	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		if (this.enabled) {
			try {
				return super.onInterceptTouchEvent(ev);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (this.enabled) {
			try {
				return super.onTouchEvent(ev);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}

		return false;
	}

	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}