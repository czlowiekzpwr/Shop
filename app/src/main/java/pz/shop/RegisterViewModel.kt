package pz.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pz.shop.data.Item

class RegisterViewModel : ViewModel() {
    private val scannedItems: MutableLiveData<ArrayList<Item>> = MutableLiveData<ArrayList<Item>>().also { it.value = ArrayList<Item>() }
    fun getItems(): MutableLiveData<ArrayList<Item>> {
        return scannedItems
    }
}