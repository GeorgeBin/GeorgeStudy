package com.georgebindragon.learn.linphone

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import org.linphone.core.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity-->"

    private lateinit var core: Core

    // Create a Core listener to listen for the callback we need
    // In this case, we want to know about the account registration status
    private val coreListener = object : CoreListenerStub() {
        override fun onAccountRegistrationStateChanged(core: Core, account: Account, state: RegistrationState, message: String) {
            // If account has been configured correctly, we will go through Progress and Ok states
            // Otherwise, we will be Failed.
            findViewById<TextView>(R.id.registration_status).text = message

            if (state == RegistrationState.Failed || state == RegistrationState.Cleared) {
                findViewById<AppCompatButton>(R.id.connect).isEnabled = true
            } else if (state == RegistrationState.Ok) {
                findViewById<AppCompatButton>(R.id.disconnect).isEnabled = true
            }

            Log.i(TAG, "onAccountRegistrationStateChanged-->core=$core\taccount=$account\tstate=$state\tmessage=$message")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun init(view: View) {

        // Core is the main object of the SDK. You can't do much without it.
        // To create a Core, we need the instance of the Factory.
        val factory = Factory.instance()

        // Some configuration can be done before the Core is created, for example enable debug logs.
        //        factory.setDebugMode(true, "Hello Linphone")
        factory.enableLogcatLogs(true)
        factory.setLoggerDomain("Linphone Demo")
        // Your Core can use up to 2 configuration files, but that isn't mandatory.
        // On Android the Core needs to have the application context to work.
        // If you don't, the following method call will crash.
        core = factory.createCore(null, null, this)

        // Now we can start using the Core object
        findViewById<TextView>(R.id.core_version).text = core.version
    }

    var domain = "192.168.0.241:2060"
    var username = "80006"
    var password = username
    var remoteSipUri = "sip:80003@$domain"

    fun connect(view: View) {
        // Get the transport protocol to use.
        // TLS is strongly recommended
        // Only use UDP if you don't have the choice
        val transportType = TransportType.Udp

        // To configure a SIP account, we need an Account object and an AuthInfo object
        // The first one is how to connect to the proxy server, the second one stores the credentials

        // The auth info can be created from the Factory as it's only a data class
        // userID is set to null as it's the same as the username in our case
        // ha1 is set to null as we are using the clear text password. Upon first register, the hash will be computed automatically.
        // The realm will be determined automatically from the first register, as well as the algorithm
        val authInfo = Factory.instance().createAuthInfo(username, null, password, null, null, domain, null)

        // Account object replaces deprecated ProxyConfig object
        // Account object is configured through an AccountParams object that we can obtain from the Core
        val accountParams = core.createAccountParams()

        // A SIP account is identified by an identity address that we can construct from the username and domain
        val identity = Factory.instance().createAddress("sip:$username@$domain")
        accountParams.identityAddress = identity

        // We also need to configure where the proxy server is located
        val address = Factory.instance().createAddress("sip:$domain")
        // We use the Address object to easily set the transport protocol
        address?.transport = transportType
        accountParams.serverAddress = address
        accountParams.isRegisterEnabled = true
        // And we ensure the account will start the registration process
        accountParams.isRegisterEnabled = true
        accountParams.pushNotificationAllowed = true

        // Now that our AccountParams is configured, we can create the Account object
        val account = core.createAccount(accountParams)

        // Now let's add our objects to the Core
        core.addAuthInfo(authInfo)
        core.addAccount(account)

        // Also set the newly added account as default
        core.defaultAccount = account

        // Allow account to be removed
        findViewById<Button>(R.id.delete).isEnabled = true

        // To be notified of the connection status of our account, we need to add the listener to the Core
        core.addListener(coreListener)
        // We can also register a callback on the Account object
        account.addListener { _, state, message ->
            // There is a Log helper in org.linphone.core.tools package
            Log.i(TAG, "[Account] Registration state changed: $state, $message")
        }

        // Finally we need the Core to be started for the registration to happen (it could have been started before)
        core.start()


        if (!core.isPushNotificationAvailable) {
            Toast.makeText(this, "Something is wrong with the push setup!", Toast.LENGTH_LONG).show()
        }

    }

    fun disconnect(view: View) {
        // Here we will disable the registration of our Account
        val account = core.defaultAccount
        account ?: return

        val params = account.params
        // Returned params object is const, so to make changes we first need to clone it
        val clonedParams = params.clone()

        // Now let's make our changes
        clonedParams.isRegisterEnabled = false

        // And apply them
        account.params = clonedParams

    }

    fun delete(view: View) {
        // To completely remove an Account
        val account = core.defaultAccount
        account ?: return
        core.removeAccount(account)

        // To remove all accounts use
        core.clearAccounts()

        // Same for auth info
        core.clearAllAuthInfo()

    }
}