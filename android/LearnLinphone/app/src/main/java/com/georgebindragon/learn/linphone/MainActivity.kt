package com.georgebindragon.learn.linphone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import org.linphone.core.Factory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun init(view: View) {

        // Core is the main object of the SDK. You can't do much without it.
        // To create a Core, we need the instance of the Factory.
        val factory = Factory.instance()

        // Some configuration can be done before the Core is created, for example enable debug logs.
        factory.setDebugMode(true, "Hello Linphone")

        // Your Core can use up to 2 configuration files, but that isn't mandatory.
        // On Android the Core needs to have the application context to work.
        // If you don't, the following method call will crash.
        val core = factory.createCore(null, null, this)

        // Now we can start using the Core object
        findViewById<TextView>(R.id.core_version).text = core.version
    }
}