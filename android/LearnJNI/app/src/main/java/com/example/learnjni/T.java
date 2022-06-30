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


class T
{
	private static final String TAG = "T-->";

	private static final T ourInstance = new T();

	static T getInstance() {return ourInstance;}

	private T() {}

}
