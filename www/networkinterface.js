var networkinterface = function() {
};

networkinterface.getIPAddress = function( success, fail ) {
    cordova.exec( success, fail, "networkinterface", "getIPAddress", [] );
};

networkinterface.getNetworkInfo = function( success, fail ) {
    cordova.exec( success, fail, "networkinterface", "getNetworkInfo", [] );
};

module.exports = networkinterface;
