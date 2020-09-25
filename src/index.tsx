import { NativeEventEmitter, NativeModules } from 'react-native';

// type BarcodeManagerType = {
//   multiply(a: number, b: number): Promise<number>;
//   pressTrigger(): Promise<number>;
//   releaseTrigger(): Promise<number>;
//   addReadListener(): Promise<number>;
// };

// type AutoScanTriggerType = {
//   isAvailable(): Promise<boolean>;
// };

const BarcodeManager = NativeModules.BarcodeManager;
const AutoScanTrigger = NativeModules.AutoScanTrigger;
const KeyboardManager = NativeModules.KeyboardManager;
const LedManager = NativeModules.LedManager;
const ScannerProperties = NativeModules.ScannerProperties;

module.exports = {
  //emitter: new NativeEventEmitter(BarcodeManager),
  addReadListener(callback: any) {
    var emitter = new NativeEventEmitter(BarcodeManager);
    emitter.addListener('successCallback', (name: any) => callback(name));

    return BarcodeManager.addReadListener();
  },
  BarcodeManager,
  AutoScanTrigger,
  KeyboardManager,
  LedManager,
  ScannerProperties,
};

//export default BarcodeManager as BarcodeManagerType; //This one works
//export { BarcodeManager, AutoScanTrigger, KeyboardManager, LedManager, ScannerProperties }; //This works when the import has { } around it

//module.exports = NativeModules.BarcodeManager;
//module.exports = NativeModules.AutoScanTrigger;
