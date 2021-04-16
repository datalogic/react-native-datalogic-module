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
import BarcodeManager from "@datalogic/react-native-datalogic-module";
// ...
const result = await BarcodeManager.pressTrigger();
```

## Developer Instructions

Basic to-do's when updating and pushing to gitlab.

### First Time Instructions

Follow the [React Native CLI Quickstart](https://reactnative.dev/docs/environment-setup) instructions to install necessary software. Run 'yarn install' from the main project directory

### Editting the Module

* The kotlin files are located in android/src/main/java/com/reactnativedatalogicmodule
* If you wish to add a new class, make a new file in the same location and add it to the DatalogicModulePackage.kt
* Here is the React Native documentation on native modules [that are used](https://reactnative.dev/docs/native-modules-intro).

### Release Preparation

* Run 'yarn test android' in the command line from the main project
    All tests should pass except the AutoScanTrigger (only some Datalogic devices have the functionality)

* Install and use the [np tool](https://github.com/sindresorhus/np):

  ``` bash
  npm install --global np
  np
  ```
  