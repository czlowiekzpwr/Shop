package pz.shop

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private var welcomeText: String = "Welcome Text\n"
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            Log.i("tester", "Bef")
            if (result.resultCode == Activity.RESULT_OK) {
                Log.i("tester", "In")
                val intent = result.data
                Log.i("tester", "Mid")
                welcomeText += intent?.getStringExtra("name")
                welcomeText += "\n"
                actionClicked("")
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun clickCash(view: View) {
        startForResult.launch(Intent(this, CashActivity::class.java))
        actionClicked("Cash Clicked\n")
    }

    fun clickScan(view: View) {
        startForResult.launch(Intent(this, ManScanActivity::class.java))
        actionClicked("Scanner Clicked\n")
    }

    fun clickShopView(view: View) {
        startForResult.launch(Intent(this, ShopViewActivity::class.java))
        actionClicked("Shop View Clicked\n")
    }

    fun clickDummy(view: View) {
        actionClicked("Dummy Clicked\n")
    }

    private fun actionClicked(name: String) {
        val mainText = findViewById<View>(R.id.textView) as TextView
        welcomeText += name
        mainText.text = welcomeText
    }

}