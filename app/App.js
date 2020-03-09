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
  StyleSheet,
  ScrollView,
  View,
  Text,
  StatusBar,
  TouchableOpacity
} from 'react-native';

import {
  Header,
  LearnMoreLinks,
  Colors,
  DebugInstructions,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

import SmartadModule from './RNSmartAdRewardedVideo';

const App: () => React$Node = () => {
  const [mess, setMess] = React.useState('...');

  React.useEffect(() => {
    SmartadModule.initializeRewardedVideo("app key", "user id");
  }, []);
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <TouchableOpacity>
          <Text>
            Click me !
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
