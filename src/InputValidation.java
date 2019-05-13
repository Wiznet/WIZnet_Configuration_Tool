import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author Raphael
 *
 */
public class InputValidation {

	public boolean IpValid(String str) {
		if(Pattern.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\z", str)) {
			String[] str_array = str.split("\\.");

			if(str_array.length != 4)
				return false;

			short[] ip = new short[4];
			ip[0] = Short.parseShort(str_array[0], 10);
			ip[1] = Short.parseShort(str_array[1], 10);
			ip[2] = Short.parseShort(str_array[2], 10);
			ip[3] = Short.parseShort(str_array[3], 10);

			if(ip[0] < 0 || ip[0] > 255)
				return false;
			if(ip[1] < 0 || ip[1] > 255)
				return false;
			if(ip[2] < 0 || ip[2] > 255)
				return false;
			if(ip[3] < 0 || ip[3] > 255)
				return false;
		}
		else {
			return false;
		}

		return true;
	}

	public boolean MacValid(String str) {
		if(Pattern.matches("[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}:[0-9a-fA-F]{1,2}\\z", str)) {
			String[] str_array = str.split("\\:");

			if(str_array.length != 6)
				return false;

			short[] mac = new short[6];
			mac[0] = Short.parseShort(str_array[0], 16);
			mac[1] = Short.parseShort(str_array[1], 16);
			mac[2] = Short.parseShort(str_array[2], 16);
			mac[3] = Short.parseShort(str_array[3], 16);
			mac[4] = Short.parseShort(str_array[4], 16);
			mac[5] = Short.parseShort(str_array[5], 16);

			if(mac[0] < 0 || mac[0] > 255)
				return false;
			if(mac[1] < 0 || mac[1] > 255)
				return false;
			if(mac[2] < 0 || mac[2] > 255)
				return false;
			if(mac[3] < 0 || mac[3] > 255)
				return false;
			if(mac[4] < 0 || mac[4] > 255)
				return false;
			if(mac[5] < 0 || mac[5] > 255)
				return false;
		}
		else {
			return false;
		}

		return true;
	}

	public boolean PortValid(String str) {
		if(Pattern.matches("[0-9]{1,5}", str)) {
			int port;
			port = Integer.parseInt(str, 10);

			if(port < 0 || port > 65535)
				return false;
		}
		else {
			return false;
		}
		return true;
	}
}
