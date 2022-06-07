import * as React from 'react';

import {
  StyleSheet,
  View,
  Text,
  NativeEventEmitter,
  Alert,
  Button,
} from 'react-native';
import {
  BarcodeManager,
  AutoScanTrigger,
  KeyboardManager,
  LedManager,
  ScannerProperties,
} from 'react-native-datalogic-module';

export default function App() {
  React.useEffect(() => {
    (async () => {
      try {
        console.log('BarcodeManager Tests\n');
        let bmTests = true;
        if ((await addReadListenerCall()) !== 0) bmTests = false;
        if (await pressTrigger() === false) bmTests = false;
        if (await releaseTrigger() === false) bmTests = false;
        console.log('BARCODEMANAGER TESTS RESULT: ' + bmTests + '\n');

        console.log('AutoScanTrigger Tests\n');
        let astTests = true;
        if ((await isAvailable()) === false) astTests = false;
        if ((await getSupportedRanges()) === false) astTests = false;
        if ((await setCurrentRange()) === false) astTests = false;
        if ((await getCurrentRange()) === false) astTests = false;
        console.log('AUTOSCANTRIGGER TESTS RESULT: ' + astTests + '\n');

        console.log('KeyboardManager Tests\n');
        let kmTests = true;
        if ((await getAllAvailableTriggers()) === null) kmTests = false;
        if ((await setAllAvailableTriggersTrue()) === null) kmTests = false;
        if ((await setAllAvailableTriggersFalse()) === null) kmTests = false;
        if ((await setTriggers()) === false) kmTests = false;
        console.log('KEYBOARDMANAGER TESTS RESULT: ' + kmTests + '\n');

        console.log('LedManager Tests\n');
        let lTests = true;
        if ((await setLed()) === false) lTests = false;
        console.log('LEDMANAGER TESTS RESULT: ' + lTests + '\n');

        console.log('ScannerProperties Tests\n');
        let spTests = true;
        if ((await editScanner()) === null) spTests = false;
        if ((await storeScanner()) === false) spTests = false;
        console.log('SCANNERPROPERTIES TESTS RESULT: ' + spTests + '\n');
      } catch (e) {
        console.error(e);
      }
    })();

    return () => {
      // unsubscribeOrRemoveEventHandler(); // 👍
    };
  }, []);

  const pressTrigger = async () => {
    try {
      let triggerReturn = await BarcodeManager.pressTrigger();
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

  const releaseTrigger = async () => {
    try {
      let triggerReturn = await BarcodeManager.releaseTrigger();
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

  const addReadListenerCall = async () => {
    try {
      const eventEmitter = new NativeEventEmitter(BarcodeManager);
      eventEmitter.addListener('successCallback', map => {
        Alert.alert(map.barcodeData);
      });
      let addListenerReturn = await BarcodeManager.addReadListener();

      console.log('Add Read Listener: ' + addListenerReturn);
      return addListenerReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const isAvailable = async () => {
    try {
      let isAvailableReturn = await AutoScanTrigger.isAvailable();
      console.log('Is Available: ' + isAvailableReturn);
      return isAvailableReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const getSupportedRanges = async () => {
    try {
      let supportedRangesReturn = await AutoScanTrigger.getSupportedRanges();
      console.log('Get Supported Ranges: ');
      console.log(supportedRangesReturn);
      return supportedRangesReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const getCurrentRange = async () => {
    try {
      let currentRangeReturn = await AutoScanTrigger.getCurrentRange();
      console.log('Get Current Range: ');
      console.log(currentRangeReturn);
      return currentRangeReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const setCurrentRange = async () => {
    try {
      let setRangeReturn = await AutoScanTrigger.setCurrentRange(2);
      console.log('Set Current Range: ' + setRangeReturn + '\n');
      return setRangeReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const getAllAvailableTriggers = async () => {
    try {
      let getTriggersReturn = await KeyboardManager.getAllAvailableTriggers();
      console.log(getTriggersReturn);
      return getTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const setAllAvailableTriggersTrue = async () => {
    try {
      let setTriggersReturn = await KeyboardManager.setAllAvailableTriggers(
        true
      );
      console.log('Set All Available Triggers True: ' + setTriggersReturn);
      return setTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const setAllAvailableTriggersFalse = async () => {
    try {
      let setTriggersReturn = await KeyboardManager.setAllAvailableTriggers(
        false
      );
      console.log('Set All Available Triggers False: ' + setTriggersReturn);
      return setTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const setTriggers = async () => {
    try {
      let triggersArray = [
        { enabled: false, id: 0, name: 'Left Trigger' },
        { enabled: true, id: 1, name: 'Right Trigger' },
        { enabled: false, id: 2, name: 'Pistol Trigger' },
      ];
      let setTriggersReturn = await KeyboardManager.setTriggers(triggersArray);
      console.log('Set Triggers: ' + setTriggersReturn + '\n');
      return setTriggersReturn;
    } catch (e) {
      console.error(e);
      return false;
    }
  };

  const setLed = async () => {
    try {
      let ledMap = { led: 'LED_GREEN_SPOT', enable: false };
      let setLedReturn = await LedManager.setLed(ledMap);
      console.log('Set LED: ' + setLedReturn + '\n');
      return setLedReturn;
    } catch (e) {
      console.error(e + '\n');
      return false;
    }
  };

  const editScanner = async () => {
    try {
      let editReturn = await ScannerProperties.edit();
      if (editReturn != null) console.log('Edit Scanner: true');
      else console.log('false');
      return editReturn;
    } catch (e) {
      console.error(e); //Error here because Digimarc is null?
      return false;
    }
  };

  const storeScanner = async () => {
    try {
      let storeMap = {
        keyboardWedge: { enable: false, supported: true },
        aztec: { enable: false },
      };
      let storeReturn = await ScannerProperties.store(storeMap);
      console.log(
        'Store Scanner: ' + JSON.stringify(storeReturn, undefined, 1) + '\n',
      );
      return storeReturn;
    } catch (e) {
      console.error(e);
      console.log('');
      return false;
    }
  };

  return (
    <View style={styles.container}>
      <Text>BarcodeManager Tests</Text>
      <Button
        onPress={pressTrigger}
        title="Press Trigger"
        color="#841584"
        accessibilityLabel="Try to use trigger"
      />
      <Button
        onPress={releaseTrigger}
        title="Release Trigger"
        color="#841584"
        accessibilityLabel="Try to use release trigger"
      />
      <Button
        onPress={addReadListenerCall}
        title="Add Read Listener"
        color="#841584"
        accessibilityLabel="Try to add Read Listener"
      />
      <Text>AutoScanTrigger Tests</Text>
      <Button
        onPress={isAvailable}
        title="Is Available"
        color="#841584"
        accessibilityLabel="Check if AutoScanTrigger is available"
      />
      <Button
        onPress={getSupportedRanges}
        title="Get Supported Ranges"
        color="#841584"
        accessibilityLabel="Retrieve the supported ranges of the auto scan trigger"
      />
      <Button
        onPress={getCurrentRange}
        title="Get Current Range"
        color="#841584"
        accessibilityLabel="Retrieve the current range of the auto scan trigger"
      />
      <Button
        onPress={setCurrentRange}
        title="Set Current Range"
        color="#841584"
        accessibilityLabel="Set the current range of the auto scan trigger"
      />
      <Text>KeyboardManager Tests</Text>
      <Button
        onPress={getAllAvailableTriggers}
        title="Get Available Triggers"
        color="#841584"
        accessibilityLabel="Get All Available Triggers"
      />
      <Button
        onPress={setAllAvailableTriggersTrue}
        title="Set Available Triggers True"
        color="#841584"
        accessibilityLabel="Set All Available Triggers"
      />
      <Button
        onPress={setAllAvailableTriggersFalse}
        title="Set Available Triggers False"
        color="#841584"
        accessibilityLabel="Set All Available Triggers"
      />
      <Button
        onPress={setTriggers}
        title="Set Triggers"
        color="#841584"
        accessibilityLabel="Set Triggers"
      />
      <Text>LedManager Tests</Text>
      <Button
        onPress={setLed}
        title="Set Led"
        color="#841584"
        accessibilityLabel="Set Led"
      />
      <Text>ScannerProperties Tests</Text>
      <Button
        onPress={editScanner}
        title="Edit Scanner"
        color="#841584"
        accessibilityLabel="Edit Scanner"
      />
      <Button
        onPress={storeScanner}
        title="Store Scanner"
        color="#841584"
        accessibilityLabel="Store Scanner"
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
