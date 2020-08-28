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



class ScannerProperties(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "ScannerProperties"
  }

  @ReactMethod
  fun edit(promise: Promise) {
    
  }
}
