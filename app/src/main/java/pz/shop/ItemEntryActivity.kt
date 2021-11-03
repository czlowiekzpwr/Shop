package pz.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
/*Activity for adding/editting products in local db
  Requires user to enter/scan barcode first
  If no data found locally, auto-fills in suggestions from barcodelookup.com
 */
class ItemEntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_entry)
    }
}