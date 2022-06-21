/****************************************************/
// Filename: BarcodeManager.kt
// Overview: Contains the React Methods for the
// BarcodeManager class.
/****************************************************/
package com.reactnativedatalogicmodule

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.datalogic.decode.BarcodeManager
import com.datalogic.decode.ReadListener


class BarcodeManager(var reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var barcodeManager: BarcodeManager? = null
  private var listener: ReadListener? = null

  init {
    barcodeManager = BarcodeManager()
    // Create an anonymous class.
    listener = ReadListener { decodeResult ->
      try {
        //Override so read info is put into JSON Object
        val barcodeObject: WritableMap = WritableNativeMap()
        barcodeObject.putString("barcodeData", decodeResult.getText())
        barcodeObject.putString("barcodeType", decodeResult.getBarcodeID().name)
        sendEvent(reactContext, "successCallback", barcodeObject)
      } catch (e: Exception) {
        Log.i("Listener Exception: ", e.toString());
      }
    }
  }

  override fun getName(): String {
      return "BarcodeManager"
  }

  fun sendEvent(reactContext: ReactApplicationContext, eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }

  @ReactMethod
  fun addListener(eventName: String) {
    //Log.i("addListener: ", "is a stub function, use addReadListener()");
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    //Log.i("removeListeners: ",  "is a stub function, use release()");
  }

  /**********************************************************************
  * Purpose:        Simulates a trigger button press. The method does not always
  *                 immediately start a capture; instead it behaves like pressing
  *                 a physical scan button.
  * Precondition:   N/A
  * Postcondition:  A trigger press is simulated.
  ************************************************************************/
  @ReactMethod
  fun pressTrigger(promise: Promise) {
    try {
      promise.resolve(barcodeManager!!.pressTrigger())
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************
  * Purpose:        Simulates a trigger button release. The method does not always
  *                 immediately stop a capture; instead it behaves like releasing
  *                 a physical scan button.
  * Precondition:   N/A
  * Postcondition:  A trigger release is simulated.
  ************************************************************************/
  @ReactMethod
  fun releaseTrigger(promise: Promise) {
    try {
      promise.resolve(barcodeManager!!.releaseTrigger())
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************
  * Purpose:        Register to receive barcode data on each scan.
  * Precondition:   An event must have been created with the expected
  *                 callback.
  * Postcondition:  The event is connected to the BarcodeManager.
  ************************************************************************/
  @ReactMethod
  fun addReadListener(promise: Promise) {
    try {
      barcodeManager!!.removeReadListener(listener)
      promise.resolve(barcodeManager!!.addReadListener(listener))
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************
   * Purpose:        Removes all listeners from the BarcodeManager class
   * Precondition:   N/A
   * Postcondition:  All listeners are removed.
   ************************************************************************/
  @ReactMethod
  fun release(promise: Promise) {
    try {
      promise.resolve(barcodeManager!!.release())
    } catch (e: Exception) {
      Log.i("release Exception: ", e.toString());
      promise.reject(e)
    }
  }
}
