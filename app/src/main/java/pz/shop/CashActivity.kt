package pz.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity

class CashActivity : AppCompatActivity() {

    private var scanningTime: String = ""
    private var productName: String = ""
    private var quantity = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cash_reg_sim_activity)

        val numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        numberPicker.minValue = 0
        numberPicker.maxValue = 1000
        numberPicker.value = 10
        numberPicker.wrapSelectorWheel = true
        numberPicker.setOnValueChangedListener {
                _, _, newVal -> quantity = newVal
        }
    }

    override fun onBackPressed() {
        goBack()
    }

    fun clickBack(view: View) {
        goBack()
    }

    fun clickScan(view: View) {
        scanningTime += "Scan made!\n"
    }

    fun clickAdd(view: View) {
        val prodName = findViewById<EditText>(R.id.product_input).text.toString()
        productName = prodName

        val cashIntent = Intent()
        cashIntent.putExtra("name", "//${scanningTime}Clicked Remove\nProd: $productName\nQuantity: $quantity\n//")
        setResult(RESULT_OK, cashIntent)
        finish()
    }

    private fun goBack() {
        val cashIntent = Intent()
        cashIntent.putExtra("name", "//${scanningTime}Aborted action//")
        setResult(RESULT_OK, cashIntent)
        finish()
    }
}