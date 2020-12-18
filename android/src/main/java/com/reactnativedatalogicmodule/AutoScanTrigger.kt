/****************************************************/
// Filename: AutoScanTrigger.kt
// Overview: Contains the React Methods for the 
// AutoScanTrigger class. 
/****************************************************/
package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.Callback
import com.datalogic.device.ErrorManager
import com.datalogic.device.DeviceException
import com.datalogic.device.input.KeyboardManager
import com.datalogic.device.input.Trigger
import com.datalogic.device.input.AutoScanTrigger.Range
import android.widget.TextView
import org.json.JSONObject

class AutoScanTrigger(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  var keyboardManager: KeyboardManager? = null
  override fun getName(): String {
    return "AutoScanTrigger"
  }

  /**********************************************************************	
  * Purpose:        Determine if the auto scan feature is available on this device.
  * Precondition:   N/A
  * Postcondition:  Boolean is returned, with the status of the auto scan trigger.
  ************************************************************************/
  @ReactMethod
  fun isAvailable(promise: Promise) {
    var availableFlag = false
    try {
      if (keyboardManager == null)
        keyboardManager = KeyboardManager()
      val triggersList: List<Trigger> = keyboardManager!!.getAvailableTriggers()
      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          availableFlag = true
          break
        }
      }

      promise.resolve(availableFlag)

    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************	
  * Purpose:        Gets the supported ranges of the auto scan feature.
  * Precondition:   N/A
  * Postcondition:  Returns an object of objects. Each inner object will
  *                 have the following fields:
  *                 { "id":number, "name":string }
  *                 List will be null if the device does not support
  *                 auto scan feature.
  ************************************************************************/
  @ReactMethod
  fun getSupportedRanges(promise: Promise) {
    try {
      val triggersList: List<Trigger> = keyboardManager!!.getAvailableTriggers()
      var rangeList: List<com.datalogic.device.input.AutoScanTrigger.Range>? = null
      var resultJson: org.json.JSONObject? = null

      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          rangeList = (t as com.datalogic.device.input.AutoScanTrigger).getSupportedRanges()
          break
        }
      }
      promise.resolve(rangeList)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************	
  * Purpose:        Gets the current ranges of the device
  * Precondition:   N/A
  * Postcondition:  Returns an object containing the current range of the 
  *                 device. The object will have the following fields:
  *                 { "id":number, "name":string } 
  *                 If the auto scan feature is not supported, the return
  *                 value will be null.
  ************************************************************************/
  //retuns current range. List will be empty if device does not support Auto Scan
  @ReactMethod
  fun getCurrentRange(promise: Promise) {
    try {
      val triggersList: List<Trigger> = keyboardManager!!.getAvailableTriggers()
      var currentRange: com.datalogic.device.input.AutoScanTrigger.Range? = null
      var resultJson: org.json.JSONObject? = null

      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          currentRange = (t as com.datalogic.device.input.AutoScanTrigger).getCurrentRange()
          break
        }
      }
      promise.resolve(currentRange)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************	
  * Purpose:        Set the current range of the auto scan feature.
  * Precondition:   Is passed a number that should match one of the id
  *                 values returned by the getSupportedRanges function.
  * Postcondition:  Returns a boolean of the success flag.
  ************************************************************************/
  @ReactMethod
  fun setCurrentRange(rangeId: Int, promise: Promise) {
    var successFlag = false
    try {
      var triggersList: List<Trigger> = keyboardManager!!.getAvailableTriggers()
      var rangeList: List<com.datalogic.device.input.AutoScanTrigger.Range>? = null
      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          rangeList = (t as com.datalogic.device.input.AutoScanTrigger).getSupportedRanges()
          for(range in rangeList) {
            if(range.getId() == rangeId) {
              successFlag = (t as com.datalogic.device.input.AutoScanTrigger).setCurrentRange(range)
              break
            }
          }
          break
        }
      }
      if(rangeList == null) {
        //Device does not support AutoScan
        successFlag = false
      }

    } catch(e: Exception) {
      promise.reject(e)
    }
    promise.resolve(successFlag)
  }
}
