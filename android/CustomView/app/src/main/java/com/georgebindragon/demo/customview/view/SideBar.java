package com.georgebindragon.demo.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.georgebindragon.demo.customview.R;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class SideBar extends View
{
	private static final String TAG = "SideBar-->";

	public SideBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public SideBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public SideBar(Context context)
	{
		super(context);
	}

	// 索引内容
	public static String[] b = {
			"☆", "0~9",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
			"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
			"#"
	};

	private int   choose = -1;// 选中
	private Paint paint  = new Paint();

	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		int height = getHeight();// 获取对应高度
		int width  = getWidth(); // 获取对应宽度

		float singleHeight = (height * 1f) / b.length;// 获取每一个字母的高度
		singleHeight = (height * 1f - singleHeight / 2) / b.length;
		for (int i = 0; i < b.length; i++)
		{
			paint.setColor(Color.BLACK);
			paint.setTypeface(Typeface.DEFAULT);
			paint.setAntiAlias(true);
			paint.setTextSize(23);

			// x坐标等于中间-字符串宽度的一半.
			float xPos = width / 2f - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);

			paint.reset();// 重置画笔
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event)
	{
		final int   action = event.getAction();
		final float y      = event.getY();// 点击y坐标
		final int   c      = (int) (y / getHeight() * b.length);// 点击y坐标所占总高度的比例*b数组的长度就等于点击b中的个数.

		switch (action)
		{
			case MotionEvent.ACTION_UP:
				setBackgroundDrawable(new ColorDrawable(0x00000000));

				this.choose = -1;
				invalidate();
				changeEvent(false);
				break;

			default:
				// 获取焦点改变背景颜色
				setBackgroundDrawable(new ColorDrawable(0x13161316));

				if (choose != c)
				{
					if (c >= 0 && c < b.length)
					{
						changeLetter(b[c]);
						changeEvent(true);
						this.choose = c;
						invalidate();
					}
				}
				break;
		}
		return true;
	}

	private boolean isPressed;

	private void changeEvent(boolean isPressed)
	{
		if (this.isPressed != isPressed)
		{
			this.isPressed = isPressed;
		}
	}

	private String letter = "";

	private void changeLetter(String letter)
	{
		if (!this.letter.equals(letter))
		{
			this.letter = letter;
		}
	}
}
