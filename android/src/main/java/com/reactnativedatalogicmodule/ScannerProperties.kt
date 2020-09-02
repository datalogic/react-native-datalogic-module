package com.reactnativedatalogicmodule

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.bridge.Arguments

import com.datalogic.decode.BarcodeManager
import com.datalogic.decode.DecodeException
import com.datalogic.decode.configuration.IntentDeliveryMode
import com.datalogic.decode.configuration.IntentWedge
import com.datalogic.device.ErrorManager
import com.datalogic.device.configuration.BooleanProperty
import com.datalogic.device.configuration.CharacterProperty
import com.datalogic.device.configuration.ConfigException
import com.datalogic.device.configuration.EnumProperty
import com.datalogic.device.configuration.NumericProperty
import com.datalogic.device.configuration.TextProperty
import com.datalogic.device.configuration.PropertyGroup
import com.datalogic.decode.configuration.ScannerProperties



class ScannerProperties(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return "ScannerProperties"
  }

  @ReactMethod
  fun edit(promise: Promise) {
    val manager = BarcodeManager()
    val sp: ScannerProperties = ScannerProperties.edit(manager)
    //Make a map of maps, and manually put each of the classes in it
    val cfg: WritableMap = WritableNativeMap() //WritableNativeMap vs WritableMap?
    try {
      cfg.putMap("keyboardWedge", editHelper(sp.keyboardWedge.enable.get(), sp.keyboardWedge.isSupported()))
      cfg.putMap("aztec", editHelper(sp.aztec.enable.get(), sp.aztec.isSupported()))
      cfg.putMap("codabar", editHelper(sp.codabar.enable.get(), sp.codabar.isSupported()))
      cfg.putMap("code128", editHelper(sp.code128.enable.get(), sp.code128.isSupported()))
      cfg.putMap("code39", editHelper(sp.code39.enable.get(), sp.code39.isSupported()))
      cfg.putMap("code93", editHelper(sp.code93.enable.get(), sp.code93.isSupported()))
      cfg.putMap("composite", editHelper(sp.composite.enable.get(), sp.composite.isSupported()))
      cfg.putMap("datamatrix", editHelper(sp.datamatrix.enable.get(), sp.datamatrix.isSupported()))
      cfg.putMap("digimarc", editHelper(sp.digimarc.enable.get(), sp.digimarc.isSupported()))
      cfg.putMap("discrete25", editHelper(sp.discrete25.enable.get(), sp.discrete25.isSupported()))
      cfg.putMap("ean13", editHelper(sp.ean13.enable.get(), sp.ean13.isSupported()))
      cfg.putMap("ean8", editHelper(sp.ean8.enable.get(), sp.ean8.isSupported()))
      cfg.putMap("gs1DataBar_14", editHelper(sp.gs1DataBar_14.enable.get(), sp.gs1DataBar_14.isSupported()))
      cfg.putMap("gs1DataBar_Expanded", editHelper(sp.gs1DataBar_Expanded.enable.get(), sp.gs1DataBar_Expanded.isSupported()))
      cfg.putMap("gs1DataBar_Limited", editHelper(sp.gs1DataBar_Limited.enable.get(), sp.gs1DataBar_Limited.isSupported()))
      cfg.putMap("interleaved25", editHelper(sp.interleaved25.enable.get(), sp.interleaved25.isSupported()))
      cfg.putMap("matrix25", editHelper(sp.matrix25.enable.get(), sp.matrix25.isSupported()))
      cfg.putMap("maxicode", editHelper(sp.maxicode.enable.get(), sp.maxicode.isSupported()))
      cfg.putMap("microQr", editHelper(sp.microQr.enable.get(), sp.microQr.isSupported()))
      cfg.putMap("micropdf417", editHelper(sp.micropdf417.enable.get(), sp.micropdf417.isSupported()))
      cfg.putMap("msi", editHelper(sp.msi.enable.get(), sp.msi.isSupported()))
      cfg.putMap("p4State", editHelper(sp.p4State.enable.get(), sp.p4State.isSupported()))
      cfg.putMap("pAus", editHelper(sp.pAus.enable.get(), sp.pAus.isSupported()))
      cfg.putMap("pJap", editHelper(sp.pJap.enable.get(), sp.pJap.isSupported()))
      cfg.putMap("pKix", editHelper(sp.pKix.enable.get(), sp.pKix.isSupported()))
      cfg.putMap("pPlanet", editHelper(sp.pPlanet.enable.get(), sp.pPlanet.isSupported()))
      cfg.putMap("pPostnet", editHelper(sp.pPostnet.enable.get(), sp.pPostnet.isSupported()))
      cfg.putMap("pRM", editHelper(sp.pRM.enable.get(), sp.pRM.isSupported()))
      cfg.putMap("pdf417", editHelper(sp.pdf417.enable.get(), sp.pdf417.isSupported()))
      cfg.putMap("qrCode", editHelper(sp.qrCode.enable.get(), sp.qrCode.isSupported()))
      cfg.putMap("upcA", editHelper(sp.upcA.enable.get(), sp.upcA.isSupported()))
      cfg.putMap("upcE", editHelper(sp.upcE.enable.get(), sp.upcE.isSupported()))


      promise.resolve(cfg)

    } catch(e: Exception) {
      promise.reject(e)
    }
  }

  @ReactMethod
  fun store(map: ReadableMap, promise: Promise) {
    var successFlag = true

    val manager = BarcodeManager()
    val cfg: ScannerProperties = ScannerProperties.edit(manager)
    var all: ReadableMap? = null
    try {
      all = map

      storeHelper(cfg.keyboardWedge, cfg.keyboardWedge.enable, all, "keyboardWedge")
      // note: digimarc and dotcote are not supported on Memor 10
      storeHelper(cfg.aztec, cfg.aztec.enable, all, "aztec")
      storeHelper(cfg.codabar, cfg.codabar.enable, all, "codabar")
      storeHelper(cfg.code128, cfg.code128.enable, all, "code128")
      storeHelper(cfg.code39, cfg.code39.enable, all, "code39")
      storeHelper(cfg.code93, cfg.code93.enable, all, "code93")

    } catch(e: Exception) {
      promise.reject(e)
    }
    val ret: Int = cfg.store(manager, true)
    if(ret != ConfigException.SUCCESS) {
      successFlag = false
    }

    promise.resolve(successFlag)
  }

  private fun editHelper(enable: Boolean, supported: Boolean): WritableMap {
    val returnMap: WritableMap = Arguments.createMap()
    try {
      returnMap.putBoolean("enable", enable)
      returnMap.putBoolean("supported", supported)
    } catch(e: Exception) {
      return returnMap
    }
    return returnMap
  }

  private fun storeHelper(pg: PropertyGroup, en: BooleanProperty, all: ReadableMap, key: String): Boolean {
    var successFlag = true
    //var tempMap: ReadableMap? = all.getMap(key) //Problems with this line?
    var tempMap: ReadableMap? = null
    try {
      tempMap = all.getMap(key)
    } catch(e: Exception) {
      successFlag = false
      tempMap = null
    }
    if(tempMap != null) {
      if(pg.isSupported()) {
        en.set(tempMap.getBoolean("enable"))
      } else {
        successFlag = false
      }
    }
    return successFlag
  }
}
