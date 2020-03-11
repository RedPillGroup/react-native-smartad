import { NativeModules, NativeEventEmitter } from 'react-native';

const RNSmartAdRewardedVideo = NativeModules.Smartad;
const SmartAdRewardedVideoEventEmitter = new NativeEventEmitter(RNSmartAdRewardedVideo);

const eventHandlers = {
  smartAdRewardedVideoNotReady: new Map(),
  smartAdRewardedVideoAdLoaded: new Map(),
  smartAdRewardedVideoAdFailedToLoad: new Map(),
  smartAdRewardedVideoAdShown: new Map(),
  smartAdVideoAdFailedToShow: new Map(),
  smartAdRewardedVideoAdClosed: new Map(),
  smartAdRewardReceived: new Map(),
  smartAdRewardNotReceived: new Map(),
  smartAdRewardedVideoAdClicked: new Map(),
  smartAdRewardedVideoEvent: new Map(),
  smartAdRewardedVideoEndCardDisplayed: new Map(),
}

const addEventListener = (type, handler) => {
  switch (type) {
    case 'smartAdRewardedVideoNotReady':
    case 'smartAdRewardedVideoAdLoaded':
    case 'smartAdRewardedVideoAdFailedToLoad':
    case 'smartAdRewardedVideoAdShown':
    case 'smartAdVideoAdFailedToShow':
    case 'smartAdRewardedVideoAdClosed':
    case 'smartAdRewardReceived':
    case 'smartAdRewardNotReceived':
    case 'smartAdRewardedVideoAdClicked':
    case 'smartAdRewardedVideoEvent':
    case 'smartAdRewardedVideoEndCardDisplayed':
      eventHandlers[type].set(handler, SmartAdRewardedVideoEventEmitter.addListener(type, handler));
      break;
    default:
      console.log(`Event with type ${type} does not exist.`);
  }
};

const removeEventListener = (type, handler) => {
  if (!eventHandlers[type].has(handler)) {
    return;
  }
  eventHandlers[type].get(handler).remove();
  eventHandlers[type].delete(handler);
};

const removeAllListeners = () => {
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoNotReady');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdLoaded');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdFailedToLoad');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdShown');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdVideoAdFailedToShow');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdClosed');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardReceived');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardNotReceived');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdClicked');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoEvent');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoEndCardDisplayed');
}

const loadAndShowRewardedVideo = () => {
  console.log('here');
  const showAndDelete = () => {
    RNSmartAdRewardedVideo.showRewardedVideo();
    removeEventListener('smartAdRewardedVideoAdLoaded', showAndDelete);
    removeEventListener('smartAdRewardedVideoAdFailedToLoad', errorDelete);
  }
  const errorDelete = () => {
    removeEventListener('smartAdRewardedVideoAdLoaded', showAndDelete);
    removeEventListener('smartAdRewardedVideoAdFailedToLoad', errorDelete);
  }

  addEventListener('smartAdRewardedVideoAdLoaded', showAndDelete);
  addEventListener('smartAdRewardedVideoAdFailedToLoad', errorDelete);
  RNSmartAdRewardedVideo.loadRewardedVideoAd();
}

module.exports = {
  ...RNSmartAdRewardedVideo,
  initializeRewardedVideo: (siteId, pageId, formatId, target) => RNSmartAdRewardedVideo.initializeRewardedVideo(siteId, pageId, formatId, target),
  showRewardedVideo: () => RNSmartAdRewardedVideo.showRewardedVideo(),
  loadRewardedVideo: () => RNSmartAdRewardedVideo.loadRewardedVideoAd(),
  loadAndShowRewardedVideo,
  addEventListener,
  removeEventListener,
  removeAllListeners
};