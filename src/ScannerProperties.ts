import { NativeModules } from 'react-native';

type ScannerPropertiesType = {
  edit(): Promise<object>;
  store(map: object): Promise<boolean>;
};

export const ScannerProperties: ScannerPropertiesType =
  NativeModules.ScannerProperties;
