/****************************************************/
// Filename: BarcodeManager.kt
// Overview: Contains the React Methods for the
// BarcodeManager class.
/****************************************************/
package com.reactnativedatalogicmodule

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

  private var decoder: BarcodeManager? = null

  init {
    decoder = BarcodeManager()
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
    print(eventName);
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    print(count);
    // Remove upstream listeners, stop unnecessary background tasks
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
      promise.resolve(decoder!!.pressTrigger())
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
      promise.resolve(decoder!!.releaseTrigger())
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
      // Create an anonymous class.
      var listener: ReadListener = ReadListener { decodeResult ->
        try {
          //Override so read info is put into JSON Object
          val barcodeObject: WritableMap = WritableNativeMap()
          barcodeObject.putString("barcodeData", decodeResult.getText())
          barcodeObject.putString("barcodeType", decodeResult.getBarcodeID().name)
          sendEvent(reactContext, "successCallback", barcodeObject)
        } catch (e: Exception) {
          promise.reject(e)
        }
      }
      promise.resolve(decoder!!.addReadListener(listener))
    } catch (e: Exception) {
      promise.reject(e)
    }
  }
}
