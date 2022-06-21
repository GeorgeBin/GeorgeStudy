package com.example.csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import com.opencsv.bean.CsvNumber;

import java.util.Date;

/**
 * 创建人：George
 *
 * 描述：
 *
 * 修改人：
 * 修改时间：
 * 修改备注：
 */


public class Bean
{
	@CsvIgnore
	private static final String TAG = "Bean-->";

	//		@CsvBindByPosition(position = 0) // 没有列时，使用position来固定位置：position：位置
	// 备注：此方式不能和 CsvBindByName 方式同时使用


	@CsvBindByName
	public String name;

	@CsvBindByName(column = "哈哈") // 列相关的设置：column：列名称、required：是否必要、capture：正则选择器？
	public int count;

	@CsvBindByName(column = "时间")
	@CsvDate("yyyy/MM/dd ") // 数据类型=日期：
	public Date time;

	@CsvBindByName(column = "价格", locale = "de-DE")
	@CsvNumber("#.###¤")// 数据类型=数字：
	private int salary;

	// 高级：列表中的列表

	public Bean(String name, int count, Date time, int salary)
	{
		this.name = name;
		this.count = count;
		this.time = time;
		this.salary = salary;
	}
}