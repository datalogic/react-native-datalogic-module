package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.Callback

import com.datalogic.device.ErrorManager
import com.datalogic.device.DeviceException
import com.datalogic.device.input.KeyboardManager


class KeyboardManager(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
  override fun getName(): String {
    return "KeyboardManager"
  }

  @ReactMethod
  fun getAllAvailableTriggers(promise: Promise) {
    
  }
}
