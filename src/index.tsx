import { NativeModules } from 'react-native';

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

//export default BarcodeManager as BarcodeManagerType; //This one works
export { BarcodeManager, AutoScanTrigger }; //This works when the import has { } around it

//module.exports = NativeModules.BarcodeManager;
//module.exports = NativeModules.AutoScanTrigger;
