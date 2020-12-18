import { NativeModules, EventSubscriptionVendor } from 'react-native';

type BarcodeManagerType = EventSubscriptionVendor & {
  pressTrigger(): Promise<number>;
  releaseTrigger(): Promise<number>;
  addReadListener(): Promise<number>;
};

export const BarcodeManager: BarcodeManagerType = NativeModules.BarcodeManager;
