import java.util.Random;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class WIZnet_Header {
	final byte STX						= (byte)0xA5;

	final byte DISCOVERY_ALL			= (byte)0xA1;
	final byte DISCOVERY_PRODUCT_CODE	= (byte)0xA2;
	final byte DISCOVERY_MAC_ADDRESS	= (byte)0xA3;
	final byte DISCOVERY_ALIAS			= (byte)0xA4;
	final byte DISCOVERY_MIXED_COND		= (byte)0xA5;

	final byte FIRMWARE_UPLOAD_INIT		= (byte)0xD1;
	final byte FIRMWARE_UPLOAD_DONE		= (byte)0xD2;

	final byte DISCOVERY				= (byte)0xA0;
	final byte GET_INFO					= (byte)0xB0;
	final byte SET_INFO					= (byte)0xC0;
	final byte FIRMWARE_UPLOAD			= (byte)0xD0;
	final byte REMOTE_RESET				= (byte)0xE0;
	final byte FACTORY_RESET			= (byte)0xF0;

	final byte WIZNET_REQUEST			= (byte)0xAA;
	final byte WIZNET_REPLY				= (byte)0x55;

	public static class Header {
		byte stx;
		byte valid;
		byte unicast;
		byte[] op_code = new byte[2];
		short length;
	}

	public static class Discovery_All {
	}

	public static class Discovery_Product_Code {
		byte[] product_code = new byte[3];
		byte[] fw_version = new byte[3];
		byte option;
	}

	public static class Discovery_MAC_Address {
		byte[] start_mac_address = new byte[6];
		byte[] end_mac_address = new byte[6];
	}

	public static class Discovery_Alias {
		byte[] alias = new byte[32];
		byte option;
	}

	public static class Discovery_Mixed_Condition {
		byte[] product_code = new byte[3];
		byte[] fw_version = new byte[3];
		byte fw_version_option;
		byte[] start_mac_address = new byte[6];
		byte[] end_mac_address = new byte[6];
		byte[] alias = new byte[32];
		byte alias_option;
	}

	public static class Discovery_Reply {
		byte[] product_code = new byte[3];
		byte[] fw_version = new byte[3];
		byte[] mac_address = new byte[6];
	}

	public static class Get_Info {
		byte[] dst_mac_address = new byte[6];
	}

	public static class Get_Info_Reply {
		byte[] src_mac_address = new byte[6];
		byte[] system_info;
	}

	public static class Set_Info {
		byte[] dst_mac_address = new byte[6];
		byte set_pw_len;
		byte[] set_pw = new byte[16];
		byte[] system_info;
	}

	public static class Set_Info_Reply {
		byte[] src_mac_address = new byte[6];
		byte[] system_info;
	}

	public static class Firmware_Upload_Init {
		byte[] dst_mac_address = new byte[6];
		byte set_pw_len;
		byte[] set_pw = new byte[16];
		byte[] server_ip = new byte[4];
		short server_port;
		byte[] file_name = new byte[50];
	}

	public static class Firmware_Upload_Init_Reply {
		byte[] src_mac_address = new byte[6];
	}

	public static class Firmware_Upload_Done_Reply {
		byte[] src_mac_address = new byte[6];
	}

	public static class Reset {
		byte[] dst_mac_address = new byte[6];
		byte set_pw_len;
		byte[] set_pw = new byte[16];
	}

	public static class Reset_Reply {
		byte[] src_mac_address = new byte[6];
	}

	public static class Factory_Reset {
		byte[] dst_mac_address = new byte[6];
		byte set_pw_len;
		byte[] set_pw = new byte[16];
	}

	public static class Factory_Reset_Reply {
		byte[] src_mac_address = new byte[6];
	}

	/**
	* Reallocates an array with a new size, and copies the contents
	* of the old array to the new array.
	* @param oldArray  the old array, to be reallocated.
	* @param newSize   the new array size.
	* @return          A new array with the same contents.
	*/
	private Object resizeArray (Object oldArray, int newSize) {
		int oldSize = java.lang.reflect.Array.getLength(oldArray);
		Class<?> elementType = oldArray.getClass().getComponentType();
		Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
		int preserveLength = Math.min(oldSize, newSize);
		if (preserveLength > 0)
			System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
		return newArray;
	}

	private int make_header(byte[] buffer, Header header) {
		int index = 0;

		buffer[index++] = header.stx;
		buffer[index++] = header.valid;
		buffer[index++] = header.unicast;
		buffer[index++] = header.op_code[0];
		buffer[index++] = header.op_code[1];
		buffer[index++]  = (byte) header.length;
		buffer[index++] += (byte) (header.length >> 8);

		return index;
	}

	public boolean parse_header(byte[] buffer, Header header) {
		int index = 0;

		header.stx = buffer[index++];
		header.valid = buffer[index++];
		header.unicast = buffer[index++];
		header.op_code[0] = buffer[index++];
		header.op_code[1] = buffer[index++];
		header.length  = (short) (0x00FF & buffer[index++]);
		header.length += (short) (0x00FF & buffer[index++]<<8);

		if(header.stx != STX)
			return false;

		System.arraycopy(buffer, 7, buffer, 0, header.length);

		return true;
	}

	public void set_unicast(boolean set, byte[] buffer) {
		if(set)
			buffer[2] = 1;
		else
			buffer[2] = 0;
	}

	public void encrypt(byte key, byte[] buffer, short len) {
		short i;
		for(i=7; i<len; i++) {
			buffer[i] ^= key;
		}
	}

	public void decrypt(byte key, byte[] buffer, short len) {
		short i;
		for(i=0; i<len; i++) {
			buffer[i] ^= key;
		}
	}

	public byte[] discovery(byte op) {
		Random random = new Random();
		Header header = new Header();
		byte[] data = new byte[1024];
		int index = 0;

		header.stx = STX;
		header.valid = (byte)(0x80 + random.nextInt(0x7F));
		header.unicast = 0;
		header.length = 0;

		switch(op) {
		case DISCOVERY_ALL:
			header.op_code[0] = DISCOVERY_ALL;
			header.op_code[1] = WIZNET_REQUEST;
			header.length = 0;

			index = make_header(data, header);
			break;

		case DISCOVERY_PRODUCT_CODE:
			Discovery_Product_Code product_code = new Discovery_Product_Code();
			
			header.op_code[0] = DISCOVERY_PRODUCT_CODE;
			header.op_code[1] = WIZNET_REQUEST;
			header.length = 7;

			product_code.product_code[0] = 0;
			product_code.product_code[1] = 0;
			product_code.product_code[2] = 0;
			
			product_code.fw_version[0] = 0;
			product_code.fw_version[1] = 0;
			product_code.fw_version[2] = 0;
			
			product_code.option = 0;
			
			index = make_header(data, header);
			System.arraycopy(product_code.product_code, 0, data, index, 3);
			index += 3;
			System.arraycopy(product_code.fw_version, 0, data, index, 3);
			index += 3;
			data[index++] = product_code.option;
			break;

		case DISCOVERY_MAC_ADDRESS:
			Discovery_MAC_Address mac_address = new Discovery_MAC_Address();

			header.op_code[0] = DISCOVERY_MAC_ADDRESS;
			header.op_code[1] = WIZNET_REQUEST;
			header.length = 12;

			mac_address.start_mac_address[0] = (byte)0x00;
			mac_address.start_mac_address[1] = (byte)0x00;
			mac_address.start_mac_address[2] = (byte)0x00;
			mac_address.start_mac_address[3] = (byte)0x00;
			mac_address.start_mac_address[4] = (byte)0x00;
			mac_address.start_mac_address[5] = (byte)0x00;

			mac_address.end_mac_address[0] = (byte)0xFF;
			mac_address.end_mac_address[1] = (byte)0xFF;
			mac_address.end_mac_address[2] = (byte)0xFF;
			mac_address.end_mac_address[3] = (byte)0xFF;
			mac_address.end_mac_address[4] = (byte)0xFF;
			mac_address.end_mac_address[5] = (byte)0xFF;

			index = make_header(data, header);
			System.arraycopy(mac_address.start_mac_address, 0, data, index, 6);
			index += 6;
			System.arraycopy(mac_address.end_mac_address, 0, data, index, 6);
			index += 6;
			break;

		case DISCOVERY_ALIAS:
			Discovery_Alias alias = new Discovery_Alias();
			
			header.op_code[0] = DISCOVERY_ALIAS;
			header.op_code[1] = WIZNET_REQUEST;
			header.length = 33;

			System.arraycopy("WIZ550S2E".getBytes(), 0, alias.alias, 0, 9);
			alias.option = 0;

			index = make_header(data, header);
			System.arraycopy(alias.alias, 0, data, index, 32);
			index += 32;
			data[index++] = alias.option;
			break;

		case DISCOVERY_MIXED_COND:
			header.op_code[0] = DISCOVERY_MIXED_COND;
			header.op_code[1] = WIZNET_REQUEST;
			header.length = 52;
			break;
		default:
			return null;
		}

		data = (byte[])resizeArray(data, index);
		encrypt((byte)(header.valid & 0x7F), data, (short)data.length);
		return data;
	}

	public byte[] get_info(byte[] dst_mac_address) {
		Random random = new Random();
		Header header = new Header();
		Get_Info get_info = new Get_Info();
		byte[] data = new byte[1024];
		int index = 0;

		header.stx = STX;
		header.valid = (byte)(0x80 + random.nextInt(0x7F));
		header.unicast = 0;
		header.op_code[0] = GET_INFO;
		header.op_code[1] = WIZNET_REQUEST;
		header.length = 6;
		System.arraycopy(dst_mac_address, 0, get_info.dst_mac_address, 0, 6);

		index = make_header(data, header);
		System.arraycopy(get_info.dst_mac_address, 0, data, index, 6);
		index += 6;

		data = (byte[])resizeArray(data, index);
		encrypt((byte)(header.valid & 0x7F), data, (short)data.length);
		return data;
	}

	public byte[] set_info(byte[] dst_mac_address, byte[] s2e_packet, String set_pw) {
		Random random = new Random();
		Header header = new Header();
		Set_Info set_info = new Set_Info();
		byte[] data = new byte[1024];
		int index = 0;

		header.stx = STX;
		header.valid = (byte)(0x80 + random.nextInt(0x7F));
		header.unicast = 0;
		header.op_code[0] = SET_INFO;
		header.op_code[1] = WIZNET_REQUEST;
		header.length = (short) (23 + s2e_packet.length);
		System.arraycopy(dst_mac_address, 0, set_info.dst_mac_address, 0, 6);
		set_info.set_pw_len = (byte) set_pw.length();
		System.arraycopy(set_pw.getBytes(), 0, set_info.set_pw, 0, set_pw.length());
		set_info.system_info = s2e_packet;

		index = make_header(data, header);
		System.arraycopy(set_info.dst_mac_address, 0, data, index, 6);
		index += 6;
		data[index++] = set_info.set_pw_len;
		System.arraycopy(set_info.set_pw, 0, data, index, 16);
		index += 16;
		System.arraycopy(set_info.system_info, 0, data, index, set_info.system_info.length);
		index += set_info.system_info.length;

		data = (byte[])resizeArray(data, index);
		encrypt((byte)(header.valid & 0x7F), data, (short)data.length);
		return data;
	}

	public byte[] firmware_upload(byte[] dst_mac_address, byte[] serverIp, short serverPort, String fileName, String set_pw) {
		Random random = new Random();
		Header header = new Header();
		Firmware_Upload_Init firmware_upload_init = new Firmware_Upload_Init();
		byte[] data = new byte[1024];
		int index = 0;

		header.stx = STX;
		header.valid = (byte)(0x80 + random.nextInt(0x7F));
		header.unicast = 0;
		header.op_code[0] = FIRMWARE_UPLOAD_INIT;
		header.op_code[1] = WIZNET_REQUEST;
		header.length = 79;
		System.arraycopy(dst_mac_address, 0, firmware_upload_init.dst_mac_address, 0, 6);
		firmware_upload_init.set_pw_len = (byte) set_pw.length();
		System.arraycopy(set_pw.getBytes(), 0, firmware_upload_init.set_pw, 0, set_pw.length());
		System.arraycopy(serverIp, 0, firmware_upload_init.server_ip, 0, 4);
		firmware_upload_init.server_port = serverPort;
		System.arraycopy(fileName.getBytes(), 0, firmware_upload_init.file_name, 0, fileName.length());

		index = make_header(data, header);
		System.arraycopy(firmware_upload_init.dst_mac_address, 0, data, index, 6);
		index += 6;
		data[index++] = firmware_upload_init.set_pw_len;
		System.arraycopy(firmware_upload_init.set_pw, 0, data, index, 16);
		index += 16;
		System.arraycopy(firmware_upload_init.server_ip, 0, data, index, 4);
		index += 4;
		data[index++] = (byte) firmware_upload_init.server_port;
		data[index++] = (byte) (firmware_upload_init.server_port >> 8);
		System.arraycopy(firmware_upload_init.file_name, 0, data, index, 50);
		index += 50;

		data = (byte[])resizeArray(data, index);
		encrypt((byte)(header.valid & 0x7F), data, (short)data.length);
		return data;
	}

	public byte[] reset(byte[] dst_mac_address, String set_pw) {
		Random random = new Random();
		Header header = new Header();
		Reset reset = new Reset();
		byte[] data = new byte[1024];
		int index = 0;

		header.stx = STX;
		header.valid = (byte)(0x80 + random.nextInt(0x7F));
		header.unicast = 0;
		header.op_code[0] = REMOTE_RESET;
		header.op_code[1] = WIZNET_REQUEST;
		header.length = 23;
		System.arraycopy(dst_mac_address, 0, reset.dst_mac_address, 0, 6);
		reset.set_pw_len = (byte) set_pw.length();
		System.arraycopy(set_pw.getBytes(), 0, reset.set_pw, 0, set_pw.length());

		index = make_header(data, header);
		System.arraycopy(reset.dst_mac_address, 0, data, index, 6);
		index += 6;
		data[index++] = reset.set_pw_len;
		System.arraycopy(reset.set_pw, 0, data, index, 16);
		index += 16;

		data = (byte[])resizeArray(data, index);
		encrypt((byte)(header.valid & 0x7F), data, (short)data.length);
		return data;
	}

	public byte[] factory_reset(byte[] dst_mac_address, String set_pw) {
		Random random = new Random();
		Header header = new Header();
		Factory_Reset factory_reset = new Factory_Reset();
		byte[] data = new byte[1024];
		int index = 0;

		header.stx = STX;
		header.valid = (byte)(0x80 + random.nextInt(0x7F));
		header.unicast = 0;
		header.op_code[0] = FACTORY_RESET;
		header.op_code[1] = WIZNET_REQUEST;
		header.length = 23;
		System.arraycopy(dst_mac_address, 0, factory_reset.dst_mac_address, 0, 6);
		factory_reset.set_pw_len = (byte) set_pw.length();
		System.arraycopy(set_pw.getBytes(), 0, factory_reset.set_pw, 0, set_pw.length());

		index = make_header(data, header);
		System.arraycopy(factory_reset.dst_mac_address, 0, data, index, 6);
		index += 6;
		data[index++] = factory_reset.set_pw_len;
		System.arraycopy(factory_reset.set_pw, 0, data, index, 16);
		index += 16;

		data = (byte[])resizeArray(data, index);
		encrypt((byte)(header.valid & 0x7F), data, (short)data.length);
		return data;
	}
}
