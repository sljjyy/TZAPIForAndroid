package com.tianzunchina.android.api.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tianzunchina.android.api.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 广告展示控件【图片滚动展示】
 * CraetTime 2016-3-14
 * @author SunLiang
 */
@SuppressLint("HandlerLeak")
public class AdTools extends FrameLayout implements Runnable {
	private Context context;
	// 滚动展示的控件
	private ViewPager viewPager = null;
	// 滚动展示控件的adapter
	private PageAdapter pageAdapter = null;
	// 要展示的界面
	private List<ImageView> imageViews = null;
	// 展示图片的页码（一排点标志展示哪张图）
	private List<ImageView> dotViews = null;
	// 点击的监听事件
	private ClickListener clickListener = null;
	// “当前是哪页”的标志点布局
	private LinearLayout dotsLinearLayout = null;
	// 这个控件内的图片（drawable）集合
	private String[] urls = null;
	// 滚动线程休眠时间【秒】
	// 广告标题控件
	private TextView adTitle;
	// 广告标题
	private String[] titlesString = null;
	private int sleepTime = 1,scrollTime = 1500;// 滑动速度
	// 当前页码
	private int curPosition = 0;
	// 最大页码
	private int maxPage = 0;
	// 自动滚动的线程
	private Thread thread = null;
	// 用于线程的运行
	private boolean isContinue = true;

	public AdTools(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.activity_advertisement, this, true);

		viewPager = (ViewPager) findViewById(R.id.advertisements);
		initViewPagerScroll(viewPager);
		adTitle = (TextView) findViewById(R.id.ad_title);
		dotsLinearLayout = (LinearLayout) findViewById(R.id.dotsLinearLayout);

		// 实现一个PagerAdapter
		imageViews = new ArrayList<ImageView>();
		pageAdapter = new PageAdapter(imageViews);
		viewPager.setAdapter(pageAdapter);
		// 为viewPager添加页面改变、接触监听
		viewPager.addOnPageChangeListener(new PageChangeListener());
		viewPager.setOnTouchListener(new PageTounchListener());
	}

	/**
	 * @param urls
	 */
	@SuppressWarnings("deprecation")
	public void setData(String[] urls) {
		if (urls == null) {
			return;
		}
		dotViews = new ArrayList<ImageView>();
		// 定义图片样式，高宽MATCH_PARENT
		LayoutParams imageViewParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// 定义圆点样式，自适应，左右间距margin
		LayoutParams dotViewParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		dotViewParams.setMargins(20, 20, 20, 20);

		for (int i = 0; i < urls.length; i++) {
			final ImageView imageView = new ImageView(context);
			Picasso.with(context).load(urls[i]).fit().config(Bitmap.Config.ALPHA_8).into(imageView);
			// 定义图片样式
			imageView.setLayoutParams(imageViewParams);
			ImageView dotView = new ImageView(context);
			// 设置圆点样式
			dotView.setLayoutParams(dotViewParams);
			// 设置ViewPager显示图片的页面内容
			dotView.setBackgroundResource((i == 0) ? R.mipmap.ico_ad_focused
					: R.mipmap.ico_ad_normal);
			// 将每个圆点添加到圆点列表中存储
			dotViews.add(dotView);
			// 将每个圆点添加到页面上
			dotsLinearLayout.addView(dotView, dotViewParams);

			final int position = i;
			if (clickListener != null) {
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						clickListener.onClick(position);
					}
				});
			}
			imageViews.add(imageView);
		}
		// 如果为两张图片，则将第一张图片添加到第三个位置，成为“ABAB”式的三张图片
		if (urls.length == 2) {
			for(int i=0;i<2;i++){
			final ImageView imageView = new ImageView(context);
			Picasso.with(context).load(urls[i]).fit().config(Bitmap.Config.ALPHA_8).into(imageView);
			imageView.setLayoutParams(imageViewParams);
			final int position = i;
			if (clickListener != null) {
				imageView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						clickListener.onClick(position);
					}
				});
			}
			imageViews.add(imageView);
		}}
		pageAdapter.notifyDataSetChanged();
	}

	/**
	 * 设置数据
	 * @param urls
	 */
	public void setData(String[] urls, String[] _titlesString,
			ClickListener _Listener, int _sleepTime) {
		this.titlesString = _titlesString;
		this.clickListener = _Listener;
		setData(urls);
		adTitle.setText(_titlesString[0]);
		if (urls.length > 1) {
			sleepTime = _sleepTime;
			thread = new Thread(this);
			thread.start();
		}
	}

	public interface ClickListener {
		void onClick(int position);
	}

	/**
	 * 向左滚动
	 */
	public void leftScroll() {
		if (viewPager != null)
			viewPager.setCurrentItem((--curPosition <= 0) ? maxPage
					: curPosition);
	}

	/**
	 * 向右滚动
	 */
	public void rightScroll() {
		if (viewPager != null)
			viewPager.setCurrentItem((++curPosition >= maxPage) ? 0
					: curPosition);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			rightScroll();
		}
	};

	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		while (!Thread.interrupted() && thisThread == thread) {
			try {
				Thread.sleep(1000 * sleepTime);
			} catch (InterruptedException e) {
				// e.printStackTrace();
				if (thread != null)
					thread.interrupt();
			}
			if (isContinue && handler != null) {
				handler.sendMessage(Message.obtain(handler));
			}
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	class PageTounchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				isContinue = false;
				break;
			case MotionEvent.ACTION_MOVE:
				isContinue = false;
				break;
			case MotionEvent.ACTION_UP:
				isContinue = true;
				break;
			default:
				isContinue = true;
				break;
			}
			return false;
		}
	}

	/**
	 * 内部类，页面切换监听
	 */
	class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {
			curPosition = position;
			if (titlesString != null) {
				adTitle.setText(titlesString[position % titlesString.length]);
			}
			if (dotViews != null) {
				for (int i = 0; i < dotViews.size(); i++) {
					// 不是当前选中的page，其小圆点设置为未选中的状态
					dotViews.get(i)
							.setBackgroundResource(
									(position % dotViews.size() != i) ? R.mipmap.ico_ad_normal
											: R.mipmap.ico_ad_focused);
				}
			}
		}
	}

	/**
	 * 初始化页面图片播放数据
	 */
	class PageAdapter extends PagerAdapter {
		List<ImageView> mImageViews;

		public PageAdapter(List<ImageView> _imageViews) {
			this.mImageViews = _imageViews;
		}

		/**
		 * 要显示的页面的个数
		 */
		@Override
		public int getCount() {
			// 设置成最大值以便循环滑动
			// int count = ((mImageViews == null) ? 0 : Integer.MAX_VALUE);
			int count = 0;
			if (mImageViews == null) {
				count = 0;
			} else if (mImageViews.size() <= 1) {
				count = mImageViews.size();
			} else if (mImageViews.size() > 1) {
				count = Integer.MAX_VALUE;
			}
			maxPage = count;
			return count;
		}

		/**
		 * 获取一个指定页面的title描述 如果返回null意味着这个页面没有标题，默认的实现就是返回null
		 * 如果要显示页面上的title则此方法必须实现
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// System.out.println("==标题==>"+titles[position]);
			// return titles[position];
			return null;
		}

		/**
		 * 创建指定position的页面。这个适配器会将页面加到容器container中。
		 * 
		 * @param container
		 *            创建出的实例放到container中，这里的container就是viewPager
		 * @return 返回一个能表示该页面的对象，不一定要是view，可以其他容器或者页面。
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			try {
				container.addView(
						mImageViews.get(position % mImageViews.size()), 0);
			} catch (Exception e) {
			}
			return (mImageViews.size() > 0) ? mImageViews.get(position
					% mImageViews.size()) : null;
		}

		/**
		 * 此方法会将容器中指定页面给移除 该方法中的参数container和position跟instantiateItem方法中的内容一致
		 * 
		 * @param object
		 *            这个object 就是 instantiateItem方法中返回的那个Object
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// 由于需要它循环滚动，所以不能将其清除掉。
			/*
			 * if(position<mImageViews.size()) {
			 * container.removeView(mImageViews.get(position)); }
			 */
		}

		/**
		 * 这个方法就是比较一下容器中页面和instantiateItem方法返回的Object是不是同一个
		 * 
		 * @param arg0
		 *            ViewPager中的一个页面
		 * @param arg1
		 *            instantiateItem方法返回的对象
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	/**
	 * 自定义Scroller，用于调节ViewPager滑动速度
	 * 
	 */
	@SuppressLint("NewApi")
	public class ViewPagerScroller extends Scroller {

		public ViewPagerScroller(Context context) {
			super(context);
		}

		public ViewPagerScroller(Context context, Interpolator interpolator) {
			super(context, interpolator);
		}

		public ViewPagerScroller(Context context, Interpolator interpolator,
				boolean flywheel) {
			super(context, interpolator, flywheel);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy,
				int duration) {
			super.startScroll(startX, startY, dx, dy, scrollTime);
		}

		@Override
		public void startScroll(int startX, int startY, int dx, int dy) {
			super.startScroll(startX, startY, dx, dy, scrollTime);
		}
	}

	/**
	 * 设置ViewPager的滑动速度
	 * 
	 * */
	private void initViewPagerScroll(ViewPager mViewPager) {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			ViewPagerScroller scroller = new ViewPagerScroller(
					mViewPager.getContext());
			mScroller.set(mViewPager, scroller);
		} catch (NoSuchFieldException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}
	}
}
