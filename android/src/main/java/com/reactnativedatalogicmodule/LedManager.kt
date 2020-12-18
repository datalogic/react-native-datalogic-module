/****************************************************/
// Filename: LedManager.kt
// Overview: Contains the React Methods for the 
// LedManager class. 
/****************************************************/
package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.datalogic.device.ErrorManager
import com.datalogic.device.DeviceException
import java.util.HashMap
import com.datalogic.device.notification.LedManager
import com.datalogic.device.notification.Led



class LedManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  var ledManager: LedManager? = null

  override fun getName(): String {
    return "LedManager"
  }

  /**********************************************************************	
  * Purpose:        Set a singular LED true or false.
  * Precondition:   Is passed in a object of objects, with  each object
  *                 containing the following fields:
  *                 { led: string, enable: boolean }
  * Postcondition:  LED is set, and a success flag is returned. 
  ************************************************************************/
  @ReactMethod
  fun setLed(map: ReadableMap, promise: Promise) {
    try {
      var successFlag = true
      if(ledManager == null) {
        ledManager = LedManager()
      }

      var notificationLed: Led? = null
      var enable: Boolean? = null
      try { //Check that the string of the led passed in is viable
        var ledString: String = map!!.getString("led") ?: ""
        notificationLed = Led.valueOf(ledString)
        enable = map!!.getBoolean("enable")
      } catch (e: Exception) { //If not, return false
        promise.resolve(false)
      }
      if(enable != null && ledManager!!.setLed(notificationLed, enable) == DeviceException.SUCCESS)
        successFlag = true
      else
        successFlag = false

      promise.resolve(successFlag)

    } catch (e: Exception) {
      promise.reject(e)
    }
  }
}
