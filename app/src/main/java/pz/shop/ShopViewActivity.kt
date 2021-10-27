package pz.shop

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ShopViewActivity : AppCompatActivity()  {

    var returningText: String = "Back from Shop"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shop_view_activity)
    }

    override fun onBackPressed() {
        goBack()
    }

    fun clickBack(view: View) {
        goBack()
    }

    private fun goBack() {
        val cashIntent = Intent()
        cashIntent.putExtra("name", "//Aborted action\n$returningText//")
        setResult(RESULT_OK, cashIntent)
        finish()
    }
}