import { NativeModules } from 'react-native';

type LedManagerType = {
  setLed(map: object): Promise<boolean>;
};

export const LedManager: LedManagerType = NativeModules.LedManager;
