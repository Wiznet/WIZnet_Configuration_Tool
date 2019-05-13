/**
 * 
 */

/**
 * @author Raphael
 *
 */
public class WIZ550WEB_Config {
	/**
	 * 
	 */

	private final int length = 133;

	class __network_info_common {
		byte[] mac = new byte[6];
		byte[] local_ip = new byte[4];
		byte[] gateway = new byte[4];
		byte[] subnet = new byte[4];
	}

	class __serial_info {
		int baud_rate;	// 각 Baud Rate별로 코드 부여?
		byte data_bits;	// 7, 8, 9 and more?
		byte parity;			// None, odd, even
		byte stop_bits;	// 1, 1.5, 2
		byte flow_control;	// None, RTS/CTS, XON/XOFF, DST/DTR, RTS Only for RS422/485
	}

	class __options {
		byte[] pw_setting = new byte[10];

		byte dhcp_use;

		byte dns_use;
		byte[] dns_server_ip = new byte[4];
		byte[] dns_domain_name = new byte[50];

		//byte serial_command;			// Serial Command Mode 사용 여부
		//byte[] serial_trigger = new byte[3];	// Serial Command Mode 진입을 위한 Trigger 코드
	}

	short packet_size;
	byte[] module_type = new byte[3]; // 모듈의 종류별로 코드를 부여하고 이를 사용한다.
	byte[] module_name = new byte[25];
	byte[] fw_ver = new byte[3];			// 10진수. Major Version . Minor Version . Maintenance Version 버전으로 나뉨
	__network_info_common network_info_common = null;
	__serial_info[] serial_info = new __serial_info[2];	// 여러개 시리얼을 사용할 경우
	__options options;

	public WIZ550WEB_Config() {
		int i;
		packet_size = length;

		module_type[0] = 0x01;
		module_type[1] = 0x02;
		module_type[2] = 0x00;

		network_info_common = new __network_info_common();

		for(i=0; i<serial_info.length; i++) {
			serial_info[i] = new __serial_info();
		}

		options = new __options();
	}

	public int getLength() {
		return length;
	}

	public void setData(byte[] data) {
		int index = 0, i;

		packet_size = (short) ((0xFF&data[index++]) + ((0xFF&data[index++]) << 8));
		module_type[0] = data[index++];
		module_type[1] = data[index++];
		module_type[2] = data[index++];
		for(i=0; i<25; i++)
			module_name[i] = data[index++];
		fw_ver[0] = data[index++];
		fw_ver[1] = data[index++];
		fw_ver[2] = data[index++];

		for(i=0; i<6; i++)
			network_info_common.mac[i] = data[index++];
		for(i=0; i<4; i++)
			network_info_common.local_ip[i] = data[index++];
		for(i=0; i<4; i++)
			network_info_common.gateway[i] = data[index++];
		for(i=0; i<4; i++)
			network_info_common.subnet[i] = data[index++];

		serial_info[0].baud_rate = (int) ((0xFF&data[index++]) + ((0xFF&data[index++]) << 8) + ((0xFF&data[index++]) << 16) + ((0xFF&data[index++]) << 24));
		serial_info[0].data_bits = data[index++];
		serial_info[0].parity = data[index++];
		serial_info[0].stop_bits = data[index++];
		serial_info[0].flow_control = data[index++];

		serial_info[1].baud_rate = (int) ((0xFF&data[index++]) + ((0xFF&data[index++]) << 8) + ((0xFF&data[index++]) << 16) + ((0xFF&data[index++]) << 24));
		serial_info[1].data_bits = data[index++];
		serial_info[1].parity = data[index++];
		serial_info[1].stop_bits = data[index++];
		serial_info[1].flow_control = data[index++];

		for(i=0; i<10; i++)
			options.pw_setting[i] = data[index++];
		options.dhcp_use = data[index++];
		options.dns_use = data[index++];
		options.dns_server_ip[0] = data[index++];
		options.dns_server_ip[1] = data[index++];
		options.dns_server_ip[2] = data[index++];
		options.dns_server_ip[3] = data[index++];
		for(i=0; i<50; i++)
			options.dns_domain_name[i] = data[index++];
	}

	public byte[] getData() {
		byte[] data = new byte[length];
		int index = 0, i;

		data[index++] = (byte) packet_size;
		data[index++] = (byte) (packet_size >> 8);
		data[index++] = module_type[0];
		data[index++] = module_type[1];
		data[index++] = module_type[2];
		for(i=0; i<25; i++) {
			if(i >= module_name.length)
				data[index++] = '\0';
			else
				data[index++] = module_name[i];
		}
		data[index++] = fw_ver[0];
		data[index++] = fw_ver[1];
		data[index++] = fw_ver[2];

		for(i=0; i<6; i++)
			data[index++] = network_info_common.mac[i];
		for(i=0; i<4; i++)
			data[index++] = network_info_common.local_ip[i];
		for(i=0; i<4; i++)
			data[index++] = network_info_common.gateway[i];
		for(i=0; i<4; i++)
			data[index++] = network_info_common.subnet[i];

		data[index++] = (byte) serial_info[0].baud_rate;
		data[index++] = (byte) (serial_info[0].baud_rate >> 8);
		data[index++] = (byte) (serial_info[0].baud_rate >> 16);
		data[index++] = (byte) (serial_info[0].baud_rate >> 24);
		data[index++] = serial_info[0].data_bits;
		data[index++] = serial_info[0].parity;
		data[index++] = serial_info[0].stop_bits;
		data[index++] = serial_info[0].flow_control;

		data[index++] = (byte) serial_info[1].baud_rate;
		data[index++] = (byte) (serial_info[1].baud_rate >> 8);
		data[index++] = (byte) (serial_info[1].baud_rate >> 16);
		data[index++] = (byte) (serial_info[1].baud_rate >> 24);
		data[index++] = serial_info[1].data_bits;
		data[index++] = serial_info[1].parity;
		data[index++] = serial_info[1].stop_bits;
		data[index++] = serial_info[1].flow_control;

		for(i=0; i<10; i++) {
			if(i >= options.pw_setting.length)
				data[index++] = '\0';
			else
				data[index++] = options.pw_setting[i];
		}
		data[index++] = options.dhcp_use;
		data[index++] = options.dns_use;
		data[index++] = options.dns_server_ip[0];
		data[index++] = options.dns_server_ip[1];
		data[index++] = options.dns_server_ip[2];
		data[index++] = options.dns_server_ip[3];
		for(i=0; i<50; i++) {
			if(i >= options.dns_domain_name.length)
				data[index++] = '\0';
			else
				data[index++] = options.dns_domain_name[i];
		}

		return data;
	}
}
