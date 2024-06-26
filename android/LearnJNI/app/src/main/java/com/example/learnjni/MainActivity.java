package com.example.learnjni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.learnjni.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

	// Used to load the 'learnjni' library on application startup.
	static
	{
		System.loadLibrary("learnjni");
	}

	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		// Example of a call to a native method
		TextView tv = binding.sampleText;
		tv.setText(LearnJNI.getInstance().stringFromJNI());
	}


}