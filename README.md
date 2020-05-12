# react-native-smartad

Smart Ad Server SDK React Native bridge based on sample [ios](https://github.com/smartadserver/smart-display-ios-samples) and [android](https://github.com/smartadserver/smart-display-android-samples)

Supports Rewarded Video ad unit.

## Getting started

`$ yarn add react-native-smartad`

## Usage
```javascript
import Smartad from 'react-native-smartad';

await Smartad.initializeRewardedVideo(000000, "0000000", 00000, null)
await Smartad.loadRewardedVideo()
await Smartad.loadAndShowRewardedVideo()

Smartad;
```
