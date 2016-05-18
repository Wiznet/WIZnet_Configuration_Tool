import java.util.LinkedList;

import gnu.getopt.Getopt;

import java.lang.Object;

public class Console implements ReceiveCallback {

	private LinkedList<WIZnet_Header.Discovery_Reply> list = new LinkedList<WIZnet_Header.Discovery_Reply>();
	private WIZDatagramSocket socket = new WIZDatagramSocket(this);
	private String selectedMac;
	
	// module model list
	private WIZ550S2E_Config wiz550s2e_config;
	private WIZ550WEB_Config wiz550web_config;
	
	// command line arguments
	private Getopt getOpt;
	private String[] args;
	private String parMacAddress;
	private String parPassword;
	private String parServerIp;
	private String parServerPort;
	private String parFileName;
	
	boolean FIRMWARE_UPLOAD = false;
	boolean DISCOVERY_DONE = false;
	boolean GET_INFO_DONE = false;
	
	class Param{
		String par;
		int readonly;
		String desc;
		
		Param(String par, int readonly, String desc){
			this.par = par;
			this.readonly = readonly;
			this.desc = desc;
		}
	}
	
	Param []pa = {
		new Param("DP",1,"Display Infomation"), // 1 read only
		new Param("RT",1,"Reset moudule"),
		new Param("FR",1,"Factory Reset"),
		new Param("MN",0,"Model name"),
		new Param("LI",0,"Local IP address"),
		new Param("SM",0,"Subnet mask"),
		new Param("GW",0,"Gateway"),
		new Param("HI",0,"Host IP address"),
		new Param("LP",0,"Local port number"),
		new Param("RP",0,"Remote host port number"),
		new Param("WO",0,"Working mode (0:CLIENT,1:SERVER,2:MIXED,3:UDP)"),
		new Param("IT",0,"Inactivity timer (0~65535ms)"),
		new Param("RI",0,"Reconnect interval (0~65535ms)"),
		new Param("BR",0,"Baud rate (300,600,1200,1800,2400,4800,9600,14400,19200,28800,38400,57600,115200,230400)[bps]"),
		new Param("DB",0,"Data bits (7,8,9)[bit]"),
		new Param("PR",0,"Parity (0:NONE,1:ODD,2:EVEN)"),
		new Param("SB",0,"Stop bits (1,2)[bit]"),
		new Param("FL",0,"Flow control (0:NONE,1:RTS/CTS,2:RS422,3:RS485)"),
		new Param("AT",0,"AT Command (1:ENABLE,0:DISABLE)"),
		new Param("TC",0,"Trigger code(fixed 3 bytes in hex)"),	
		new Param("PT",0,"Serial data packing interval (0~65535ms)"),
		new Param("PS",0,"Packing size (0~255)"),
		new Param("PL",0,"Packing delimiter length(0,1,2,3,4)"),
		new Param("PD",0,"Packing delimiters in hex(max 4 bytes)"),
		new Param("PA",0,"Packing data appendix(0,1,2)"),
		new Param("DH",0,"DHCP flag (1:ENABLE,0:DISABLE)"),
		new Param("DN",0,"DDNS flag (1:ENABLE,0:DISABLE)"),
		new Param("DS",0,"DNS server IP"),
		new Param("DO",0,"Domain"),
		new Param("NP",0,"Connection password"),
		new Param("SP",0,"Setting password")
	};
			
	public Console(Getopt getOpt, String[] args) {
		
		
		this.getOpt = getOpt; 
		this.args = args;
		
		wiz550s2e_config = new WIZ550S2E_Config();
		wiz550web_config = new WIZ550WEB_Config();
	}

	public void exec() throws InterruptedException{
		
		InputValidation valid = new InputValidation();
		
		// default command line arguments
		parMacAddress = "";
		parPassword = ""; 
		parServerPort = "69"; 

		int c;
		while(true) {
			c = getOpt.getopt();
			
			if(c==-1) break;
			
			switch(c) {
			case 'b':
				// search every possible modules
				search();
				while(DISCOVERY_DONE == false){
					Thread.sleep(1);
				}
				WIZnet_Header.Discovery_Reply discovery_reply;
				for(int i=0; i<list.size(); i++) {
					discovery_reply = list.get(i);
					displayList(discovery_reply);
				}
				return;
			case 'm':
				parMacAddress = getOpt.getOptarg();
				if(!valid.MacValid(parMacAddress.trim())){
					System.out.println("[ERROR]Invalid Mac Address");
					return;
				}
				
				// search every possible modules
				search();
				while(DISCOVERY_DONE == false){
					Thread.sleep(1);
				}
				
				// get information from designated module 
				if(!getInfo()){
					System.out.println("[ERROR]Invalid Mac Address");
					return;
				}
				
				while(GET_INFO_DONE == false){
					Thread.sleep(1);
				}
				break;
			case 'p':
				parPassword = getOpt.getOptarg();
				break;
			case 'f':
				// firmware upload
				parFileName = getOpt.getOptarg();
				FIRMWARE_UPLOAD = true;
				break;
			case 's':
				parServerIp = getOpt.getOptarg();
				if(!valid.IpValid(parServerIp.trim())){
					System.out.println("[ERROR]Invalid Server IP Address");
					return;
				}
				break;
			case 't':
				parServerPort = getOpt.getOptarg();
				if(!valid.PortValid(parServerPort.trim())){
					System.out.println("[ERROR]Invalid Server Port");
					return;
				}
				break;
			default:
				System.out.println("Usage: [-b search modules] [-m mac-address] [-p setting-password] [-f firmware-filename] [-s TFTP server address] [-t TFTP server port] [-h] parameter list");
				System.out.println("e.g: $ -b");
				System.out.println("e.g: $ -m 00:08:DC:1D:ED:6E -p WIZnet -s 192.168.11.110 -f wiznet101.bin -t 69");
				System.out.println("e.g: $ -m 00:08:DC:1D:ED:6E DP\n"); 
				if(c!='h')
					return;
			
				for(int i = 0; i < pa.length; i++){
					System.out.println(pa[i].par+'\t'+pa[i].desc+((pa[i].readonly==1)?"[read only]":""));
				}
				return;
		       } 
			
		}
		
		// check if it is valid Mac Address
		if(!valid.MacValid(parMacAddress.trim())){
			System.out.println("[ERROR]Invalid Mac Address");
			return;
		}
		
		// firmware upload
		if(FIRMWARE_UPLOAD){
			fwUpload();
			return;
		}
		
		for (int i = getOpt.getOptind(); i < args.length ; i++){
			String param = args[i].substring(0, 2);
			String paramInput = (args[i].length() > 2)?args[i].substring(2):"";
			switch(param){
			case "DP":
				displayInfo();
				return;
			case "RT":
				reset();
				return;
			case "FR":
				factoryReset();
				return;
			case "MN":
				ConsoleManager.parModuleName = paramInput;
				break;
			case "LI":
				ConsoleManager.parIp = paramInput;
				break;
			case "SM":
				ConsoleManager.parSubnet = paramInput;
				break;
			case "GW":
				ConsoleManager.parGateway = paramInput;
				break;
			case "HI":
				ConsoleManager.parHostIp = paramInput;
				break;
			case "LP":
				ConsoleManager.parLocalPort = paramInput;
				break;
			case "RP":
				ConsoleManager.parRemotePort = paramInput;
				break;
			case "WO":
				ConsoleManager.parWorkingMode = paramInput;
				break;
			case "IT":
				ConsoleManager.parInactivity = paramInput;
				break;
			case "RI":
				ConsoleManager.parReconnection = paramInput;
				break;
			case "BR":
				ConsoleManager.parBaudRate = paramInput;
				break;
			case "DB":
				ConsoleManager.parDataBits = paramInput;
				break;
			case "PR":
				ConsoleManager.parParity = paramInput;
				break;
			case "SB":
				ConsoleManager.parStopBits = paramInput;
				break;
			case "FL":
				ConsoleManager.parFlow = paramInput;
				break;
			case "AT":
				ConsoleManager.parEnableAtCommand = paramInput;
				break;
			case "TC":
				if(paramInput.length()==6){
					ConsoleManager.parTrigger1 = paramInput.substring(0,2);
					ConsoleManager.parTrigger2 = paramInput.substring(2,4);
					ConsoleManager.parTrigger3 = paramInput.substring(4,6);
				}else{
					System.out.println("[ERROR]Trriger code is invalid");
					return;
				}
				break;
			case "PT":
				ConsoleManager.parPackingTime = paramInput;
				break;
			case "PS":
				ConsoleManager.parPackingSize = paramInput;
				break;
			case "PL":
				ConsoleManager.parPackingDelimiterLength = paramInput;
				break;
			case "PD":
				switch(ConsoleManager.parPackingDelimiterLength){
					case "1":
						if(paramInput.length() != 2){
							System.out.println("[ERROR]PackingDelimiter is invalid");
						}
						ConsoleManager.parPackingDelimiter1 = paramInput.substring(0,2);
						break;
					case "2":
						if(paramInput.length() != 4){
							System.out.println("[ERROR]PackingDelimiter is invalid");
						}
						ConsoleManager.parPackingDelimiter1 = paramInput.substring(0,2);
						ConsoleManager.parPackingDelimiter2 = paramInput.substring(2,4);
						break;
					case "3":
						if(paramInput.length() != 6){
							System.out.println("[ERROR]PackingDelimiter is invalid");
						}
						ConsoleManager.parPackingDelimiter1 = paramInput.substring(0,2);
						ConsoleManager.parPackingDelimiter2 = paramInput.substring(2,4);
						ConsoleManager.parPackingDelimiter3 = paramInput.substring(4,6);
						break;
					case "4":
						if(paramInput.length() != 8){
							System.out.println("[ERROR]PackingDelimiter is invalid");
						}
						ConsoleManager.parPackingDelimiter1 = paramInput.substring(0,2);
						ConsoleManager.parPackingDelimiter2 = paramInput.substring(2,4);
						ConsoleManager.parPackingDelimiter3 = paramInput.substring(4,6);
						ConsoleManager.parPackingDelimiter4 = paramInput.substring(6,8);
						break;
					default:
						break;
				}
				break;
			case "PA":
				ConsoleManager.parDataAppendix = paramInput;
				break;
			case "DH":
				ConsoleManager.parUseDhcp = paramInput;
				break;
			case "DN":
				ConsoleManager.parUseDns = paramInput;
				break;
			case "DS":
				ConsoleManager.parDnsServerIp = paramInput;
				break;
			case "DO":
				ConsoleManager.parDomain = paramInput;
				break;
			case "NP":
				ConsoleManager.parConnectionPassword = paramInput;
				break;
			case "SP":
				ConsoleManager.parSettingPassword = paramInput;
				break;
			default:
				System.out.println("[ERROR]Invalid Parameter: " + param);
				return;
			}
		}
		
		if( getOpt.getOptind() != args.length){
			setInfo();
		}

	}
	
	// received Thread
	public void receivedPacket(byte op, Object packet) {
		WIZnet_Header wiznet_header = new WIZnet_Header();

		if((byte)(op & 0xF0) == (byte)wiznet_header.DISCOVERY) {
			WIZnet_Header.Discovery_Reply discovery_reply = (WIZnet_Header.Discovery_Reply) packet;
			list.add(discovery_reply);
			DISCOVERY_DONE = true;
		}
		else if(op == wiznet_header.GET_INFO) {
			WIZnet_Header.Get_Info_Reply get_info_reply = (WIZnet_Header.Get_Info_Reply) packet;

			/* WIZ550S2E */
			if(get_info_reply.system_info[2] == 0x00 && get_info_reply.system_info[3] == 0x00 && get_info_reply.system_info[4] == 0x00) {
//				WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
				wiz550s2e_config.setData(get_info_reply.system_info);
//				ConsoleManager.displayModuleInfo(wiz550s2e_config);
			}
			/* WIZ550WEB */
			else if(get_info_reply.system_info[2] == 0x01 && get_info_reply.system_info[3] == 0x02 && get_info_reply.system_info[4] == 0x00) {
//				WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
				wiz550web_config.setData(get_info_reply.system_info);
//				ConsoleManager.displayModuleInfo(wiz550web_config);
			}
			
			GET_INFO_DONE = true;
		}
		else if(op == wiznet_header.SET_INFO) {
			System.out.println("["+parMacAddress+"]Setting succeed");
		}
		else if(op == wiznet_header.FIRMWARE_UPLOAD_INIT) {
			System.out.println("["+parMacAddress+"]Firmware Upload start");
		}
		else if(op == wiznet_header.FIRMWARE_UPLOAD_DONE) {
			System.out.println("["+parMacAddress+"]Firmware Upload succeed");
		}
		else if(op == wiznet_header.REMOTE_RESET) {
			System.out.println("["+parMacAddress+"]Reset succeed");
		}
		else if(op == wiznet_header.FACTORY_RESET) {
			System.out.println("["+parMacAddress+"]Factory Reset succeed ");
		}
	}

	// display all found modules
	private void displayList(WIZnet_Header.Discovery_Reply packet) {
		String module_type = new String();
		String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
				packet.mac_address[0], packet.mac_address[1],
				packet.mac_address[2], packet.mac_address[3],
				packet.mac_address[4], packet.mac_address[5]);
		
		if(packet.product_code[0] == 0x00 && packet.product_code[1] == 0x00 && packet.product_code[2] == 0x00) {
			module_type = "WIZ550S2E";
		}
		else if(packet.product_code[0] == 0x01 && packet.product_code[1] == 0x02 && packet.product_code[2] == 0x00) {
			module_type = "WIZ550WEB";
		}
		else {
			module_type = "UNKNOWN";
		}
		System.out.println("[" + module_type + "]" + mac_address);
	}
	
	// search modules	
	public void search() {
//		InputValidation valid = new InputValidation();
		WIZnet_Header wiznet_header = new WIZnet_Header();
	
		list.clear();
		socket.discovery("255.255.255.255", wiznet_header.DISCOVERY_ALL);
	}
	
	// get information from module
	public boolean getInfo() {
		selectedMac = null;

		WIZnet_Header.Discovery_Reply discovery_reply;
		for(int i=0; i<list.size(); i++) {
			discovery_reply = list.get(i);
			String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
					discovery_reply.mac_address[0], discovery_reply.mac_address[1],
					discovery_reply.mac_address[2], discovery_reply.mac_address[3],
					discovery_reply.mac_address[4], discovery_reply.mac_address[5]);
			
			if(parMacAddress.equals(mac_address)) {
				selectedMac = mac_address;
				socket.get_info("255.255.255.255", discovery_reply.mac_address);
				return true;
			}
		}
		return false;
	}
	
	// update information corresponding to parameter list
	public void setInfo() {
		
		WIZnet_Header.Discovery_Reply discovery_reply;
		for(int i=0; i<list.size(); i++) {
			
			discovery_reply = list.get(i);
			String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
					discovery_reply.mac_address[0], discovery_reply.mac_address[1],
					discovery_reply.mac_address[2], discovery_reply.mac_address[3],
					discovery_reply.mac_address[4], discovery_reply.mac_address[5]);

			if(parMacAddress.equals(mac_address)) {
				selectedMac = mac_address;
				/* WIZ550S2E */
				if(discovery_reply.product_code[0] == 0x00 && discovery_reply.product_code[1] == 0x00 && discovery_reply.product_code[2] == 0x00) {
//					WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
					if(ConsoleManager.setParamToPacket(selectedMac, wiz550s2e_config)){
						socket.set_info("255.255.255.255", wiz550s2e_config.getData(), parPassword.trim());
					}
				}
				/* WIZ550WEB */
				else if(discovery_reply.product_code[0] == 0x01 && discovery_reply.product_code[1] == 0x02 && discovery_reply.product_code[2] == 0x00) {
//					WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
					if(ConsoleManager.setParamToPacket(selectedMac, wiz550web_config)) {
						socket.set_info("255.255.255.255", wiz550web_config.getData(), parPassword.trim());
					}
				}
				break;
			}
		}
	}
	
	// display module's information on screen
	public void displayInfo() {
		WIZnet_Header.Discovery_Reply discovery_reply;
		for(int i=0; i<list.size(); i++) {
			discovery_reply = list.get(i);
			String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
					discovery_reply.mac_address[0], discovery_reply.mac_address[1],
					discovery_reply.mac_address[2], discovery_reply.mac_address[3],
					discovery_reply.mac_address[4], discovery_reply.mac_address[5]);
			
			if(parMacAddress.equals(mac_address)) {
				/* WIZ550S2E */
				if(discovery_reply.product_code[0] == 0x00 && discovery_reply.product_code[1] == 0x00 && discovery_reply.product_code[2] == 0x00) {
					ConsoleManager.displayModuleInfo(wiz550s2e_config);
				}
				/* WIZ550WEB */
				else if(discovery_reply.product_code[0] == 0x01 && discovery_reply.product_code[1] == 0x02 && discovery_reply.product_code[2] == 0x00) {
					ConsoleManager.displayModuleInfo(wiz550web_config);
				
				}
				break;
			}
		}
	}

	// firmware upload with server
	public void fwUpload() {

		WIZnet_Header.Discovery_Reply discovery_reply;
		for(int i=0; i<list.size(); i++) {
			discovery_reply = list.get(i);
			String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
					discovery_reply.mac_address[0], discovery_reply.mac_address[1],
					discovery_reply.mac_address[2], discovery_reply.mac_address[3],
					discovery_reply.mac_address[4], discovery_reply.mac_address[5]);
			
			// select a module which specified in argument
			if(parMacAddress.equals(mac_address)) {
				
				byte[] serverIp = new byte[4];
				String[] str_array = parServerIp.split("\\.");
				serverIp[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
				serverIp[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
				serverIp[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
				serverIp[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		
				//		String password = new String(dialog.pwdPassword.getPassword());
		
				/* WIZ550S2E */
				if(discovery_reply.product_code[0] == 0x00 && discovery_reply.product_code[1] == 0x00 && discovery_reply.product_code[2] == 0x00) {
//					WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
					if(ConsoleManager.setParamToPacket(selectedMac, wiz550s2e_config)) {
						socket.firmware_upload(wiz550s2e_config.getData(), serverIp, Short.parseShort(parServerPort, 10), parFileName.trim(), parPassword.trim());
					}
				}
				/* WIZ550WEB */
					else if(discovery_reply.product_code[0] == 0x01 && discovery_reply.product_code[1] == 0x02 && discovery_reply.product_code[2] == 0x00) {
//						WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
						if(ConsoleManager.setParamToPacket(selectedMac, wiz550web_config)) {
							socket.firmware_upload(wiz550web_config.getData(), serverIp, Short.parseShort(parServerPort, 10), parFileName.trim(), parPassword.trim());
						}
					}
				break;
			}
		}
	}
	
	// reset module
	public void reset() {
		WIZnet_Header.Discovery_Reply discovery_reply;
		for(int i=0; i<list.size(); i++) {
			discovery_reply = list.get(i);
			String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
					discovery_reply.mac_address[0], discovery_reply.mac_address[1],
					discovery_reply.mac_address[2], discovery_reply.mac_address[3],
					discovery_reply.mac_address[4], discovery_reply.mac_address[5]);
			
			if(parMacAddress.equals(mac_address)) {
				socket.reset("255.255.255.255", discovery_reply.mac_address, parPassword.trim());		
				break;
			}
		}	
	}

	// factory-reset module
	public void factoryReset() {
		WIZnet_Header.Discovery_Reply discovery_reply;
		for(int i=0; i<list.size(); i++) {
			discovery_reply = list.get(i);
			String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
					discovery_reply.mac_address[0], discovery_reply.mac_address[1],
					discovery_reply.mac_address[2], discovery_reply.mac_address[3],
					discovery_reply.mac_address[4], discovery_reply.mac_address[5]);

			if(parMacAddress.equals(mac_address)) {
				socket.factory_reset("255.255.255.255", discovery_reply.mac_address, parPassword.trim());
				break;
			}
		}
	}
}
