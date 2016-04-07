package net.gsrweb.plugin.networkinterface;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.DhcpInfo;
import android.provider.Settings;

public class networkinterface extends CordovaPlugin {
	public static final String GET_IP_ADDRESS="getIPAddress";
	public static final String GET_NETWORK_INFO="getNetworkInfo";

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		try {
			if (GET_IP_ADDRESS.equals(action)) {
				String ip = getIPAddress();
				String fail = "0.0.0.0";
				if (ip.equals(fail)) {
					callbackContext.error("No valid IP address identified");
					return false;
				}
				callbackContext.success(ip);
				return true;
			}
			else if(GET_NETWORK_INFO.equals(action)) {
				JSONObject networkInfo = getNetworkInfo();
				callbackContext.success(networkInfo);
				return true;
			}
			
			callbackContext.error("Error no such method '" + action + "'");
			return false;
			
		} catch(Exception e) {
			callbackContext.error("Error while retrieving network information. " + e.getMessage());
			return false;
		}
	}
	
	private String getIPAddress() {
		WifiManager wifiManager = (WifiManager) cordova.getActivity().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ip = wifiInfo.getIpAddress();

		String ipString = String.format(
			"%d.%d.%d.%d",
			(ip & 0xff),
			(ip >> 8 & 0xff),
			(ip >> 16 & 0xff),
			(ip >> 24 & 0xff)
		);

		return ipString;
	}
	
	private String intToIp(int intip) {
		String ipString = String.format(
			"%d.%d.%d.%d",
			(intip & 0xff),
			(intip >> 8 & 0xff),
			(intip >> 16 & 0xff),
			(intip >> 24 & 0xff)
		);
				
		return ipString;
	}
	
	private JSONObject getNetworkInfo() {
		WifiManager wifiManager = (WifiManager) cordova.getActivity().getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
		
		JSONObject dhcpJson = new JSONObject();
		
		try { 
            dhcpJson.put("staticIp", intToIp(dhcpInfo.ipAddress));
            dhcpJson.put("wifiAddress", intToIp(wifiInfo.getIpAddress()));
            dhcpJson.put("netmask", intToIp(dhcpInfo.netmask));
            dhcpJson.put("gateway", intToIp(dhcpInfo.gateway));
            dhcpJson.put("dns1", intToIp(dhcpInfo.dns1));
            dhcpJson.put("dns2", intToIp(dhcpInfo.dns2));
            dhcpJson.put("serverAddress", intToIp(dhcpInfo.serverAddress));
            dhcpJson.put("mac", String.valueOf(wifiInfo.getMacAddress()));
            dhcpJson.put("ssid", wifiInfo.getSSID());
            int useStatic = Settings.System.getInt(cordova.getActivity().getContentResolver(), Settings.System.WIFI_USE_STATIC_IP);
            dhcpJson.put("use_static_ip", useStatic == 1 ? "Static" : "Auto");
		} catch (JSONException ex) {
            Log.e("getDHCPInfo Error", "JSON Error:" + ex.getMessage());
        } finally {
            return dhcpJson;
        }
	}
}
