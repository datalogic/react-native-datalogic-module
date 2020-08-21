package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableNativeArray
import com.facebook.react.bridge.WritableNativeMap

import com.datalogic.device.ErrorManager
import com.datalogic.device.DeviceException
import com.datalogic.device.input.KeyboardManager
import com.datalogic.device.input.Trigger
import org.json.JSONArray





class KeyboardManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  var keyboardManager: KeyboardManager? = null

  override fun getName(): String {
    return "KeyboardManager"
  }

  @ReactMethod
  fun getAllAvailableTriggers(promise: Promise) {
    try {
      if(keyboardManager == null) {
        keyboardManager = KeyboardManager()
      }
      val triggersList: List<Trigger> = keyboardManager!!.getAvailableTriggers()
      val triggersObjArray = WritableNativeArray()
      for(t in triggersList) {
        //val trigger: Trigger = Trigger(t.getId(), t.getName(), t.isEnabled())
        val map = WritableNativeMap()
        map.putBoolean("enabled", t.isEnabled())
        map.putInt("id", t.getId())
        map.putString("name", t.getName())
        triggersObjArray.pushMap(map)
      }

      promise.resolve(triggersObjArray)
    } catch (e: Exception) {
      promise.reject(e)
    }
  }

  @ReactMethod
  fun setAllAvailableTriggers(enable: Boolean, promise: Promise) {
    var successFlag = true
    try {
      if(keyboardManager == null) {
        keyboardManager = KeyboardManager()
      }
      val triggersList: List<Trigger> = keyboardManager!!.getAvailableTriggers()
      for(t in triggersList) {
        t.setEnabled(enable)
        if(t.isEnabled() != enable) {
          successFlag = false
        }
      }
      promise.resolve(successFlag)

    } catch(e: Exception) {
      promise.reject(e)
    }
  }
}
