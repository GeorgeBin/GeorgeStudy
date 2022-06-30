package com.example.learnjni;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class LearnJNI
{
	private static final String TAG = "LearnJNI-->";

	private static final LearnJNI ourInstance = new LearnJNI();

	static LearnJNI getInstance() {return ourInstance;}

	private LearnJNI() {}

	static
	{
		System.loadLibrary("learnjni");
	}

	/**
	 * A native method that is implemented by the 'learnjni' native library,
	 * which is packaged with this application.
	 */
	public native String stringFromJNI();
}