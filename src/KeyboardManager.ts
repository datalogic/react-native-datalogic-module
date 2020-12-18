import { NativeModules } from 'react-native';

type KeyboardManagerType = {
  getAllAvailableTriggers(): Promise<object>;
  setAllAvailableTriggers(enable: boolean): Promise<boolean>;
  setTriggers(array: Array<object>): Promise<boolean>;
};

export const KeyboardManager: KeyboardManagerType =
  NativeModules.KeyboardManager;
