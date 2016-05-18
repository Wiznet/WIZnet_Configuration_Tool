
public class ConsoleManager {

	private final static int WIZ550S2E = 0x000000;
	private final static int WIZ550WEB = 0x010200;

	// command line input parameters
	static String parIp;
	static String parGateway;
	static String parSubnet;
	static String parHostIp;
	static String parLocalPort;
	static String parRemotePort;

//	static Boolean rdbtnTcpClient = false;
//	static Boolean rdbtnTcpServer = false;
//	static Boolean rdbtnTcpMixed = false;
//	static Boolean rdbtnUdp = false;
	static String parWorkingMode;

	static String parInactivity;
	static String parReconnection;

//	static Integer parBaudRate = 0;
//	static Integer parDataBits = 0;
//	static Integer parParity = 0;
//	static Integer parStopBits = 0;
//	static Integer parFlow = 0;
	static String parBaudRate;
	static String parDataBits;
	static String parParity;
	static String parStopBits;
	static String parFlow;


//	static Boolean parEnableAtCommand = false;
	static String parEnableAtCommand;
	static String parTrigger1;
	static String parTrigger2;
	static String parTrigger3;
	static String parPackingTime;
	static String parPackingSize;
//	static Integer parPackingDelimiterLength = 0;
	static String parPackingDelimiterLength;
	
	static String parPackingDelimiter1;
	static String parPackingDelimiter2;
	static String parPackingDelimiter3;
	static String parPackingDelimiter4;
//	static Integer parDataAppendix = 0;
	static String parDataAppendix;

	static String parModuleName;
	static String pwdSettingPassword;
	static String parConnectionPassword;
//	static Boolean parUseDhcp = false;
//	static Boolean parUseDns = false;
	static String parUseDhcp;
	static String parUseDns;
	
	static String parDnsServerIp;
	static String parDomain;

	static String parFirmwareVersion;
	static String parNetworkStatus;
	
	static String parSettingPassword;

	/* WIZ550S2E--> */
	public static void displayModuleInfo(WIZ550S2E_Config packet) {
		String str;

		if((0xFF&packet.fw_ver[0]) > 100) {
			str = String.format("Bootloader %d.%d.%d", (0xFF&packet.fw_ver[0]) - 100, (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		else {
			str = String.format("%d.%d.%d", (0xFF&packet.fw_ver[0]), (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		System.out.println("FirmwareVersion[ReadOnly] = " +str);
		
		

		switch((0xFF&packet.network_info[0].state)) {
		case 0:
			str = "Disconnected";
			break;
		case 1:
			str = "Connected";
			break;
		case 2:
			str = "UDP";
			break;
		default:
			str = "";
			break;
		}
		System.out.println("NetworkStatus[ReadOnly] = " +str);
		
		str = new String(packet.module_name);
		System.out.println("OptionsSetting.ModuleName = " +str.trim());
		str = new String(packet.options.pw_setting);
		System.out.println("OptionsSetting.SettingPassword = " +str.trim());
		str = new String(packet.options.pw_connect);
		System.out.println("OptionsSetting.ConnectionPassword = " +str.trim());

		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.local_ip[0]), (0xFF&packet.network_info_common.local_ip[1]), (0xFF&packet.network_info_common.local_ip[2]), (0xFF&packet.network_info_common.local_ip[3]));
		System.out.println("NetworkSetting.Ip = " +str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.gateway[0]), (0xFF&packet.network_info_common.gateway[1]), (0xFF&packet.network_info_common.gateway[2]), (0xFF&packet.network_info_common.gateway[3]));
		System.out.println("NetworkSetting.Gateway = " +str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.subnet[0]), (0xFF&packet.network_info_common.subnet[1]), (0xFF&packet.network_info_common.subnet[2]), (0xFF&packet.network_info_common.subnet[3]));
		System.out.println("NetworkSetting.Subnet = " +str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info[0].remote_ip[0]), (0xFF&packet.network_info[0].remote_ip[1]), (0xFF&packet.network_info[0].remote_ip[2]), (0xFF&packet.network_info[0].remote_ip[3]));
		System.out.println("NetworkSetting.HostIp = " +str);

		str = String.format("%d", (0xFFFF&packet.network_info[0].local_port));
		System.out.println("NetworkSetting.LocalPort = " +str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].remote_port));
		System.out.println("NetworkSetting.RemotePort = " +str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].inactivity));
		System.out.println("NetworkSetting.Timer.Inactivity = " +str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].reconnection));
		System.out.println("NetworkSetting.Timer.Reconnection Interval = " +str);

		switch((0xFF&packet.network_info[0].working_mode)) {
		case 0:
			str = "TCPClient";
			break;
		case 1:
			str = "TCPServer";
			break;
		case 2:
			str = "TCPMixed";
			break;
		case 3:
			str = "UDP";
			break;
		default:
			break;
		}
		System.out.println("NetworkSetting.WorkingMode = " + str);

		str = String.format("%d", (0xFFFFFFFF&packet.serial_info[0].baud_rate));
		System.out.println("SerialSetting.USART.BaudRate = " + str);
		str = String.format("%d", (0xFF&packet.serial_info[0].data_bits));
		System.out.println("SerialSetting.USART.DataBits = " + str);
		System.out.println("SerialSetting.USART.Parity = " + (0xFF&packet.serial_info[0].parity));
		System.out.println("SerialSetting.USART.StopBits = " + (0xFF&packet.serial_info[0].stop_bits));
		System.out.println("SerialSetting.USART.Flow = " + (0xFF&packet.serial_info[0].flow_control));

		if(packet.options.serial_command == 1)
			System.out.println("SerialSetting.AtCommand = Enabled");
		else
			System.out.println("SerialSetting.AtCommand = Disabled");
		
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[0]));
		System.out.println("SerialSetting.AtCommand.Trigger1 = " +str);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[1]));
		System.out.println("SerialSetting.AtCommand.Trigger2 = " +str);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[2]));
		System.out.println("SerialSetting.AtCommand.Trigger3 = " +str);
		
		str = String.format("%d", (0xFFFF&packet.network_info[0].packing_time));
		System.out.println("SerialSetting.PackingConditions.PackingTime = " +str);
		str = String.format("%d", (0xFF&packet.network_info[0].packing_size));
		System.out.println("SerialSetting.PackingConditions.PackingSize = " +str);
		System.out.println("SerialSetting.PackingConditions.PackingDelimiterLength = " +(0xFF&packet.network_info[0].packing_delimiter_length));
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[0]));
		System.out.println("SerialSetting.PackingConditions.PackingDelimiter1 = " +str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[1]));
		System.out.println("SerialSetting.PackingConditions.PackingDelimiter2 = " +str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[2]));
		System.out.println("SerialSetting.PackingConditions.PackingDelimiter3 = " +str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[3]));
		System.out.println("SerialSetting.PackingConditions.PackingDelimiter4 = " +str);
		System.out.println("SerialSetting.PackingConditions.DataAppendix = " +(0xFF&packet.network_info[0].packing_data_appendix));


		switch((0xFF&packet.options.dhcp_use)) {
		case 0:
//			gui.chckbxUseDhcp.setSelected(false);
			System.out.println("DHCP.UseDHCP = Disabled");
			break;
		case 1:
//			gui.chckbxUseDhcp.setSelected(true);
			System.out.println("DHCP.UseDHCP = Enabled");
			break;
		default:
//			gui.chckbxUseDhcp.setSelected(false);
			System.out.println("DHCP.UseDHCP = Disabled");
			break;
		}

		
		switch((0xFF&packet.options.dns_use)) {
		case 0:
//			gui.chckbxUseDns.setSelected(false);
			System.out.println("DNS.UseDNS = Disabled");
			break;
		case 1:
//			gui.chckbxUseDns.setSelected(true);
			System.out.println("DNS.UseDNS = Enabled");
			break;
		default:
//			gui.chckbxUseDns.setSelected(false);
			System.out.println("DNS.UseDNS = Disabled");
			break;
		}
		str = String.format("%d.%d.%d.%d", (0xFF&packet.options.dns_server_ip[0]), (0xFF&packet.options.dns_server_ip[1]), (0xFF&packet.options.dns_server_ip[2]), (0xFF&packet.options.dns_server_ip[3]));
		System.out.println("DNS.DnsServerIp = " +str);
		str = new String(packet.options.dns_domain_name);
		System.out.println("DNS.Domain = " +str.trim());
	}

	public static boolean setParamToPacket(String selectedMac, WIZ550S2E_Config packet) {
		InputValidation valid = new InputValidation();
		String[] str_array;

		if(selectedMac == null)
			return false;
		if(packet == null)
			packet = new WIZ550S2E_Config();

		str_array = selectedMac.split("\\:");
		packet.network_info_common.mac[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 16));
		packet.network_info_common.mac[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 16));
		packet.network_info_common.mac[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 16));
		packet.network_info_common.mac[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 16));
		packet.network_info_common.mac[4] = (byte) (0x00FF&Short.parseShort(str_array[4], 16));
		packet.network_info_common.mac[5] = (byte) (0x00FF&Short.parseShort(str_array[5], 16));
		
		// check if new value is entered 
		if(parModuleName != null){
			packet.module_name = parModuleName.trim().getBytes();
		}

		if(parIp != null){	
			if(!valid.IpValid(parIp.trim())) {
	//			JFrame frame = new JFrame();
	//			JOptionPane.showMessageDialog(frame, "IP input error");
				System.out.println("[Error]IP input invalid");
				return false;
			}
			str_array = parIp.split("\\.");
			packet.network_info_common.local_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info_common.local_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info_common.local_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info_common.local_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		}
		
		if(parGateway != null){
			if(!valid.IpValid(parGateway.trim())) {
				System.out.println("[Error]Gateway input invalid");
				return false;
			}
			str_array = parGateway.split("\\.");
			packet.network_info_common.gateway[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info_common.gateway[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info_common.gateway[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info_common.gateway[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));	
		}
		
		if(parSubnet != null){
			if(!valid.IpValid(parSubnet.trim())) {
				System.out.println("[Error]Subnet input invalid");
				return false;
			}
			str_array = parSubnet.split("\\.");
			packet.network_info_common.subnet[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info_common.subnet[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info_common.subnet[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info_common.subnet[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));		
		}
	
		if(parHostIp != null){
			if(!valid.IpValid(parHostIp.trim())) {
				System.out.println("[Error]Host IP input invalid");
				return false;
			}
			str_array = parHostIp.split("\\.");
			packet.network_info[0].remote_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info[0].remote_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info[0].remote_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info[0].remote_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		}
		

		if(parLocalPort != null){
			if(!valid.PortValid(parLocalPort.trim())) {
				System.out.println("[Error]Local Port input invalid");
				return false;
			}
			packet.network_info[0].local_port = (short) (0x0000FFFF&Integer.parseInt(parLocalPort, 10));
		}
		
		if(parRemotePort != null){
			if(!valid.PortValid(parRemotePort.trim())) {
				System.out.println("[Error]Remote Port input invalid");
				return false;
			}
			packet.network_info[0].remote_port = (short) (0x0000FFFF&Integer.parseInt(parRemotePort, 10));
		}
		
		if(parInactivity != null) packet.network_info[0].inactivity = (short) (0x0000FFFF&Integer.parseInt(parInactivity, 10));
		if(parReconnection != null) packet.network_info[0].reconnection = (short) (0x0000FFFF&Integer.parseInt(parReconnection, 10));
		if(parPackingTime != null) packet.network_info[0].packing_time = (short) (0x0000FFFF&Integer.parseInt(parPackingTime, 10));
		if(parPackingSize != null) packet.network_info[0].packing_size = (byte) (0x00FF&Short.parseShort(parPackingSize, 10));
		if(parPackingDelimiterLength != null) packet.network_info[0].packing_delimiter_length = (byte) (0x00FF&Short.parseShort(parPackingDelimiterLength,10));
		if(parPackingDelimiter1 != null) packet.network_info[0].packing_delimiter[0] = (byte) (0x00FF&Short.parseShort(parPackingDelimiter1, 16));
		if(parPackingDelimiter2 != null) packet.network_info[0].packing_delimiter[1] = (byte) (0x00FF&Short.parseShort(parPackingDelimiter2, 16));
		if(parPackingDelimiter3 != null) packet.network_info[0].packing_delimiter[2] = (byte) (0x00FF&Short.parseShort(parPackingDelimiter3, 16));
		if(parPackingDelimiter4 != null) packet.network_info[0].packing_delimiter[3] = (byte) (0x00FF&Short.parseShort(parPackingDelimiter4, 16));
		if(parDataAppendix != null) packet.network_info[0].packing_data_appendix = (byte) (0x00FF&Short.parseShort(parDataAppendix, 10));

		if(parWorkingMode != null){
			if(parWorkingMode.toLowerCase().equals("0")) { // TCP CLIENT
				packet.network_info[0].working_mode = 0;
			}
			else if(parWorkingMode.toLowerCase().equals("1")) { //TCP_SERVER
				packet.network_info[0].working_mode = 1;
			}
			else if(parWorkingMode.toLowerCase().equals("2")) { // TCP_MIXED
				packet.network_info[0].working_mode = 2;
			}
			else if(parWorkingMode.toLowerCase().equals("3")) { // UDP
				packet.network_info[0].working_mode = 3;
			}
			else {
				packet.network_info[0].working_mode = 0;
			}
		}
		
		
		if(parBaudRate != null) packet.serial_info[0].baud_rate = (int) (0x00000000FFFFFFFF&Long.parseLong((String) parBaudRate, 10));
		if(parDataBits != null) packet.serial_info[0].data_bits = (byte) (0x00FF&Short.parseShort((String) parDataBits, 10));
		if(parParity != null) packet.serial_info[0].parity = (byte) (0x00FF&Short.parseShort((String)parParity,10));
		if(parStopBits != null) packet.serial_info[0].stop_bits = (byte) (0x00FF&Short.parseShort((String) parStopBits, 10));
		if(parFlow != null) packet.serial_info[0].flow_control = (byte) (0x00FF&Short.parseShort((String)parFlow,10));

		if(parEnableAtCommand != null){
			if(parEnableAtCommand.toLowerCase().equals("1")) {
				packet.options.serial_command = 1; // enable
			}
			else {
				packet.options.serial_command = 0;
			}	
		}
		
		if(parTrigger1 != null) packet.options.serial_trigger[0] = (byte) (0x00FF&Short.parseShort(parTrigger1, 16));
		if(parTrigger2 != null) packet.options.serial_trigger[1] = (byte) (0x00FF&Short.parseShort(parTrigger2, 16));
		if(parTrigger3 != null) packet.options.serial_trigger[2] = (byte) (0x00FF&Short.parseShort(parTrigger3, 16));
		
		if(parUseDhcp != null){
			if(parUseDhcp.toLowerCase().equals("1")) {
				packet.options.dhcp_use = 1; // enable
			}
			else {
				packet.options.dhcp_use = 0;
			}
		}

		if(parSettingPassword != null) packet.options.pw_setting = parSettingPassword.trim().getBytes();
		if(parConnectionPassword != null) packet.options.pw_connect = parConnectionPassword.trim().getBytes();
		
		if(parUseDns != null){
			if(parUseDns.toLowerCase().equals("1")) {
				packet.options.dns_use = 1; // enable
			}
			else {
				packet.options.dns_use = 0;
			}
		}


		if(parDnsServerIp != null){
			if(!valid.IpValid(parDnsServerIp.trim())) {
				System.out.println("[Error]DNS Server IP input invalid");
				return false;
			}
			str_array = parDnsServerIp.split("\\.");
			packet.options.dns_server_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.options.dns_server_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.options.dns_server_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.options.dns_server_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
			packet.options.dns_domain_name = parDomain.trim().getBytes();
		}

		return true;
	}
	/* <--WIZ550S2E */

	/* WIZ550WEB--> */
	public static void displayModuleInfo(WIZ550WEB_Config packet) {
		String str;

		if((0xFF&packet.fw_ver[0]) > 100) {
			str = String.format("Bootloader %d.%d.%d", (0xFF&packet.fw_ver[0]) - 100, (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		else {
			str = String.format("%d.%d.%d", (0xFF&packet.fw_ver[0]), (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		System.out.println("FirmwareVersion[ReadOnly] " + str);

		str = new String(packet.module_name);
		System.out.println("OptionsSetting.ModuleName = " + str.trim());
		str = new String(packet.options.pw_setting);
		System.out.println("OptionsSetting.SettingPassword" + str.trim());

		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.local_ip[0]), (0xFF&packet.network_info_common.local_ip[1]), (0xFF&packet.network_info_common.local_ip[2]), (0xFF&packet.network_info_common.local_ip[3]));
		System.out.println("NetworkSetting.Ip " + str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.gateway[0]), (0xFF&packet.network_info_common.gateway[1]), (0xFF&packet.network_info_common.gateway[2]), (0xFF&packet.network_info_common.gateway[3]));
		System.out.println("NetworkSetting.Gateway " + str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.subnet[0]), (0xFF&packet.network_info_common.subnet[1]), (0xFF&packet.network_info_common.subnet[2]), (0xFF&packet.network_info_common.subnet[3]));
		System.out.println("NetworkSetting.Subnet " + str);

		
	}

	public static boolean setParamToPacket(String selectedMac, WIZ550WEB_Config packet) {
		InputValidation valid = new InputValidation();
		String[] str_array;

		if(selectedMac == null)
			return false;
		if(packet == null)
			packet = new WIZ550WEB_Config();

		str_array = selectedMac.split("\\:");
		packet.network_info_common.mac[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 16));
		packet.network_info_common.mac[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 16));
		packet.network_info_common.mac[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 16));
		packet.network_info_common.mac[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 16));
		packet.network_info_common.mac[4] = (byte) (0x00FF&Short.parseShort(str_array[4], 16));
		packet.network_info_common.mac[5] = (byte) (0x00FF&Short.parseShort(str_array[5], 16));
		
		if(parModuleName != null) packet.module_name = parModuleName.trim().getBytes();

		if(parIp != null){
			if(!valid.IpValid(parIp.trim())) {
				System.out.println("[Error]IP input invalid");
				return false;
			}
			str_array = parIp.split("\\.");
			packet.network_info_common.local_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info_common.local_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info_common.local_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info_common.local_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		}
	
		if(parGateway != null){
			if(!valid.IpValid(parGateway.trim())) {
				System.out.println("[Error]Gateway input invalid");
				return false;
			}
			str_array = parGateway.split("\\.");
			packet.network_info_common.gateway[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info_common.gateway[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info_common.gateway[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info_common.gateway[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		}

		if(parSubnet != null){
			if(!valid.IpValid(parSubnet.trim())) {
				System.out.println("[Error]Subnet input invalid");
				return false;
			}
			str_array = parSubnet.split("\\.");
			packet.network_info_common.subnet[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
			packet.network_info_common.subnet[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
			packet.network_info_common.subnet[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
			packet.network_info_common.subnet[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		}
	
		if(parSettingPassword != null) packet.options.pw_setting = parSettingPassword.trim().getBytes();

		return true;
	}
	/* <--WIZ550WEB */
}
