# react-native-datalogic-module

Library that exposes the Datalogic Android (Kotlin) SDK as a React Native Module.

## Installation

```sh
npm install react-native-datalogic-module
```

## Usage

```js
import BarcodeManager from "react-native-datalogic-module";

# In Command Line
yarn example android

// ...

const result = await BarcodeManager.pressTrigger();
```