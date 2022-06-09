/****************************************************/
// Filename: DatalogicModulePackage.kt
// Overview: Combines all the classes into one
// native module.
/****************************************************/
package com.reactnativedatalogicmodule

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager

class DatalogicModulePackage : ReactPackage {
  override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
    return listOf(
      BarcodeManager(reactContext),
      AutoScanTrigger(reactContext),
      KeyboardManager(reactContext),
      LedManager(reactContext),
      ScannerProperties(reactContext)
    )
  }

  override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
    return emptyList()
  }
}
