/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {
  SafeAreaView,
  Text,
  StatusBar,
  TouchableOpacity
} from 'react-native';

import SmartadModule from './RNSmartAdRewardedVideo';

const App: () => React$Node = () => {
  const [mess, setMess] = React.useState('...');

  React.useEffect(() => {
    SmartadModule.addEventListener('smartAdRewardedVideoNotReady',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoNotReady"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoAdLoaded',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoAdLoaded"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoAdFailedToLoad',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoAdFailedToLoad"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoAdShown',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoAdShown"');
      }
    );
    SmartadModule.addEventListener('smartAdVideoAdFailedToShow',
      () => {
        console.log('Got something from events: "smartAdVideoAdFailedToShow"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoAdClosed',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoAdClosed"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardReceived',
      () => {
        console.log('Got something from events: "smartAdRewardReceived"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardNotReceived',
      () => {
        console.log('Got something from events: "smartAdRewardNotReceived"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoAdClicked',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoAdClicked"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoEvent',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoEvent"');
      }
    );
    SmartadModule.addEventListener('smartAdRewardedVideoEndCardDisplayed',
      () => {
        console.log('Got something from events: "smartAdRewardedVideoEndCardDisplayed"');
      }
    );
  }, []);
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <TouchableOpacity onPress={async () => { await SmartadModule.initializeRewardedVideo(339683, "1188835", 87843, null);}}>
          <Text>
            Initialize !
          </Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={async () => { await SmartadModule.loadRewardedVideoAd();}}>
          <Text>
            Load video
          </Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={() => SmartadModule.showRewardedVideo()}>
          <Text>
            Show video
          </Text>
        </TouchableOpacity>
        <Text>
          {mess}
        </Text>
      </SafeAreaView>
    </>
  );
};

export default App;
