import { NativeModules } from 'react-native';

type AutoScanTriggerType = {
  isAvailable(): Promise<boolean>;
  getSupportedRanges(): Promise<object>;
  getCurrentRange(): Promise<object>;
  setCurrentRange(rangeId: number): Promise<boolean>;
};

export const AutoScanTrigger: AutoScanTriggerType =
  NativeModules.AutoScanTrigger;
