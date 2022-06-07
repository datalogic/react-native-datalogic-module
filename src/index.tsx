import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-datalogic-module' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const TestModule = NativeModules.TestModule
  ? NativeModules.TestModule
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function multiply(a: number, b: number): Promise<number> {
  return TestModule.multiply(a, b);
}

export const BarcodeManager = NativeModules.BarcodeManager;
export const AutoScanTrigger = NativeModules.AutoScanTrigger;
export const KeyboardManager = NativeModules.KeyboardManager;
export const LedManager = NativeModules.LedManager;
export const ScannerProperties = NativeModules.ScannerProperties;
