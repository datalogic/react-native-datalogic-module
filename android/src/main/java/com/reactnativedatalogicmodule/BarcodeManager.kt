package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.Callback

import com.datalogic.decode.BarcodeManager
import com.datalogic.decode.DecodeException
import com.datalogic.decode.DecodeResult
import com.datalogic.decode.ReadListener
import com.datalogic.device.ErrorManager
import com.datalogic.decode.BarcodeID

import android.widget.TextView

class BarcodeManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private var decoder: com.datalogic.decode.BarcodeManager? = null
    private var listener: ReadListener? = null
    private lateinit var mBarcodeText: TextView

    override fun getName(): String {
        return "BarcodeManager"
    }

    // Example method
    // See https://facebook.github.io/react-native/docs/native-modules-android
//    @ReactMethod
//    fun multiply(a: Int, b: Int, promise: Promise) {
//      promise.resolve(a * b)
//    }

    @ReactMethod
    fun pressTrigger(promise: Promise) {
      try {
        if(decoder == null) {
          decoder = com.datalogic.decode.BarcodeManager()
        }
        promise.resolve(decoder?.pressTrigger())
      } catch(e: Exception) {
        promise.reject(e)
      }
    }

    @ReactMethod
    fun releaseTrigger(promise: Promise) {
      try {
        if(decoder == null) {
          decoder = com.datalogic.decode.BarcodeManager()
        }
        promise.resolve(decoder?.releaseTrigger())
      } catch(e: Exception) {
        promise.reject(e)
      }
    }

    @ReactMethod
    fun addReadListener(promise: Promise) {
      try {
        if(decoder == null) {
          decoder = com.datalogic.decode.BarcodeManager()
        }
        if(listener == null) {
          listener = ReadListener { decodeResult ->
            //Implement the callback method
            //Change the displayed text to the current received result
            mBarcodeText.text = decodeResult.text
            //Make user selection expand to select the entire barcode text
            mBarcodeText.setSelectAllOnFocus(true)
          }
        }
        promise.resolve(decoder!!.addReadListener(listener))
        } catch(e: Exception) {
          promise.reject(e)
        }
    }
}
