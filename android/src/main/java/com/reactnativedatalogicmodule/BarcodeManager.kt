package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.modules.core.DeviceEventManagerModule

import com.datalogic.decode.BarcodeManager
import com.datalogic.decode.DecodeException
import com.datalogic.decode.DecodeResult
import com.datalogic.decode.ReadListener
import com.datalogic.device.ErrorManager
import com.datalogic.decode.BarcodeID

import android.util.Log





class BarcodeManager(var reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  //private var reactContext: ReactApplicationContext? = null
  private var decoder: com.datalogic.decode.BarcodeManager? = null
  private var listener: ReadListener? = null

  //I think Kotlins built in primary constructor is good enough?
  //I'm not sure if I need to super the context

  override fun getName(): String {
      return "BarcodeManager"
  }

  fun sendEvent(reactContext: ReactApplicationContext, eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
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
    } catch (e: Exception) {
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
    } catch (e: Exception) {
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
        // Create an anonymous class.
        listener = ReadListener { decodeResult ->
          try {
            //Override so read info is put into JSON Object
            val barcodeObject: WritableMap = WritableNativeMap()
            barcodeObject.putString("barcodeData", decodeResult.getText())
            barcodeObject.putString("barcodeType", decodeResult.getBarcodeID().name)
            //Then invoke the success callback
            //successCallback.invoke(barcodeObject)

            sendEvent(reactContext, "successCallback", barcodeObject)

          } catch (e: Exception) {

          }
        }
      }
      //decoder!!.addReadListener(listener)
      promise.resolve(decoder!!.addReadListener(listener))
    } catch (e: Exception) {
      promise.reject(e)
    }
  }
}
