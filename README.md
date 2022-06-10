# react-native-datalogic-module

Library that exposes the Datalogic Android (Kotlin) SDK as a React Native Module.

## Installation

```sh
yarn add @datalogic/react-native-datalogic-module
                    OR
npm i @datalogic/react-native-datalogic-module
```

## Documentation

[Documentation is available here](https://datalogic.github.io/reactnative).

## Usage

```js
import { BarcodeManager } from "@datalogic/react-native-datalogic-module";
import { NativeEventEmitter, Alert } from 'react-native';
// ...
React.useEffect(() => {
    try {
      const eventEmitter = new NativeEventEmitter(BarcodeManager);
      eventEmitter.addListener('successCallback', (map) => {
        Alert.alert('Barcode Result', map.barcodeData + '\n' + map.barcodeType);
      });
      BarcodeManager.addReadListener();
    } catch(e) {
      console.error(e);
    }
  }, []);
```
  