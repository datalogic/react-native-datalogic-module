/****************************************************/
// Filename: AutoScanTrigger.kt
// Overview: Contains the React Methods for the
// AutoScanTrigger class.
/****************************************************/
package com.reactnativedatalogicmodule

import com.datalogic.device.ErrorManager
import com.datalogic.device.DeviceException
import com.datalogic.device.input.KeyboardManager
import com.datalogic.device.input.Trigger
import com.datalogic.device.input.AutoScanTrigger.Range
import android.widget.TextView
import com.facebook.react.bridge.*
import org.json.JSONObject

class AutoScanTrigger(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var keyboardManager: KeyboardManager? = null

  init{
    keyboardManager = KeyboardManager()
  }

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
      val triggersList: List<Trigger> = keyboardManager!!.availableTriggers
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
      val triggersList: List<Trigger> = keyboardManager!!.availableTriggers
      var rangeList: List<com.datalogic.device.input.AutoScanTrigger.Range>? = null

      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          rangeList = t.supportedRanges
          break
        }
      }

      if(rangeList != null) {
        val rangeObjArray = WritableNativeArray();
        for(r in rangeList) {
          val map = WritableNativeMap();
          map.putInt("id", r.id);
          map.putString("name", r.name);
          rangeObjArray.pushMap(map);
        }

        promise.resolve(rangeObjArray)
      } else {
        promise.resolve(false)
      }

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
      val triggersList: List<Trigger> = keyboardManager!!.availableTriggers
      var currentRange: com.datalogic.device.input.AutoScanTrigger.Range? = null

      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          currentRange = t.currentRange
          break
        }
      }

      if(currentRange != null) {
        val currentRangeMap = WritableNativeMap();
        currentRangeMap.putInt("id", currentRange.id);
        currentRangeMap.putString("name", currentRange.name);

        promise.resolve(currentRangeMap)
      } else {
        promise.resolve(false)
      }

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
      var triggersList: List<Trigger> = keyboardManager!!.availableTriggers
      var rangeList: List<com.datalogic.device.input.AutoScanTrigger.Range>? = null
      for (t in triggersList) {
        if (t is com.datalogic.device.input.AutoScanTrigger) {
          rangeList = t.supportedRanges
          for(range in rangeList) {
            if(range.id == rangeId) {
              successFlag = t.setCurrentRange(range)
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
