package com.example.csv;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

/**
 * http://opencsv.sourceforge.net/
 */


public class MainActivity extends AppCompatActivity
{
	private static final String TAG = "MainActivity-->";

	Handler handler;
	String  filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handler = new Handler();
		filePath = getExternalCacheDir().getPath() + "/test.csv";
		Log.e(TAG, "onCreate-->filePath=" + filePath);

		try
		{
			//			// 基础用法
			//			CSVWriter writer = new CSVWriter(new FileWriter(filePath));
			//			// feed in your array (or convert your data to an array)
			//			String[] entries = {"id", "name", "name", "name", "name",};
			//			writer.writeNext(entries);
			//			writer.close();

			//			// 使用Bean
			//			int             sal  = 1;
			//			ArrayList<Bean> list = new ArrayList<>();
			//			for (int i = 0; i < 50; i++)
			//			{
			//				list.add(new Bean("name" + sal, sal, new Date(System.currentTimeMillis() + sal * 1000L), sal));
			//				sal++;
			//			}
			//			Writer                  writer    = new FileWriter(filePath);
			//			StatefulBeanToCsv<Bean> beanToCsv = new StatefulBeanToCsvBuilder<Bean>(writer).build();
			//			beanToCsv.write(list);
			//			writer.close();

			//			// 策略：排序
			//			Writer                                writer   = new FileWriter("yourfile.csv");
			//			HeaderColumnNameMappingStrategy<Bean> strategy = new HeaderColumnNameMappingStrategyBuilder<Bean>().build();
			//			strategy.setType(Bean.class);
			//			strategy.setColumnOrderOnWrite(new MyComparator());
			//			StatefulBeanToCsv beanToCsv = StatefulBeanToCsvBuilder(writer)
			//					.withMappingStrategy(strategy)
			//					.build();
			//			beanToCsv.write(beans);
			//			writer.close();
		} catch (Exception e)
		{
			Log.e(TAG, "onCreate", e);
		}
	}

	public void test(View view)
	{
		Thread thread = new Thread(() ->
		{
			try
			{
				Log.e(TAG, "test-->run");

				int             sal  = 1;
				ArrayList<Bean> list = new ArrayList<>();
				for (int i = 0; i < 50; i++)
				{
					list.add(new Bean("name" + sal, sal, new Date(System.currentTimeMillis() + sal * 1000L), sal));
					sal++;
				}

				Writer writer = new FileWriter(filePath);
				StatefulBeanToCsv<Bean> beanToCsv = new StatefulBeanToCsvBuilder<Bean>(writer)
						.build();
				beanToCsv.write(list);
				writer.close();

				handler.post(() -> Toast.makeText(MainActivity.this, "导出完成\n存储路径：" + filePath, Toast.LENGTH_LONG).show());

			} catch (Exception e)
			{
				Log.e(TAG, "test", e);
			}
		});
		thread.start();
		Log.e(TAG, "test-->");
	}
}