package com.tianzunchina.sample.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.tianzunchina.android.api.log.TZLog;

import java.util.NoSuchElementException;

public class StickyLayout extends LinearLayout {
	private static final String TAG = "StickyLayout";
	private static final boolean DEBUG = true;

	public interface OnGiveUpTouchEventListener {
		public boolean giveUpTouchEvent(MotionEvent event);
	}

	private View mHeader;
	private View mContent;
	private OnGiveUpTouchEventListener mGiveUpTouchEventListener;

	// header的高度 单位：px
	private int mHeaderHeight;
	private int mHeaderMarginTop = 0;

	private int mStatus = STATUS_DISPLAY;
	public static final int STATUS_DISPLAY = 1;
	public static final int STATUS_HIDDEN = 2;

	private int mTouchSlop;

	// 分别记录上次滑动的坐标
	private int mLastX = 0;
	private int mLastY = 0;

	// 分别记录上次滑动的坐标(onInterceptTouchEvent)
	private int mLastXIntercept = 0;
	private int mLastYIntercept = 0;

	private boolean mIsSticky = true;
	private boolean mInitDataSucceed = false;
	private boolean mDisallowInterceptTouchEventOnHeader = true;

	public StickyLayout(Context context) {
		super(context);
	}

	public StickyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StickyLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if (hasWindowFocus && (mHeader == null || mContent == null)) {
			initData();
		}
	}

	private void initData() {
		int headerId = getResources().getIdentifier("sticky_header", "id",
				getContext().getPackageName());
		int contentId = getResources().getIdentifier("sticky_content", "id",
				getContext().getPackageName());
		if (headerId != 0 && contentId != 0) {
			mHeader = findViewById(headerId);
			mContent = findViewById(contentId);
			mHeaderHeight = mHeader.getMeasuredHeight();
			mTouchSlop = ViewConfiguration.get(getContext())
					.getScaledTouchSlop();
			if (mHeaderHeight > 0) {
				mInitDataSucceed = true;
			}
			if (DEBUG) {
				TZLog.e(TAG + " initData()", "mTouchSlop = " + mTouchSlop
						+ ", mHeaderHeight = " + mHeaderHeight);
			}
		} else {
			throw new NoSuchElementException(
					"Did your view with id \"sticky_header\" or \"sticky_content\" exists?");
		}
	}

	public void setOnGiveUpTouchEventListener(OnGiveUpTouchEventListener l) {
		mGiveUpTouchEventListener = l;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		boolean intercepted = false;
		int x = (int) event.getX();
		int y = (int) event.getY();

		if (DEBUG) {
			TZLog.e(TAG + " onInterceptTouchEvent()", "x = " + x + ", y = " + y);
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mLastXIntercept = x;
			mLastYIntercept = y;
			mLastX = x;
			mLastY = y;
			intercepted = false;
			if (DEBUG) {
				TZLog.e(TAG + " onInterceptTouchEvent() ACTION_DOWN",
						"intercepted = " + intercepted);
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastXIntercept;
			int deltaY = y - mLastYIntercept;

			if (DEBUG) {
				TZLog.e(TAG + " onInterceptTouchEvent() ACTION_MOVE", "deltaX = "
						+ deltaX + ", deltaY = " + deltaY + ", mStatus = "
						+ mStatus);
			}

			if (mDisallowInterceptTouchEventOnHeader) {
				if (Math.abs(deltaY) <= Math.abs(deltaX)) {
					intercepted = false;
				} else if (mStatus == STATUS_DISPLAY && deltaY <= -mTouchSlop) {
					intercepted = true;
				} else if (mGiveUpTouchEventListener != null) {
					if (mStatus == STATUS_HIDDEN
							&& mGiveUpTouchEventListener
									.giveUpTouchEvent(event)
							&& deltaY >= mTouchSlop) {
						intercepted = true;
					}
				}
			}

			if (DEBUG) {
				TZLog.e(TAG + " onInterceptTouchEvent() ACTION_MOVE",
						"intercepted = " + intercepted);
			}
			break;
		}
		case MotionEvent.ACTION_UP: {
			mLastXIntercept = mLastYIntercept = 0;
			intercepted = false;

			if (DEBUG) {
				TZLog.e(TAG + " onInterceptTouchEvent() ACTION_UP",
						"intercepted = " + intercepted);
			}

			break;
		}
		default:
			break;
		}

		if (DEBUG) {
			TZLog.e(TAG + " onInterceptTouchEvent()", "intercepted="
					+ intercepted);
		}
		return intercepted && mIsSticky;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!mIsSticky) {
			return true;
		}
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			int deltaX = x - mLastX;
			int deltaY = y - mLastY;
			if (DEBUG) {
				TZLog.e(TAG + " onTouchEvent() ACTION_MOVE",
						"mHeaderMarginTop = " + mHeaderMarginTop
								+ ", deltaY = " + deltaY + ", mlastY = "
								+ mLastY);
			}
			mHeaderMarginTop += deltaY;
			setHeaderMarginTop(mHeaderMarginTop);
			break;
		}
		case MotionEvent.ACTION_UP: {
			// 这里做了下判断，当松开手的时候，会自动向两边滑动，具体向哪边滑，要看当前所处的位置
			int destHeaderMarginTop = 0;
			if (Math.abs(mHeaderMarginTop) <= mHeaderHeight * 0.5) {
				destHeaderMarginTop = 0;
				mStatus = STATUS_DISPLAY;
			} else {
				destHeaderMarginTop = -mHeaderHeight;
				mStatus = STATUS_HIDDEN;
			}
			// 慢慢滑向终点
			this.smoothSetHeaderMarginTop(mHeaderMarginTop,
					destHeaderMarginTop, 500);
			break;
		}
		default:
			break;
		}
		mLastX = x;
		mLastY = y;
		return true;
	}

	public void smoothSetHeaderMarginTop(final int from, final int to,
			long duration) {
		final int frameCount = (int) (duration / 1000f * 30) + 1;
		final float partation = (to - from) / (float) frameCount;
		new Thread("Thread#smoothSetHeaderHeight") {

			@Override
			public void run() {
				for (int i = 0; i < frameCount; i++) {
					final int marginTop;
					if (i == frameCount - 1) {
						marginTop = to;
					} else {
						marginTop = (int) (from + partation * i);
					}
					post(new Runnable() {
						public void run() {
							setHeaderMarginTop(marginTop);
						}
					});
					try {
						sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			};

		}.start();
	}

	public void setHeaderMarginTop(int marginTop) {
		if (!mInitDataSucceed) {
			initData();
		}

		if (DEBUG) {
			TZLog.e(TAG + " setHeaderMarginTop()",
					"setHeaderMarginTop marginTop = " + marginTop);
		}
		if (marginTop >= 0) {
			marginTop = 0;
		} else if (marginTop <= -mHeaderHeight) {
			marginTop = -mHeaderHeight;
		}

		if (marginTop == 0) {
			mStatus = STATUS_DISPLAY;
		} else {
			mStatus = STATUS_HIDDEN;
		}

		if (mHeader != null && mHeader.getLayoutParams() != null) {
			((MarginLayoutParams) mHeader.getLayoutParams()).setMargins(0,
					marginTop, 0, 0);
			mHeader.requestLayout();
			mHeaderMarginTop = marginTop;
		} else {
			if (DEBUG) {
				TZLog.e(TAG + " setHeaderMarginTop()",
						"null LayoutParams when setHeaderMarginTop");
			}
		}
	}

	public int getHeaderHeight() {
		return mHeaderHeight;
	}

	public int getHeaderMarginTop() {
		return mHeaderMarginTop;
	}

	public void setSticky(boolean isSticky) {
		mIsSticky = isSticky;
	}

	public void requestDisallowInterceptTouchEventOnHeader(
			boolean disallowIntercept) {
		mDisallowInterceptTouchEventOnHeader = disallowIntercept;
	}

}