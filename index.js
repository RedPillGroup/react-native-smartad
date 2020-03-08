import { NativeModules } from 'react-native';

const { Smartad } = NativeModules.SmartAd;
SmartAd.addEvent('Birthday Party', '4 Privet Drive, Surrey');;

export default Smartad;
