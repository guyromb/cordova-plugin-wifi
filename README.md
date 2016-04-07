Wi-Fi Network Interface Information Plugin
=================

Wifi Network interface information plugin for Cordova that supports Android and iOS (=currently partially).
Using this plugin you can get information such as DNS, SSID, Gateway, IP Address, MAC Address etc.

## Command Line Install

    cordova plugin add https://github.com/guyromb/cordova-plugin-wifi.git

## Usage

The plugin creates the object `networkinterface` with the methods `getIPAddress(onSuccess, onError)` and `getNetworkInfo(onSuccess, onError)`.

Example:
```javascript
networkinterface.getIPAddress(
	function (ip) {
		alert(ip); 
	},
	function (err) {
		alert(err); 
	}
);
```

Ionic Example:

The library is automatically injected - it is ready to use after installation.
Factory service implementation example:
```javascript
.factory('wifiInfo', ['$q', function ($q) {
	var service = {
		info: {},
		getInfo: getInfo,
		isConnected: isConnected
	};
	return service;
	function getInfo() {
		var def = $q.defer();
		if(!window.networkinterface) {
			def.reject("cannot access wifi interface");
		}
		else {
			window.networkinterface.getNetworkInfo(function (data) {
				service.info = data;
				def.resolve(data);
			});
		}
		return def.promise;
	}
	function isConnected() {
		if(service.info)
			return (service.info.dns1 != '0.0.0.0');
		return null;
	}
}])
```
In the controller:
```javascript
wifiInfo.getInfo().then(
	function(info) {
		if(wifiInfo.isConnected()) {
			console.log(info);
		}
	},
	function(err) {
		console.log(err);
	}
);
```
