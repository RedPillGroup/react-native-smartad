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
  kSmartAdVignette:new Map(),
}

const addEventListener = (type, handler) => {
  if(Object.keys(eventHandlers).includes(type)){
    eventHandlers[type].set(handler, SmartAdRewardedVideoEventEmitter.addListener(type, handler));
  } else {
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
  for(const key in eventHandlers) {
    SmartAdRewardedVideoEventEmitter.removeAllListeners(key);  
  }
}

const loadAndShowRewardedVideo = (securedTransactionToken=null) => {
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
  RNSmartAdRewardedVideo.loadRewardedVideoAd(securedTransactionToken);
}

module.exports = {
  ...RNSmartAdRewardedVideo,
  initialize: (siteId) => RNSmartAdRewardedVideo.initialize(siteId),
  initializeRewardedVideo: (siteId, pageId, formatId, target) => RNSmartAdRewardedVideo.initializeRewardedVideo(siteId, pageId, formatId, target),
  showRewardedVideo: () => RNSmartAdRewardedVideo.showRewardedVideo(),
  loadRewardedVideo: (securedTransactionToken=null) => RNSmartAdRewardedVideo.loadRewardedVideoAd(securedTransactionToken),
  loadAndShowRewardedVideo,
  addEventListener,
  removeEventListener,
  removeAllListeners
};