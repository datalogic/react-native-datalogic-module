/****************************************************/
// Filename: KeyboardManager.kt
// Overview: Contains the React Methods for the
// KeyboardManager class.
/****************************************************/
package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableNativeArray
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.bridge.ReadableArray
import com.datalogic.device.input.KeyboardManager
import com.datalogic.device.input.Trigger
import java.util.HashMap

class KeyboardManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  private var keyboardManager: KeyboardManager? = null

  init{
    keyboardManager = KeyboardManager()
  }

  override fun getName(): String {
    return "KeyboardManager"
  }

  /**********************************************************************
  * Purpose:        Gets all the available triggers on the device.
  * Precondition:   N/A
  * Postcondition:  Returns an object of objects, with one for each trigger.
  *                 They will have the following fields:
  *                 { "enabled":boolean, "id":number, "name":string }
  ************************************************************************/
  @ReactMethod
  fun getAllAvailableTriggers(promise: Promise) {
    try {
      val triggersList: List<Trigger> = keyboardManager!!.availableTriggers
      val triggersObjArray = WritableNativeArray()
      for(t in triggersList) {
        val map = WritableNativeMap()
        map.putBoolean("enabled", t.isEnabled)
        map.putInt("id", t.id)
        map.putString("name", t.name)
        triggersObjArray.pushMap(map)
      }

      promise.resolve(triggersObjArray)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************
  * Purpose:        Sets all the available triggers on the device.
  * Precondition:   Is passed in a boolean
  * Postcondition:  All available triggers are either set to true or false.
  ************************************************************************/
  @ReactMethod
  fun setAllAvailableTriggers(enable: Boolean, promise: Promise) {
    var successFlag = true
    try {
      val triggersList: List<Trigger> = keyboardManager!!.availableTriggers
      for(t in triggersList) {
        t.isEnabled = enable
        if(t.isEnabled != enable) {
          successFlag = false
        }
      }
      promise.resolve(successFlag)

    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  /**********************************************************************
  * Purpose:        Set one or more triggers on or off.
  * Precondition:   Is passed in a object of objects, with  each object
  *                 containing the following fields:
  *                 { enabled: boolean, id: number, name: string }
  * Postcondition:  Triggers are set to the passed in values, and a success
  *                 flag is returned.
  ************************************************************************/
  @ReactMethod
  fun setTriggers(array: ReadableArray, promise: Promise) {
    try {
      var successFlag = true
      val triggersMap: HashMap<Int, Boolean> = HashMap<Int, Boolean>()

      val arraySize: Int = array.size()
      for(i: Int in 0 until arraySize) {
        val id: Int = array.getMap(i).getInt("id")
        val enabled: Boolean = array.getMap(i).getBoolean("enabled")
        triggersMap.put(id, enabled)
      }

      val triggersList: List<Trigger> = keyboardManager!!.availableTriggers
      for(t in triggersList) {
        if( triggersMap.containsKey(t.id)) {
          val tEnabled: Boolean = triggersMap[t.id] ?: false
          t.isEnabled = tEnabled
          if(t.isEnabled != tEnabled) {
            successFlag = false
          }
        }
      }

        promise.resolve(successFlag)

    } catch (e: Exception) {
      promise.reject(e)
    }
  }
}
