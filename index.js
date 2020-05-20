import { NativeModules, NativeEventEmitter } from 'react-native';

class SmartadModule {
  RNSmartAdRewardedVideo = NativeModules.Smartad;
  SmartAdRewardedVideoEventEmitter = new NativeEventEmitter(this.RNSmartAdRewardedVideo);
  eventHandlers = {
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
    kSmartAdCustomAdvertiser:new Map()
  };

  addEventListener(type, handler) {
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
      case 'kSmartAdVignette':
      case 'kSmartAdCustomAdvertiser':
      case 'smartAdRewardedVideoEndCardDisplayed':
        this.eventHandlers[type].set(handler, this.SmartAdRewardedVideoEventEmitter.addListener(type, handler));
        break;
      default:
        console.log(`Event with type ${type} does not exist.`);
    }
  };

  removeEventListener (type, handler) {
    if (!this.eventHandlers[type].has(handler)) {
      return;
    }
    this.eventHandlers[type].get(handler).remove();
    this.eventHandlers[type].delete(handler);
  };

  removeAllListeners() {
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoNotReady');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdLoaded');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdFailedToLoad');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdShown');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdVideoAdFailedToShow');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdClosed');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardReceived');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardNotReceived');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoAdClicked');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoEvent');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('kSmartAdVignette');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('kSmartAdCustomAdvertiser');
    this.SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdRewardedVideoEndCardDisplayed');
  }

  loadAndShowRewardedVideo(id) {
    const showAndDelete = () => {
      this.RNSmartAdRewardedVideo.showRewardedVideo();
      removeEventListener('smartAdRewardedVideoAdLoaded', showAndDelete);
      removeEventListener('smartAdRewardedVideoAdFailedToLoad', errorDelete);
    }
    const errorDelete = () => {
      removeEventListener('smartAdRewardedVideoAdLoaded', showAndDelete);
      removeEventListener('smartAdRewardedVideoAdFailedToLoad', errorDelete);
    }

    addEventListener('smartAdRewardedVideoAdLoaded', showAndDelete);
    addEventListener('smartAdRewardedVideoAdFailedToLoad', errorDelete);
    this.RNSmartAdRewardedVideo.loadRewardedVideoAd();
  }
  initializeRewardedVideo(siteId, pageId, formatId, target) {
    this.RNSmartAdRewardedVideo.initializeRewardedVideo(siteId, pageId, formatId, target)
  } 
  showRewardedVideo () {
    this.RNSmartAdRewardedVideo.showRewardedVideo();
  } 
  loadRewardedVideo(i) {
    console.log("loadRewardedVideo :",i);
    this.RNSmartAdRewardedVideo.loadRewardedVideoAd();
  }
}


export default SmartadModule;