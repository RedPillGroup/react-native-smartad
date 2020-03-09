import { NativeModules, NativeEventEmitter } from 'react-native';

const RNSmartAdRewardedVideo = NativeModules.RNSmartAdRewardedVideo;
const SmartAdRewardedVideoEventEmitter = new NativeEventEmitter(RNSmartAdRewardedVideo);

const eventHandlers = {
  smartAdRewardedVideoNotReady: new Map(),
  smartAdA: new Map(),
  smartAdB: new Map(),
  smartAdC: new Map(),
  smartAdD: new Map(),
  smartAdE: new Map(),
  smartAdF: new Map(),
  smartAdG: new Map(),
  smartAdH: new Map(),
  smartAdI: new Map(),
  smartAdJ: new Map(),
  smartAdK: new Map(),
  smartAdL: new Map(),
}

const addEventListener = (type, handler) => {
  switch (type) {
    case 'smartAdRewardedVideoNotReady':
    case 'smartAdA':
    case 'smartAdB':
    case 'smartAdC':
    case 'smartAdD':
    case 'smartAdE':
    case 'smartAdF':
    case 'smartAdG':
    case 'smartAdH':
    case 'smartAdI':
    case 'smartAdJ':
    case 'smartAdK':
    case 'smartAdL':
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
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdA');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdB');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdC');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdD');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdE');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdF');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdG');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdH');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdI');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdJ');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdK');
  SmartAdRewardedVideoEventEmitter.removeAllListeners('smartAdL');
}

module.exports = {
  ...RNSmartAdRewardedVideo,
  initializeRewardedVideo: () => RNSmartAdRewardedVideo.initializeRewardedVideo(),
  showRewardedVideo: () => RNSmartAdRewardedVideo.showRewardedVideo(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};