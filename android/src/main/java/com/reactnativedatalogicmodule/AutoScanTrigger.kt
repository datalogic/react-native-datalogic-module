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

  //retuns list of supported ranges. List will be empty if device does not support Auto Scan
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
      //empty array of ranges
//      if (rangeList != null) {
//        resultJson.put("supportedRanges", new JSONArray (rangeList.toString())) //Not sure how to turn this into a JSONArray
//      } else {
//        resultJson.put("supportedRanges", JSONArray())
//      }

      //Think i need to return a JSONObject like the Cordova one does, since it won't know what
      //the ranges is
      promise.resolve(rangeList)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

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

      //Think i need to return a JSONObject like the Cordova one does, since it won't know what
      //the ranges is
      promise.resolve(currentRange)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

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
