package pz.shop

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import java.util.*

class RegisterActivity : AppCompatActivity() {
    lateinit var barcodeView: DecoratedBarcodeView

    val bcCallback: BarcodeCallback = BarcodeCallback { result ->
        if (result.text != null){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        barcodeView = findViewById(R.id.barcodePreview)
        val formats: Collection<BarcodeFormat> = Arrays.asList(BarcodeFormat.CODE_39, BarcodeFormat.UPC_A)
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
}
