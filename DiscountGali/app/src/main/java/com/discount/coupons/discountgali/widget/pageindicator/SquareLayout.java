package com.discount.coupons.discountgali.widget.pageindicator;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareLayout extends FrameLayout {
	public SquareLayout(Context context) {
		super(context);
	}

	public SquareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int size = Math.min(width, height);
		if (size == 0)
			size = Math.max(width, height);

		//Log.d("SquareLayout", "SquareLayout size= " + size + " width= " + width + " height= " + height);

		super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
	}
}
