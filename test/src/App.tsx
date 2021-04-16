/****************************************************/
// Filename: App.tsx
// Overview: This file contains the test bench that
// can be run with 'yarn test android' from the main
// project directory.
/****************************************************/
import * as React from 'react';
import { StyleSheet, View, Text } from 'react-native';
import { Button, Alert, NativeEventEmitter } from 'react-native';
import {
  BarcodeManager,
  AutoScanTrigger,
  KeyboardManager,
  LedManager,
  ScannerProperties,
} from '../../src/index';

class App extends React.Component {
  async componentDidMount() {
    try {
      console.log('BarcodeManager Tests\n');
      var bmTests = true;
      if ((await this.addReadListenerCall()) !== 0) bmTests = false;
      if ((await this.pressTrigger()) === false) bmTests = false;
      if ((await this.releaseTrigger()) === false) bmTests = false;
      console.log('BARCODEMANAGER TESTS RESULT: ' + bmTests + '\n');

      console.log('AutoScanTrigger Tests\n');
      var astTests = true;
      if ((await this.isAvailable()) === false) astTests = false;
      if ((await this.getSupportedRanges()) === false) astTests = false;
      if ((await this.getCurrentRange()) === false) astTests = false;
      if ((await this.setCurrentRange()) === false) astTests = false;
      console.log('AUTOSCANTRIGGER TESTS RESULT: ' + astTests + '\n');

      console.log('KeyboardManager Tests\n');
      var kmTests = true;
      if ((await this.getAllAvailableTriggers()) === null) kmTests = false;
      if ((await this.setAllAvailableTriggersTrue()) === null) kmTests = false;
      if ((await this.setAllAvailableTriggersFalse()) === null) kmTests = false;
      if ((await this.setTriggers()) === false) kmTests = false;
      console.log('KEYBOARDMANAGER TESTS RESULT: ' + kmTests + '\n');

      console.log('LedManager Tests\n');
      var lTests = true;
      if ((await this.setLed()) === false) lTests = false;
      console.log('LEDMANAGER TESTS RESULT: ' + lTests + '\n');

      console.log('ScannerProperties Tests\n');
      var spTests = true;
      if ((await this.editScanner()) === null) spTests = false;
      if ((await this.storeScanner()) === false) spTests = false;
      console.log('SCANNERPROPERTIES TESTS RESULT: ' + spTests + '\n');
    } catch (e) {
      console.error(e);
    }
  }

  pressTrigger = async () => {
    try {
      var triggerReturn = await BarcodeManager.pressTrigger();
      if (triggerReturn === 0) {
        console.log('Press Trigger: Success');
        return true;
      } else {
        console.log('Press Trigger: Failure: ' + triggerReturn);
        return false;
      }
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  releaseTrigger = async () => {
    try {
      var triggerReturn = await BarcodeManager.releaseTrigger();
      if (triggerReturn === 0) {
        console.log('Release Trigger: Success\n');
        return true;
      } else {
        console.log('Release Trigger: Failure -> ' + triggerReturn + '\n');
        return false;
      }
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  addReadListenerCall = async () => {
    try {
      const eventEmitter = new NativeEventEmitter(BarcodeManager);
      eventEmitter.addListener('successCallback', (map: any) => {
        Alert.alert(map.barcodeData);
      });
      var addListenerReturn = await BarcodeManager.addReadListener();

      console.log('Add Read Listener: ' + addListenerReturn);
      return addListenerReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  isAvailable = async () => {
    try {
      var isAvailableReturn = await AutoScanTrigger.isAvailable();
      console.log('Is Available: ' + isAvailableReturn);
      return isAvailableReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  getSupportedRanges = async () => {
    try {
      var supportedRangesReturn = await AutoScanTrigger.getSupportedRanges();
      console.log('Get Supported Ranges: ' + supportedRangesReturn);
      return supportedRangesReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  getCurrentRange = async () => {
    try {
      var currentRangeReturn = await AutoScanTrigger.getCurrentRange();
      console.log('Get Current Range: ' + currentRangeReturn);
      return currentRangeReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  setCurrentRange = async () => {
    try {
      var setRangeReturn = await AutoScanTrigger.setCurrentRange(0);
      console.log('Set Current Range: ' + setRangeReturn + '\n');
      return setRangeReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  getAllAvailableTriggers = async () => {
    try {
      var getTriggersReturn = await KeyboardManager.getAllAvailableTriggers();
      console.log(getTriggersReturn);
      return getTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  setAllAvailableTriggersTrue = async () => {
    try {
      var setTriggersReturn = await KeyboardManager.setAllAvailableTriggers(
        true
      );
      console.log('Set All Available Triggers True: ' + setTriggersReturn);
      return setTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  setAllAvailableTriggersFalse = async () => {
    try {
      var setTriggersReturn = await KeyboardManager.setAllAvailableTriggers(
        false
      );
      console.log('Set All Available Triggers False: ' + setTriggersReturn);
      return setTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  setTriggers = async () => {
    try {
      var triggersArray = [
        { enabled: false, id: 0, name: 'Left Trigger' },
        { enabled: true, id: 1, name: 'Right Trigger' },
        { enabled: false, id: 2, name: 'Pistol Trigger' },
      ];
      var setTriggersReturn = await KeyboardManager.setTriggers(triggersArray);
      console.log('Set Triggers: ' + setTriggersReturn + '\n');
      return setTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  setLed = async () => {
    try {
      var ledMap = { led: 'LED_GREEN_SPOT', enable: false };
      var setLedReturn = await LedManager.setLed(ledMap);
      console.log('Set LED: ' + setLedReturn + '\n');
      return setLedReturn;
    } catch (e) {
      console.error(e + '\n');
      return false;
    }
  };

  editScanner = async () => {
    try {
      var editReturn = await ScannerProperties.edit();
      //console.log(JSON.stringify(editReturn, undefined, 1));
      if (editReturn != null) console.log('Edit Scanner: true');
      else console.log('false');
      return editReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  storeScanner = async () => {
    try {
      var storeMap = {
        keyboardWedge: { enable: false, supported: true },
        aztec: { enable: false },
      };
      var storeReturn = await ScannerProperties.store(storeMap);
      console.log(
        'Store Scanner: ' + JSON.stringify(storeReturn, undefined, 1) + '\n'
      );
      return storeReturn;
    } catch (e) {
      console.error(e);
      console.log('');
      return false;
    }
  };

  render() {
    return (
      <View style={styles.container}>
        <Text>BarcodeManager Tests</Text>
        <Button
          onPress={this.pressTrigger}
          title="Press Trigger"
          color="#841584"
          accessibilityLabel="Try to use trigger"
        />
        <Button
          onPress={this.releaseTrigger}
          title="Release Trigger"
          color="#841584"
          accessibilityLabel="Try to use release trigger"
        />
        <Button
          onPress={this.addReadListenerCall}
          title="Add Read Listener"
          color="#841584"
          accessibilityLabel="Try to add Read Listener"
        />
        <Text>AutoScanTrigger Tests</Text>
        <Button
          onPress={this.isAvailable}
          title="Is Available"
          color="#841584"
          accessibilityLabel="Check if AutoScanTrigger is available"
        />
        <Button
          onPress={this.getSupportedRanges}
          title="Get Supported Ranges"
          color="#841584"
          accessibilityLabel="Retrieve the supported ranges of the auto scan trigger"
        />
        <Button
          onPress={this.getCurrentRange}
          title="Get Current Range"
          color="#841584"
          accessibilityLabel="Retrieve the current range of the auto scan trigger"
        />
        <Button
          onPress={this.setCurrentRange}
          title="Set Current Range"
          color="#841584"
          accessibilityLabel="Set the current range of the auto scan trigger"
        />
        <Text>KeyboardManager Tests</Text>
        <Button
          onPress={this.getAllAvailableTriggers}
          title="Get Available Triggers"
          color="#841584"
          accessibilityLabel="Get All Available Triggers"
        />
        <Button
          onPress={this.setAllAvailableTriggersTrue}
          title="Set Available Triggers True"
          color="#841584"
          accessibilityLabel="Set All Available Triggers"
        />
        <Button
          onPress={this.setAllAvailableTriggersFalse}
          title="Set Available Triggers False"
          color="#841584"
          accessibilityLabel="Set All Available Triggers"
        />
        <Button
          onPress={this.setTriggers}
          title="Set Triggers"
          color="#841584"
          accessibilityLabel="Set Triggers"
        />
        <Text>LedManager Tests</Text>
        <Button
          onPress={this.setLed}
          title="Set Led"
          color="#841584"
          accessibilityLabel="Set Led"
        />
        <Text>ScannerProperties Tests</Text>
        <Button
          onPress={this.editScanner}
          title="Edit Scanner"
          color="#841584"
          accessibilityLabel="Edit Scanner"
        />
        <Button
          onPress={this.storeScanner}
          title="Store Scanner"
          color="#841584"
          accessibilityLabel="Store Scanner"
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});

export default App;
