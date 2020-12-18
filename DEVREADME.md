# Developer Instructions

Basic to-do's when updating and pushing to gitlab.

## First Time Instructions
Follow the React Native CLI Quickstart instructions to install necessary software -> https://reactnative.dev/docs/environment-setup
Run 'yarn install' from the main project directory

## Editting the Module
* The kotlin files are located in android/src/main/java/com/reactnativedatalogicmodule
* If you wish to add a new class, make a new file in the same location and add it to the DatalogicModulePackage.kt
* Here is the React Native documentation on native modules that I've used -> https://reactnative.dev/docs/native-modules-intro
 
## Release Preparation
Increase version number in package.json
Run 'yarn test android' in the command line from the main project
    All tests should pass except the AutoScanTrigger (only some Datalogic devices have the functionality)
Push upstream to GitLab and npm will update automatically