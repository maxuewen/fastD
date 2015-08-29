package cn.zh.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoTouchViewPager extends ViewPager {

	private boolean isok;

	public NoTouchViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.isok = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (this.isok) {
			return super.onTouchEvent(arg0);
		}
		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (this.isok) {
			return super.onInterceptTouchEvent(event);
		}

		return false;
	}

	public void setPagingEnabled(boolean enabled) {
		this.isok = enabled;
	}

}
