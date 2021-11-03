package pz.shop

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import pz.shop.data.Item
import java.lang.NumberFormatException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

class RegisterActivity : AppCompatActivity() {
    lateinit var barcodeView: DecoratedBarcodeView

    var rvadapter : ScannedItemAdapter = ScannedItemAdapter()
    private val model: RegisterViewModel by viewModels()


    var lastScannedId : Long = 0
    @ExperimentalTime
    var lastScannedTime : TimeMark = TimeSource.Monotonic.markNow()
    @ExperimentalTime
    val bcCallback: BarcodeCallback = BarcodeCallback { result ->
        if (result.text != null && result.text.length == 13){
            try {
                val scannedItemId = result.text.toLong()
                Log.d("barc", scannedItemId.toString())
                if (scannedItemId != lastScannedId || lastScannedTime.elapsedNow().inWholeSeconds > 4){
                    lastScannedId = scannedItemId
                    lastScannedTime = TimeSource.Monotonic.markNow()
                    var itemData: Item? = null
                    //TODO: Get item from local DB
                    if (itemData != null){
                        val scannedItems = ArrayList<Item>(model.getItems().value!!)
                        val alreadyIdx = scannedItems.indexOfFirst { it.id == itemData.id }
                        if (alreadyIdx == -1){
                            itemData.scannedQty = 1
                            scannedItems.add(itemData)
                        } else{
                            scannedItems[alreadyIdx].scannedQty += 1
                        }
                        model.getItems().postValue(scannedItems)
                    } else{
                        Toast.makeText(this, "Not found in DB", Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: NumberFormatException) {}
        }
    }

    @ExperimentalTime
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val recView = findViewById<RecyclerView>(R.id.rvScannedList)
        recView.layoutManager = LinearLayoutManager(this)
        recView.adapter = rvadapter
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val scannedItems = ArrayList<Item>(model.getItems().value!!)
                scannedItems.removeAt(viewHolder.adapterPosition)
                model.getItems().postValue(scannedItems)
            }
        }
        rvadapter.submitList(model.getItems().value!!)

        val itemsObserver = androidx.lifecycle.Observer<ArrayList<Item>>{
            rvadapter.submitList(it)
        }
        model.getItems().observe(this, itemsObserver)

        barcodeView = findViewById(R.id.barcodePreview)
        val formats: Collection<BarcodeFormat> = Arrays.asList(BarcodeFormat.EAN_13)
        barcodeView.barcodeView.setDecoderFactory(DefaultDecoderFactory(formats))
        barcodeView.initializeFromIntent(intent)
        barcodeView.decodeContinuous(bcCallback)
    }

    val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            barcodeView.resume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            barcodeView.resume()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

    }

    override fun onPause() {
        super.onPause()
        barcodeView.pauseAndWait()
    }

    public fun endItemEntry(view: View){
        val scannedItems = ArrayList<Item>(model.getItems().value!!)
        //TODO log transaction in db / update stock
        model.getItems().postValue(ArrayList<Item>())
        finish()
    }
}
