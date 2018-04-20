import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;

/**
 * 
 */

/**
 * @author Administrator
 *
 */
public class PanelManager {

	private final static int WIZ550S2E = 0x000000;
	private final static int WIZ550WEB = 0x010200;
	private final static int WIZ550SR = 0x020000;

	public static void clearPanel(GUI gui) {
		gui.txtIp.setText("");
		gui.txtGateway.setText("");
		gui.txtSubnet.setText("");
		gui.txtHostIp.setText("");
		gui.txtLocalPort.setText("");
		gui.txtRemotePort.setText("");

		gui.rdbtnTcpClient.setSelected(false);
		gui.rdbtnTcpServer.setSelected(false);
		gui.rdbtnTcpMixed.setSelected(false);
		gui.rdbtnUdp.setSelected(false);

		gui.txtInactivity.setText("");
		gui.txtReconnection.setText("");

		gui.cmbBaudRate.setSelectedIndex(0);
		gui.cmbDataBits.setSelectedIndex(0);
		gui.cmbParity.setSelectedIndex(0);
		gui.cmbStopBits.setSelectedIndex(0);
		gui.cmbFlow.setSelectedIndex(0);

		gui.chckbxEnableAtCommand.setSelected(false);
		gui.txtTrigger1.setText("");
		gui.txtTrigger2.setText("");
		gui.txtTrigger3.setText("");
		gui.txtPackingTime.setText("");
		gui.txtPackingSize.setText("");
		gui.cmbPackingDelimiterLength.setSelectedIndex(0);
		gui.txtPackingDelimiter1.setText("");
		gui.txtPackingDelimiter2.setText("");
		gui.txtPackingDelimiter3.setText("");
		gui.txtPackingDelimiter4.setText("");
		gui.cmbDataAppendix.setSelectedIndex(0);

		gui.txtModuleName.setText("");
		gui.pwdSettingPassword.setText("");
		gui.txtConnectionPassword.setText("");
		gui.chckbxUseDhcp.setSelected(false);
		gui.chckbxUseDns.setSelected(false);
		gui.txtDnsServerIp.setText("");
		gui.txtDomain.setText("");

		gui.txtFirmwareVersion.setText("");
		gui.txtNetworkStatus.setText("");
	}

	public static void setPanel(GUI gui, int moduleType) {
		switch(moduleType) {
		case WIZ550S2E:
			//gui.panel_ListOfModules.setEnabled(true);
			//gui.panel_SearchMethod.setEnabled(false);
			//gui.panel_Options.setEnabled(true);
			//gui.panel_ModuleName.setEnabled(true);
			gui.txtModuleName.setEnabled(true);
			//gui.panel_Password.setEnabled(true);
			//gui.lblSettingPassword.setEnabled(true);
			gui.pwdSettingPassword.setEnabled(true);
			//gui.lblConnectionPassword.setEnabled(true);
			gui.txtConnectionPassword.setEnabled(true);
			//gui.panel_Dhcp.setEnabled(true);
			gui.chckbxUseDhcp.setEnabled(true);
			//gui.panel_Dns.setEnabled(true);
			gui.chckbxUseDns.setEnabled(true);
			//gui.lblDnsServerIp.setEnabled(true);
			gui.txtDnsServerIp.setEnabled(true);
			//gui.lblDomain.setEnabled(true);
			gui.txtDomain.setEnabled(true);
			//gui.panel_Network.setEnabled(true);
			//gui.panel_Ip.setEnabled(true);
			//gui.lblIp.setEnabled(true);
			gui.txtIp.setEnabled(true);
			//gui.lblGateway.setEnabled(true);
			gui.txtGateway.setEnabled(true);
			//gui.lblSubnet.setEnabled(true);
			gui.txtSubnet.setEnabled(true);
			//gui.lblHostIp.setEnabled(true);
			gui.txtHostIp.setEnabled(true);
			//gui.panel_Port.setEnabled(true);
			//gui.lblLocalPort.setEnabled(true);
			gui.txtLocalPort.setEnabled(true);
			//gui.lblRemotePort.setEnabled(false);
			gui.txtRemotePort.setEnabled(true);
			//gui.panel_WorkingMode.setEnabled(true);
			gui.rdbtnTcpClient.setEnabled(true);
			gui.rdbtnTcpServer.setEnabled(true);
			gui.rdbtnTcpMixed.setEnabled(true);
			gui.rdbtnUdp.setEnabled(true);
			//gui.panel_Timer.setEnabled(true);
			//gui.lblInactivity.setEnabled(true);
			gui.txtInactivity.setEnabled(true);
			//gui.lblReconnection.setEnabled(true);
			gui.txtReconnection.setEnabled(true);
			//gui.panel_Serial.setEnabled(true);
			//gui.panel_Usart.setEnabled(true);
			//gui.lblBaudRate.setEnabled(true);
			gui.cmbBaudRate.setModel(new DefaultComboBoxModel<Object>(new String[] {"300", "600", "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400"}));
			gui.cmbBaudRate.setEnabled(true);
			//gui.lblDataBits.setEnabled(true);
			gui.cmbDataBits.setModel(new DefaultComboBoxModel<Object>(new String[] {"7", "8"}));
			gui.cmbDataBits.setEnabled(true);
			//gui.lblParity.setEnabled(true);
			gui.cmbParity.setEnabled(true);
			//gui.lblStopBits.setEnabled(true);
			gui.cmbStopBits.setEnabled(true);
			//gui.lblFlow.setEnabled(true);
			gui.cmbFlow.setModel(new DefaultComboBoxModel<Object>(new String[] {"None", "RTS/CTS", "RS422", "RS485"}));
			gui.cmbFlow.setEnabled(true);
			//gui.panel_AtCommand.setEnabled(true);
			gui.chckbxEnableAtCommand.setEnabled(true);
			//gui.lblTrigger.setEnabled(true);
			gui.txtTrigger1.setEnabled(true);
			gui.txtTrigger2.setEnabled(true);
			gui.txtTrigger3.setEnabled(true);
			//gui.panel_PackingConditions.setEnabled(true);
			//gui.lblPackingTime.setEnabled(true);
			gui.txtPackingTime.setEnabled(true);
			//gui.lblPackingSize.setEnabled(true);
			gui.txtPackingSize.setEnabled(true);
			//gui.panel_Char.setEnabled(true);
			//gui.lblLength.setEnabled(true);
			gui.cmbPackingDelimiterLength.setEnabled(true);
			gui.txtPackingDelimiter1.setEnabled(true);
			gui.txtPackingDelimiter2.setEnabled(true);
			gui.txtPackingDelimiter3.setEnabled(true);
			gui.txtPackingDelimiter4.setEnabled(true);
			//gui.lblAppendix.setEnabled(true);
			gui.cmbDataAppendix.setEnabled(true);
			//gui.panel.setEnabled(true);
			gui.txtFirmwareVersion.setEnabled(true);
			gui.txtNetworkStatus.setEnabled(true);
			//gui.panel_Buttons.setEnabled(true);
			break;
		case WIZ550WEB:
			//gui.panel_ListOfModules.setEnabled(true);
			//gui.panel_SearchMethod.setEnabled(false);
			//gui.panel_Options.setEnabled(true);
			//gui.panel_ModuleName.setEnabled(true);
			gui.txtModuleName.setEnabled(true);
			//gui.panel_Password.setEnabled(false);
			//gui.lblSettingPassword.setEnabled(true);
			gui.pwdSettingPassword.setEnabled(true);
			//gui.lblConnectionPassword.setEnabled(true);
			gui.txtConnectionPassword.setEnabled(true);
			gui.panel_Dhcp.setEnabled(true);
			gui.chckbxUseDhcp.setEnabled(true);
			gui.panel_Dns.setEnabled(false);
			gui.chckbxUseDns.setEnabled(false);
			//gui.lblDnsServerIp.setEnabled(false);
			gui.txtDnsServerIp.setEnabled(false);
			//gui.lblDomain.setEnabled(false);
			gui.txtDomain.setEnabled(false);
			//gui.panel_Network.setEnabled(true);
			//gui.panel_Ip.setEnabled(true);
			//gui.lblIp.setEnabled(true);
			gui.txtIp.setEnabled(true);
			//gui.lblGateway.setEnabled(true);
			gui.txtGateway.setEnabled(true);
			//gui.lblSubnet.setEnabled(true);
			gui.txtSubnet.setEnabled(true);
			//gui.lblHostIp.setEnabled(false);
			gui.txtHostIp.setEnabled(false);
			//gui.panel_Port.setEnabled(false);
			//gui.lblLocalPort.setEnabled(false);
			gui.txtLocalPort.setEnabled(true);
			//gui.lblRemotePort.setEnabled(false);
			gui.txtRemotePort.setEnabled(false);
			//gui.panel_WorkingMode.setEnabled(false);
			gui.rdbtnTcpClient.setEnabled(false);
			gui.rdbtnTcpServer.setEnabled(false);
			gui.rdbtnTcpMixed.setEnabled(false);
			gui.rdbtnUdp.setEnabled(false);
			//gui.panel_Timer.setEnabled(false);
			//gui.lblInactivity.setEnabled(false);
			gui.txtInactivity.setEnabled(false);
			//gui.lblReconnection.setEnabled(false);
			gui.txtReconnection.setEnabled(false);
			//gui.panel_Serial.setEnabled(false);
			//gui.panel_Usart.setEnabled(false);
			//gui.lblBaudRate.setEnabled(false);
			gui.cmbBaudRate.setModel(new DefaultComboBoxModel<Object>(new String[] {"300", "600", "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400"}));
			gui.cmbBaudRate.setEnabled(true);
			//gui.lblDataBits.setEnabled(false);
			gui.cmbDataBits.setModel(new DefaultComboBoxModel<Object>(new String[] {"7", "8"}));
			gui.cmbDataBits.setEnabled(true);
			//gui.lblParity.setEnabled(false);
			gui.cmbParity.setEnabled(true);
			//gui.lblStopBits.setEnabled(false);
			gui.cmbStopBits.setEnabled(true);
			//gui.lblFlow.setEnabled(false);
			gui.cmbFlow.setModel(new DefaultComboBoxModel<Object>(new String[] {"None", "RTS/CTS", "RS422", "RS485"}));
			gui.cmbFlow.setEnabled(true);
			//gui.panel_AtCommand.setEnabled(false);
			gui.chckbxEnableAtCommand.setEnabled(false);
			//gui.lblTrigger.setEnabled(false);
			gui.txtTrigger1.setEnabled(false);
			gui.txtTrigger2.setEnabled(false);
			gui.txtTrigger3.setEnabled(false);
			//gui.panel_PackingConditions.setEnabled(false);
			//gui.lblPackingTime.setEnabled(false);
			gui.txtPackingTime.setEnabled(false);
			//gui.lblPackingSize.setEnabled(false);
			gui.txtPackingSize.setEnabled(false);
			//gui.panel_Char.setEnabled(false);
			//gui.lblLength.setEnabled(false);
			gui.cmbPackingDelimiterLength.setEnabled(false);
			gui.txtPackingDelimiter1.setEnabled(false);
			gui.txtPackingDelimiter2.setEnabled(false);
			gui.txtPackingDelimiter3.setEnabled(false);
			gui.txtPackingDelimiter4.setEnabled(false);
			//gui.lblAppendix.setEnabled(false);
			gui.cmbDataAppendix.setEnabled(false);
			//gui.panel.setEnabled(true);
			gui.txtFirmwareVersion.setEnabled(true);
			gui.txtNetworkStatus.setEnabled(false);
			//gui.panel_Buttons.setEnabled(true);
			break;
		case WIZ550SR:
			//gui.panel_ListOfModules.setEnabled(true);
			//gui.panel_SearchMethod.setEnabled(false);
			//gui.panel_Options.setEnabled(true);
			//gui.panel_ModuleName.setEnabled(true);
			gui.txtModuleName.setEnabled(true);
			//gui.panel_Password.setEnabled(true);
			//gui.lblSettingPassword.setEnabled(true);
			gui.pwdSettingPassword.setEnabled(true);
			//gui.lblConnectionPassword.setEnabled(true);
			gui.txtConnectionPassword.setEnabled(true);
			//gui.panel_Dhcp.setEnabled(true);
			gui.chckbxUseDhcp.setEnabled(true);
			//gui.panel_Dns.setEnabled(true);
			gui.chckbxUseDns.setEnabled(true);
			//gui.lblDnsServerIp.setEnabled(true);
			gui.txtDnsServerIp.setEnabled(true);
			//gui.lblDomain.setEnabled(true);
			gui.txtDomain.setEnabled(true);
			//gui.panel_Network.setEnabled(true);
			//gui.panel_Ip.setEnabled(true);
			//gui.lblIp.setEnabled(true);
			gui.txtIp.setEnabled(true);
			//gui.lblGateway.setEnabled(true);
			gui.txtGateway.setEnabled(true);
			//gui.lblSubnet.setEnabled(true);
			gui.txtSubnet.setEnabled(true);
			//gui.lblHostIp.setEnabled(true);
			gui.txtHostIp.setEnabled(true);
			//gui.panel_Port.setEnabled(true);
			//gui.lblLocalPort.setEnabled(true);
			gui.txtLocalPort.setEnabled(true);
			//gui.lblRemotePort.setEnabled(false);
			gui.txtRemotePort.setEnabled(true);
			//gui.panel_WorkingMode.setEnabled(true);
			gui.rdbtnTcpClient.setEnabled(true);
			gui.rdbtnTcpServer.setEnabled(true);
			gui.rdbtnTcpMixed.setEnabled(true);
			gui.rdbtnUdp.setEnabled(true);
			//gui.panel_Timer.setEnabled(true);
			//gui.lblInactivity.setEnabled(true);
			gui.txtInactivity.setEnabled(true);
			//gui.lblReconnection.setEnabled(true);
			gui.txtReconnection.setEnabled(true);
			//gui.panel_Serial.setEnabled(true);
			//gui.panel_Usart.setEnabled(true);
			//gui.lblBaudRate.setEnabled(true);
			
			gui.cmbBaudRate.setModel(new DefaultComboBoxModel<Object>(new String[] {"600", "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400"}));
			
			gui.cmbBaudRate.setEnabled(true);
			//gui.lblDataBits.setEnabled(true);
			
			gui.cmbDataBits.setModel(new DefaultComboBoxModel<Object>(new String[] {"8"}));
			gui.cmbDataBits.setEnabled(true);
			//gui.lblParity.setEnabled(true);
			gui.cmbParity.setEnabled(true);
			//gui.lblStopBits.setEnabled(true);
			gui.cmbStopBits.setEnabled(true);
			//gui.lblFlow.setEnabled(true);
			
			gui.cmbFlow.setModel(new DefaultComboBoxModel<Object>(new String[] {"None", "RTS/CTS"}));
			
			gui.cmbFlow.setEnabled(true);
			//gui.panel_AtCommand.setEnabled(true);
			gui.chckbxEnableAtCommand.setEnabled(true);
			//gui.lblTrigger.setEnabled(true);
			gui.txtTrigger1.setEnabled(true);
			gui.txtTrigger2.setEnabled(true);
			gui.txtTrigger3.setEnabled(true);
			//gui.panel_PackingConditions.setEnabled(true);
			//gui.lblPackingTime.setEnabled(true);
			gui.txtPackingTime.setEnabled(true);
			//gui.lblPackingSize.setEnabled(true);
			gui.txtPackingSize.setEnabled(true);
			//gui.panel_Char.setEnabled(true);
			//gui.lblLength.setEnabled(true);
			gui.cmbPackingDelimiterLength.setEnabled(true);
			gui.txtPackingDelimiter1.setEnabled(true);
			gui.txtPackingDelimiter2.setEnabled(true);
			gui.txtPackingDelimiter3.setEnabled(true);
			gui.txtPackingDelimiter4.setEnabled(true);
			//gui.lblAppendix.setEnabled(true);
			gui.cmbDataAppendix.setEnabled(true);
			//gui.panel.setEnabled(true);
			gui.txtFirmwareVersion.setEnabled(true);
			gui.txtNetworkStatus.setEnabled(true);
			//gui.panel_Buttons.setEnabled(true);
			break;
		default:
			//gui.panel_ListOfModules.setEnabled(true);
			//gui.panel_SearchMethod.setEnabled(false);
			//gui.panel_Options.setEnabled(true);
			//gui.panel_ModuleName.setEnabled(true);
			gui.txtModuleName.setEnabled(true);
			//gui.panel_Password.setEnabled(true);
			//gui.lblSettingPassword.setEnabled(true);
			gui.pwdSettingPassword.setEnabled(true);
			//gui.lblConnectionPassword.setEnabled(true);
			gui.txtConnectionPassword.setEnabled(true);
			//gui.panel_Dhcp.setEnabled(true);
			gui.chckbxUseDhcp.setEnabled(true);
			//gui.panel_Dns.setEnabled(true);
			gui.chckbxUseDns.setEnabled(true);
			//gui.lblDnsServerIp.setEnabled(true);
			gui.txtDnsServerIp.setEnabled(true);
			//gui.lblDomain.setEnabled(true);
			gui.txtDomain.setEnabled(true);
			//gui.panel_Network.setEnabled(true);
			//gui.panel_Ip.setEnabled(true);
			//gui.lblIp.setEnabled(true);
			gui.txtIp.setEnabled(true);
			//gui.lblGateway.setEnabled(true);
			gui.txtGateway.setEnabled(true);
			//gui.lblSubnet.setEnabled(true);
			gui.txtSubnet.setEnabled(true);
			//gui.lblHostIp.setEnabled(true);
			gui.txtHostIp.setEnabled(true);
			//gui.panel_Port.setEnabled(true);
			//gui.lblLocalPort.setEnabled(true);
			gui.txtLocalPort.setEnabled(true);
			//gui.lblRemotePort.setEnabled(false);
			gui.txtRemotePort.setEnabled(true);
			//gui.panel_WorkingMode.setEnabled(true);
			gui.rdbtnTcpClient.setEnabled(true);
			gui.rdbtnTcpServer.setEnabled(true);
			gui.rdbtnTcpMixed.setEnabled(true);
			gui.rdbtnUdp.setEnabled(true);
			//gui.panel_Timer.setEnabled(true);
			//gui.lblInactivity.setEnabled(true);
			gui.txtInactivity.setEnabled(true);
			//gui.lblReconnection.setEnabled(true);
			gui.txtReconnection.setEnabled(true);
			//gui.panel_Serial.setEnabled(true);
			//gui.panel_Usart.setEnabled(true);
			//gui.lblBaudRate.setEnabled(true);
			gui.cmbBaudRate.setEnabled(true);
			//gui.lblDataBits.setEnabled(true);
			gui.cmbDataBits.setEnabled(true);
			//gui.lblParity.setEnabled(true);
			gui.cmbParity.setEnabled(true);
			//gui.lblStopBits.setEnabled(true);
			gui.cmbStopBits.setEnabled(true);
			//gui.lblFlow.setEnabled(true);
			gui.cmbFlow.setEnabled(true);
			//gui.panel_AtCommand.setEnabled(true);
			gui.chckbxEnableAtCommand.setEnabled(true);
			//gui.lblTrigger.setEnabled(true);
			gui.txtTrigger1.setEnabled(true);
			gui.txtTrigger2.setEnabled(true);
			gui.txtTrigger3.setEnabled(true);
			//gui.panel_PackingConditions.setEnabled(true);
			//gui.lblPackingTime.setEnabled(true);
			gui.txtPackingTime.setEnabled(true);
			//gui.lblPackingSize.setEnabled(true);
			gui.txtPackingSize.setEnabled(true);
			//gui.panel_Char.setEnabled(true);
			//gui.lblLength.setEnabled(true);
			gui.cmbPackingDelimiterLength.setEnabled(true);
			gui.txtPackingDelimiter1.setEnabled(true);
			gui.txtPackingDelimiter2.setEnabled(true);
			gui.txtPackingDelimiter3.setEnabled(true);
			gui.txtPackingDelimiter4.setEnabled(true);
			//gui.lblAppendix.setEnabled(true);
			gui.cmbDataAppendix.setEnabled(true);
			//gui.panel_Buttons.setEnabled(true);
			break;
		}
	}

	/* WIZ550S2E--> */
	public static void updateToPanel(GUI gui, WIZ550S2E_Config packet) {
		clearPanel(gui);
		setPanel(gui, WIZ550S2E);

		String str;

		str = new String(packet.module_name);
		gui.txtModuleName.setText(str.trim());
		if((0xFF&packet.fw_ver[0]) > 100) {
			str = String.format("Bootloader %d.%d.%d", (0xFF&packet.fw_ver[0]) - 100, (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		else {
			str = String.format("%d.%d.%d", (0xFF&packet.fw_ver[0]), (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		gui.txtFirmwareVersion.setText(str);

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
		gui.txtNetworkStatus.setText(str);

		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.local_ip[0]), (0xFF&packet.network_info_common.local_ip[1]), (0xFF&packet.network_info_common.local_ip[2]), (0xFF&packet.network_info_common.local_ip[3]));
		gui.txtIp.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.gateway[0]), (0xFF&packet.network_info_common.gateway[1]), (0xFF&packet.network_info_common.gateway[2]), (0xFF&packet.network_info_common.gateway[3]));
		gui.txtGateway.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.subnet[0]), (0xFF&packet.network_info_common.subnet[1]), (0xFF&packet.network_info_common.subnet[2]), (0xFF&packet.network_info_common.subnet[3]));
		gui.txtSubnet.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info[0].remote_ip[0]), (0xFF&packet.network_info[0].remote_ip[1]), (0xFF&packet.network_info[0].remote_ip[2]), (0xFF&packet.network_info[0].remote_ip[3]));
		gui.txtHostIp.setText(str);

		str = String.format("%d", (0xFFFF&packet.network_info[0].local_port));
		gui.txtLocalPort.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].remote_port));
		gui.txtRemotePort.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].inactivity));
		gui.txtInactivity.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].reconnection));
		gui.txtReconnection.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].packing_time));
		gui.txtPackingTime.setText(str);
		str = String.format("%d", (0xFF&packet.network_info[0].packing_size));
		gui.txtPackingSize.setText(str);
		gui.cmbPackingDelimiterLength.setSelectedIndex((0xFF&packet.network_info[0].packing_delimiter_length));
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[0]));
		gui.txtPackingDelimiter1.setText(str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[1]));
		gui.txtPackingDelimiter2.setText(str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[2]));
		gui.txtPackingDelimiter3.setText(str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[3]));
		gui.txtPackingDelimiter4.setText(str);
		gui.cmbDataAppendix.setSelectedIndex((0xFF&packet.network_info[0].packing_data_appendix));

		switch((0xFF&packet.network_info[0].working_mode)) {
		case 0:
			gui.rdbtnTcpClient.setSelected(true);
			break;
		case 1:
			gui.rdbtnTcpServer.setSelected(true);
			break;
		case 2:
			gui.rdbtnTcpMixed.setSelected(true);
			break;
		case 3:
			gui.rdbtnUdp.setSelected(true);
			break;
		case 4:
			gui.rdbtnMqtt.setSelected(true);
			break;
		default:
			break;
		}

		str = String.format("%d", (0xFFFFFFFF&packet.serial_info[0].baud_rate));
		gui.cmbBaudRate.setSelectedItem(str);
		str = String.format("%d", (0xFF&packet.serial_info[0].data_bits));
		gui.cmbDataBits.setSelectedItem(str);
		gui.cmbParity.setSelectedIndex((0xFF&packet.serial_info[0].parity));
		gui.cmbStopBits.setSelectedItem((0xFF&packet.serial_info[0].stop_bits));
		gui.cmbFlow.setSelectedIndex((0xFF&packet.serial_info[0].flow_control));

		if(packet.options.serial_command == 1)
			gui.chckbxEnableAtCommand.setSelected(true);
		else
			gui.chckbxEnableAtCommand.setSelected(false);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[0]));
		gui.txtTrigger1.setText(str);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[1]));
		gui.txtTrigger2.setText(str);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[2]));
		gui.txtTrigger3.setText(str);

		switch((0xFF&packet.options.dhcp_use)) {
		case 0:
			gui.chckbxUseDhcp.setSelected(false);
			break;
		case 1:
			gui.chckbxUseDhcp.setSelected(true);
			break;
		default:
			gui.chckbxUseDhcp.setSelected(false);
			break;
		}

		str = new String(packet.options.pw_setting);
		gui.pwdSettingPassword.setText(str.trim());
		str = new String(packet.options.pw_connect);
		gui.txtConnectionPassword.setText(str.trim());
		switch((0xFF&packet.options.dns_use)) {
		case 0:
			gui.chckbxUseDns.setSelected(false);
			break;
		case 1:
			gui.chckbxUseDns.setSelected(true);
			break;
		default:
			gui.chckbxUseDns.setSelected(false);
			break;
		}
		str = String.format("%d.%d.%d.%d", (0xFF&packet.options.dns_server_ip[0]), (0xFF&packet.options.dns_server_ip[1]), (0xFF&packet.options.dns_server_ip[2]), (0xFF&packet.options.dns_server_ip[3]));
		gui.txtDnsServerIp.setText(str);
		str = new String(packet.options.dns_domain_name);
		gui.txtDomain.setText(str.trim());
		str = new String(packet.options.mqtt_user);
		gui.txtMqttUser.setText(str.trim());
		str = new String(packet.options.mqtt_pw);
		gui.txtMqttPassword.setText(str.trim());
		str = new String(packet.options.mqtt_publish_topic);
		gui.txtMqttPublishTopic.setText(str.trim());
		str = new String(packet.options.mqtt_subscribe_topic);
		gui.txtMqttSubscribeTopic.setText(str.trim());
	}

	public static boolean updateFromPanel(GUI gui, String selectedMac, WIZ550S2E_Config packet) {
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
		
		packet.module_name = gui.txtModuleName.getText().trim().getBytes();

		if(!valid.IpValid(gui.txtIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "IP input error");
			return false;
		}
		str_array = gui.txtIp.getText().split("\\.");
		packet.network_info_common.local_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.local_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.local_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.local_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtGateway.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Gateway input error");
			return false;
		}
		str_array = gui.txtGateway.getText().split("\\.");
		packet.network_info_common.gateway[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.gateway[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.gateway[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.gateway[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtSubnet.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Subnet input error");
			return false;
		}
		str_array = gui.txtSubnet.getText().split("\\.");
		packet.network_info_common.subnet[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.subnet[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.subnet[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.subnet[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtHostIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Host IP input error");
			return false;
		}
		str_array = gui.txtHostIp.getText().split("\\.");
		packet.network_info[0].remote_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info[0].remote_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info[0].remote_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info[0].remote_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.PortValid(gui.txtLocalPort.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Local Port input error");
			return false;
		}
		packet.network_info[0].local_port = (short) (0x0000FFFF&Integer.parseInt(gui.txtLocalPort.getText(), 10));
		if(!valid.PortValid(gui.txtRemotePort.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Remote Port input error");
			return false;
		}
		packet.network_info[0].remote_port = (short) (0x0000FFFF&Integer.parseInt(gui.txtRemotePort.getText(), 10));
		packet.network_info[0].inactivity = (short) (0x0000FFFF&Integer.parseInt(gui.txtInactivity.getText(), 10));
		packet.network_info[0].reconnection = (short) (0x0000FFFF&Integer.parseInt(gui.txtReconnection.getText(), 10));
		packet.network_info[0].packing_time = (short) (0x0000FFFF&Integer.parseInt(gui.txtPackingTime.getText(), 10));
		packet.network_info[0].packing_size = (byte) (0x00FF&Short.parseShort(gui.txtPackingSize.getText(), 10));
		packet.network_info[0].packing_delimiter_length = (byte) (0x00FF&gui.cmbPackingDelimiterLength.getSelectedIndex());
		packet.network_info[0].packing_delimiter[0] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter1.getText(), 16));
		packet.network_info[0].packing_delimiter[1] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter2.getText(), 16));
		packet.network_info[0].packing_delimiter[2] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter3.getText(), 16));
		packet.network_info[0].packing_delimiter[3] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter4.getText(), 16));
		packet.network_info[0].packing_data_appendix = (byte) (0x00FF&gui.cmbDataAppendix.getSelectedIndex());

		if(gui.rdbtnTcpClient.isSelected()) {
			packet.network_info[0].working_mode = 0;
		}
		else if(gui.rdbtnTcpServer.isSelected()) {
			packet.network_info[0].working_mode = 1;
		}
		else if(gui.rdbtnTcpMixed.isSelected()) {
			packet.network_info[0].working_mode = 2;
		}
		else if(gui.rdbtnUdp.isSelected()) {
			packet.network_info[0].working_mode = 3;
		}
		else if(gui.rdbtnMqtt.isSelected()) {
			packet.network_info[0].working_mode = 4;
		}
		else {
			packet.network_info[0].working_mode = 0;
		}

		packet.serial_info[0].baud_rate = (int) (0x00000000FFFFFFFF&Long.parseLong((String) gui.cmbBaudRate.getSelectedItem(), 10));
		packet.serial_info[0].data_bits = (byte) (0x00FF&Short.parseShort((String) gui.cmbDataBits.getSelectedItem(), 10));
		packet.serial_info[0].parity = (byte) (0x00FF&gui.cmbParity.getSelectedIndex());
		packet.serial_info[0].stop_bits = (byte) (0x00FF&Short.parseShort((String) gui.cmbStopBits.getSelectedItem(), 10));
		packet.serial_info[0].flow_control = (byte) (0x00FF&gui.cmbFlow.getSelectedIndex());

		if(gui.chckbxEnableAtCommand.isSelected()) {
			packet.options.serial_command = 1;
		}
		else {
			packet.options.serial_command = 0;
		}
		packet.options.serial_trigger[0] = (byte) (0x00FF&Short.parseShort(gui.txtTrigger1.getText(), 16));
		packet.options.serial_trigger[1] = (byte) (0x00FF&Short.parseShort(gui.txtTrigger2.getText(), 16));
		packet.options.serial_trigger[2] = (byte) (0x00FF&Short.parseShort(gui.txtTrigger3.getText(), 16));
		if(gui.chckbxUseDhcp.isSelected()) {
			packet.options.dhcp_use = 1;
		}
		else {
			packet.options.dhcp_use = 0;
		}
		String password = new String(gui.pwdSettingPassword.getPassword());
		packet.options.pw_setting = password.trim().getBytes();
		packet.options.pw_connect = gui.txtConnectionPassword.getText().trim().getBytes();
		if(gui.chckbxUseDns.isSelected()) {
			packet.options.dns_use = 1;
		}
		else {
			packet.options.dns_use = 0;
		}

		if(!valid.IpValid(gui.txtDnsServerIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "DNS Server IP input error");
			return false;
		}
		str_array = gui.txtDnsServerIp.getText().split("\\.");
		packet.options.dns_server_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.options.dns_server_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.options.dns_server_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.options.dns_server_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		packet.options.dns_domain_name = gui.txtDomain.getText().trim().getBytes();
		
		packet.options.mqtt_user = gui.txtMqttUser.getText().trim().getBytes();
		packet.options.mqtt_pw = gui.txtMqttPassword.getText().trim().getBytes();
		packet.options.mqtt_publish_topic = gui.txtMqttPublishTopic.getText().trim().getBytes();
		packet.options.mqtt_subscribe_topic = gui.txtMqttSubscribeTopic.getText().trim().getBytes();

		return true;
	}
	/* <--WIZ550S2E */

	/* WIZ550WEB--> */
	public static void updateToPanel(GUI gui, WIZ550WEB_Config packet) {
		clearPanel(gui);
		setPanel(gui, WIZ550WEB);

		String str;

		str = new String(packet.module_name);
		gui.txtModuleName.setText(str.trim());
		if((0xFF&packet.fw_ver[0]) > 100) {
			str = String.format("Bootloader %d.%d.%d", (0xFF&packet.fw_ver[0]) - 100, (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		else {
			str = String.format("%d.%d.%d", (0xFF&packet.fw_ver[0]), (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		gui.txtFirmwareVersion.setText(str);

		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.local_ip[0]), (0xFF&packet.network_info_common.local_ip[1]), (0xFF&packet.network_info_common.local_ip[2]), (0xFF&packet.network_info_common.local_ip[3]));
		gui.txtIp.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.gateway[0]), (0xFF&packet.network_info_common.gateway[1]), (0xFF&packet.network_info_common.gateway[2]), (0xFF&packet.network_info_common.gateway[3]));
		gui.txtGateway.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.subnet[0]), (0xFF&packet.network_info_common.subnet[1]), (0xFF&packet.network_info_common.subnet[2]), (0xFF&packet.network_info_common.subnet[3]));
		gui.txtSubnet.setText(str);

		str = new String(packet.options.pw_setting);
		gui.pwdSettingPassword.setText(str.trim());
	}

	public static boolean updateFromPanel(GUI gui, String selectedMac, WIZ550WEB_Config packet) {
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
		
		packet.module_name = gui.txtModuleName.getText().trim().getBytes();

		if(!valid.IpValid(gui.txtIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "IP input error");
			return false;
		}
		str_array = gui.txtIp.getText().split("\\.");
		packet.network_info_common.local_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.local_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.local_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.local_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtGateway.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Gateway input error");
			return false;
		}
		str_array = gui.txtGateway.getText().split("\\.");
		packet.network_info_common.gateway[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.gateway[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.gateway[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.gateway[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtSubnet.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Subnet input error");
			return false;
		}
		str_array = gui.txtSubnet.getText().split("\\.");
		packet.network_info_common.subnet[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.subnet[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.subnet[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.subnet[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		String password = new String(gui.pwdSettingPassword.getPassword());
		packet.options.pw_setting = password.trim().getBytes();

		return true;
	}
	/* <--WIZ550WEB */
	/* WIZ550SR--> */
	public static void updateToPanel(GUI gui, WIZ550SR_Config packet) {
		clearPanel(gui);
		setPanel(gui, WIZ550SR);

		String str;

		str = new String(packet.module_name);
		gui.txtModuleName.setText(str.trim());
		if((0xFF&packet.fw_ver[0]) > 100) {
			str = String.format("Bootloader %d.%d.%d", (0xFF&packet.fw_ver[0]) - 100, (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		else {
			str = String.format("%d.%d.%d", (0xFF&packet.fw_ver[0]), (0xFF&packet.fw_ver[1]), (0xFF&packet.fw_ver[2]));
		}
		gui.txtFirmwareVersion.setText(str);

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
		gui.txtNetworkStatus.setText(str);

		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.local_ip[0]), (0xFF&packet.network_info_common.local_ip[1]), (0xFF&packet.network_info_common.local_ip[2]), (0xFF&packet.network_info_common.local_ip[3]));
		gui.txtIp.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.gateway[0]), (0xFF&packet.network_info_common.gateway[1]), (0xFF&packet.network_info_common.gateway[2]), (0xFF&packet.network_info_common.gateway[3]));
		gui.txtGateway.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info_common.subnet[0]), (0xFF&packet.network_info_common.subnet[1]), (0xFF&packet.network_info_common.subnet[2]), (0xFF&packet.network_info_common.subnet[3]));
		gui.txtSubnet.setText(str);
		str = String.format("%d.%d.%d.%d", (0xFF&packet.network_info[0].remote_ip[0]), (0xFF&packet.network_info[0].remote_ip[1]), (0xFF&packet.network_info[0].remote_ip[2]), (0xFF&packet.network_info[0].remote_ip[3]));
		gui.txtHostIp.setText(str);

		str = String.format("%d", (0xFFFF&packet.network_info[0].local_port));
		gui.txtLocalPort.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].remote_port));
		gui.txtRemotePort.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].inactivity));
		gui.txtInactivity.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].reconnection));
		gui.txtReconnection.setText(str);
		str = String.format("%d", (0xFFFF&packet.network_info[0].packing_time));
		gui.txtPackingTime.setText(str);
		str = String.format("%d", (0xFF&packet.network_info[0].packing_size));
		gui.txtPackingSize.setText(str);
		gui.cmbPackingDelimiterLength.setSelectedIndex((0xFF&packet.network_info[0].packing_delimiter_length));
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[0]));
		gui.txtPackingDelimiter1.setText(str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[1]));
		gui.txtPackingDelimiter2.setText(str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[2]));
		gui.txtPackingDelimiter3.setText(str);
		str = String.format("%02X", (0xFF&packet.network_info[0].packing_delimiter[3]));
		gui.txtPackingDelimiter4.setText(str);
		gui.cmbDataAppendix.setSelectedIndex((0xFF&packet.network_info[0].packing_data_appendix));

		switch((0xFF&packet.network_info[0].working_mode)) {
		case 0:
			gui.rdbtnTcpClient.setSelected(true);
			break;
		case 1:
			gui.rdbtnTcpServer.setSelected(true);
			break;
		case 2:
			gui.rdbtnTcpMixed.setSelected(true);
			break;
		case 3:
			gui.rdbtnUdp.setSelected(true);
			break;
		default:
			break;
		}

		str = String.format("%d", (0xFFFFFFFF&packet.serial_info[0].baud_rate));
		gui.cmbBaudRate.setSelectedItem(str);
		str = String.format("%d", (0xFF&packet.serial_info[0].data_bits));
		gui.cmbDataBits.setSelectedItem(str);
		gui.cmbParity.setSelectedIndex((0xFF&packet.serial_info[0].parity));
		gui.cmbStopBits.setSelectedItem((0xFF&packet.serial_info[0].stop_bits));
		gui.cmbFlow.setSelectedIndex((0xFF&packet.serial_info[0].flow_control));

		if(packet.options.serial_command == 1)
			gui.chckbxEnableAtCommand.setSelected(true);
		else
			gui.chckbxEnableAtCommand.setSelected(false);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[0]));
		gui.txtTrigger1.setText(str);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[1]));
		gui.txtTrigger2.setText(str);
		str = String.format("%02X", (0xFF&packet.options.serial_trigger[2]));
		gui.txtTrigger3.setText(str);

		switch((0xFF&packet.options.dhcp_use)) {
		case 0:
			gui.chckbxUseDhcp.setSelected(false);
			break;
		case 1:
			gui.chckbxUseDhcp.setSelected(true);
			break;
		default:
			gui.chckbxUseDhcp.setSelected(false);
			break;
		}

		str = new String(packet.options.pw_setting);
		gui.pwdSettingPassword.setText(str.trim());
		str = new String(packet.options.pw_connect);
		gui.txtConnectionPassword.setText(str.trim());
		switch((0xFF&packet.options.dns_use)) {
		case 0:
			gui.chckbxUseDns.setSelected(false);
			break;
		case 1:
			gui.chckbxUseDns.setSelected(true);
			break;
		default:
			gui.chckbxUseDns.setSelected(false);
			break;
		}
		str = String.format("%d.%d.%d.%d", (0xFF&packet.options.dns_server_ip[0]), (0xFF&packet.options.dns_server_ip[1]), (0xFF&packet.options.dns_server_ip[2]), (0xFF&packet.options.dns_server_ip[3]));
		gui.txtDnsServerIp.setText(str);
		str = new String(packet.options.dns_domain_name);
		gui.txtDomain.setText(str.trim());
	}

	public static boolean updateFromPanel(GUI gui, String selectedMac, WIZ550SR_Config packet) {
		InputValidation valid = new InputValidation();
		String[] str_array;

		if(selectedMac == null)
			return false;
		if(packet == null)
			packet = new WIZ550SR_Config();

		str_array = selectedMac.split("\\:");
		packet.network_info_common.mac[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 16));
		packet.network_info_common.mac[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 16));
		packet.network_info_common.mac[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 16));
		packet.network_info_common.mac[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 16));
		packet.network_info_common.mac[4] = (byte) (0x00FF&Short.parseShort(str_array[4], 16));
		packet.network_info_common.mac[5] = (byte) (0x00FF&Short.parseShort(str_array[5], 16));
		
		packet.module_name = gui.txtModuleName.getText().trim().getBytes();

		if(!valid.IpValid(gui.txtIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "IP input error");
			return false;
		}
		str_array = gui.txtIp.getText().split("\\.");
		packet.network_info_common.local_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.local_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.local_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.local_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtGateway.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Gateway input error");
			return false;
		}
		str_array = gui.txtGateway.getText().split("\\.");
		packet.network_info_common.gateway[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.gateway[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.gateway[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.gateway[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtSubnet.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Subnet input error");
			return false;
		}
		str_array = gui.txtSubnet.getText().split("\\.");
		packet.network_info_common.subnet[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info_common.subnet[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info_common.subnet[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info_common.subnet[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.IpValid(gui.txtHostIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Host IP input error");
			return false;
		}
		str_array = gui.txtHostIp.getText().split("\\.");
		packet.network_info[0].remote_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.network_info[0].remote_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.network_info[0].remote_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.network_info[0].remote_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));

		if(!valid.PortValid(gui.txtLocalPort.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Local Port input error");
			return false;
		}
		packet.network_info[0].local_port = (short) (0x0000FFFF&Integer.parseInt(gui.txtLocalPort.getText(), 10));
		if(!valid.PortValid(gui.txtRemotePort.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Remote Port input error");
			return false;
		}
		packet.network_info[0].remote_port = (short) (0x0000FFFF&Integer.parseInt(gui.txtRemotePort.getText(), 10));
		packet.network_info[0].inactivity = (short) (0x0000FFFF&Integer.parseInt(gui.txtInactivity.getText(), 10));
		packet.network_info[0].reconnection = (short) (0x0000FFFF&Integer.parseInt(gui.txtReconnection.getText(), 10));
		packet.network_info[0].packing_time = (short) (0x0000FFFF&Integer.parseInt(gui.txtPackingTime.getText(), 10));
		packet.network_info[0].packing_size = (byte) (0x00FF&Short.parseShort(gui.txtPackingSize.getText(), 10));
		packet.network_info[0].packing_delimiter_length = (byte) (0x00FF&gui.cmbPackingDelimiterLength.getSelectedIndex());
		packet.network_info[0].packing_delimiter[0] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter1.getText(), 16));
		packet.network_info[0].packing_delimiter[1] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter2.getText(), 16));
		packet.network_info[0].packing_delimiter[2] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter3.getText(), 16));
		packet.network_info[0].packing_delimiter[3] = (byte) (0x00FF&Short.parseShort(gui.txtPackingDelimiter4.getText(), 16));
		packet.network_info[0].packing_data_appendix = (byte) (0x00FF&gui.cmbDataAppendix.getSelectedIndex());

		if(gui.rdbtnTcpClient.isSelected()) {
			packet.network_info[0].working_mode = 0;
		}
		else if(gui.rdbtnTcpServer.isSelected()) {
			packet.network_info[0].working_mode = 1;
		}
		else if(gui.rdbtnTcpMixed.isSelected()) {
			packet.network_info[0].working_mode = 2;
		}
		else if(gui.rdbtnUdp.isSelected()) {
			packet.network_info[0].working_mode = 3;
		}
		else {
			packet.network_info[0].working_mode = 0;
		}

		packet.serial_info[0].baud_rate = (int) (0x00000000FFFFFFFF&Long.parseLong((String) gui.cmbBaudRate.getSelectedItem(), 10));
		packet.serial_info[0].data_bits = (byte) (0x00FF&Short.parseShort((String) gui.cmbDataBits.getSelectedItem(), 10));
		packet.serial_info[0].parity = (byte) (0x00FF&gui.cmbParity.getSelectedIndex());
		packet.serial_info[0].stop_bits = (byte) (0x00FF&Short.parseShort((String) gui.cmbStopBits.getSelectedItem(), 10));
		packet.serial_info[0].flow_control = (byte) (0x00FF&gui.cmbFlow.getSelectedIndex());

		if(gui.chckbxEnableAtCommand.isSelected()) {
			packet.options.serial_command = 1;
		}
		else {
			packet.options.serial_command = 0;
		}
		packet.options.serial_trigger[0] = (byte) (0x00FF&Short.parseShort(gui.txtTrigger1.getText(), 16));
		packet.options.serial_trigger[1] = (byte) (0x00FF&Short.parseShort(gui.txtTrigger2.getText(), 16));
		packet.options.serial_trigger[2] = (byte) (0x00FF&Short.parseShort(gui.txtTrigger3.getText(), 16));
		if(gui.chckbxUseDhcp.isSelected()) {
			packet.options.dhcp_use = 1;
		}
		else {
			packet.options.dhcp_use = 0;
		}
		String password = new String(gui.pwdSettingPassword.getPassword());
		packet.options.pw_setting = password.trim().getBytes();
		packet.options.pw_connect = gui.txtConnectionPassword.getText().trim().getBytes();
		if(gui.chckbxUseDns.isSelected()) {
			packet.options.dns_use = 1;
		}
		else {
			packet.options.dns_use = 0;
		}

		if(!valid.IpValid(gui.txtDnsServerIp.getText().trim())) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "DNS Server IP input error");
			return false;
		}
		str_array = gui.txtDnsServerIp.getText().split("\\.");
		packet.options.dns_server_ip[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
		packet.options.dns_server_ip[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
		packet.options.dns_server_ip[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
		packet.options.dns_server_ip[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
		packet.options.dns_domain_name = gui.txtDomain.getText().trim().getBytes();

		return true;
	}
	/* <--WIZ550SR */
}
