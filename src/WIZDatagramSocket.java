import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 
 */

/**
 * @author Raphael
 *
 */
public class WIZDatagramSocket {
	private final int remote_port = 6550;

	private DatagramSocket udpsocket = null;
	private InetAddress inet = null;
	private ReceiveThread receivethread = null;
	private ReceiveCallback callback;

	public WIZDatagramSocket(ReceiveCallback callback) {
		this.callback = callback;
	}

	private void startThread() {
		// Stop Receive Thread If still alive
		if(receivethread != null && receivethread.isAlive())
			receivethread.interrupt();

		// Close datagram socket If still open
		if(udpsocket != null && udpsocket.isClosed() == false)
			udpsocket.close();

		// Open datagram socket
		try {
			udpsocket = new DatagramSocket();
			udpsocket.setReceiveBufferSize(1024 * 1024);
			udpsocket.setSoTimeout(15000);
		} catch (SocketException e) {
			udpsocket.close();
			e.printStackTrace();
			return;
		}

		// Start Receive Thread
		receivethread = new ReceiveThread(this.udpsocket, callback);
		receivethread.start();
	}

	public void discovery(String host, byte op) {
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		startThread();

		WIZnet_Header wiznet_header = new WIZnet_Header();
		byte[] sendMsg = wiznet_header.discovery(op);
		if(host == "255.255.255.255") {
			wiznet_header.set_unicast(false, sendMsg);
		}
		else {
			wiznet_header.set_unicast(true, sendMsg);
		}
		DatagramPacket sendPack = new DatagramPacket(sendMsg, sendMsg.length, inet, remote_port);

		try {
			udpsocket.send(sendPack);
		} catch (IOException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}
	}

	public void get_info(String host, byte[] dst_mac_address) {
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		startThread();

		WIZnet_Header wiznet_header = new WIZnet_Header();
		byte[] sendMsg = wiznet_header.get_info(dst_mac_address);
		if(host == "255.255.255.255") {
			wiznet_header.set_unicast(false, sendMsg);
		}
		else {
			wiznet_header.set_unicast(true, sendMsg);
		}
		DatagramPacket sendPack = new DatagramPacket(sendMsg, sendMsg.length, inet, remote_port);

		try {
			udpsocket.send(sendPack);
		} catch (IOException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}
	}

	public void set_info(String host, byte[] packet, String set_pw) {
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		startThread();

		WIZnet_Header wiznet_header = new WIZnet_Header();
		byte[] mac = null;

		/* WIZ550S2E */
		if(packet[2] == 0x00 && packet[3] == 0x00 && packet[4] == 0x00) {
			WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
			wiz550s2e_config.setData(packet);
			mac = wiz550s2e_config.network_info_common.mac;
		}
		/* WIZ550WEB */
		else if(packet[2] == 0x01 && packet[3] == 0x02 && packet[4] == 0x00) {
			WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
			wiz550web_config.setData(packet);
			mac = wiz550web_config.network_info_common.mac;
		}
		/* WIZ550SR */
		else if(packet[2] == 0x02 && packet[3] == 0x00 && packet[4] == 0x00) {
			WIZ550SR_Config wiz550sr_config = new WIZ550SR_Config();
			wiz550sr_config.setData(packet);
			mac = wiz550sr_config.network_info_common.mac;
		}

		byte[] sendMsg = wiznet_header.set_info(mac, packet, set_pw);
		if(host == "255.255.255.255") {
			wiznet_header.set_unicast(false, sendMsg);
		}
		else {
			wiznet_header.set_unicast(true, sendMsg);
		}
		DatagramPacket sendPack = new DatagramPacket(sendMsg, sendMsg.length, inet, remote_port);

		try {
			udpsocket.send(sendPack);
		} catch (IOException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}

	}

	public void firmware_upload(byte[] packet, byte[] serverIp, short serverPort, String fileName, String set_pw) {
		String ip = null;
		byte[] mac = null;

		/* WIZ550S2E */
		if(packet[2] == 0x00 && packet[3] == 0x00 && packet[4] == 0x00) {
			WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
			wiz550s2e_config.setData(packet);
			ip = String.format("%d.%d.%d.%d",
					(0xFF&wiz550s2e_config.network_info_common.local_ip[0]), (0xFF&wiz550s2e_config.network_info_common.local_ip[1]),
					(0xFF&wiz550s2e_config.network_info_common.local_ip[2]), (0xFF&wiz550s2e_config.network_info_common.local_ip[3]));
			mac = wiz550s2e_config.network_info_common.mac;
		}
		/* WIZ550WEB */
		else if(packet[2] == 0x01 && packet[3] == 0x02 && packet[4] == 0x00) {
			WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
			wiz550web_config.setData(packet);
			ip = String.format("%d.%d.%d.%d",
					(0xFF&wiz550web_config.network_info_common.local_ip[0]), (0xFF&wiz550web_config.network_info_common.local_ip[1]),
					(0xFF&wiz550web_config.network_info_common.local_ip[2]), (0xFF&wiz550web_config.network_info_common.local_ip[3]));
			mac = wiz550web_config.network_info_common.mac;
		}
		/* WIZ550SR */
		else if(packet[2] == 0x02 && packet[3] == 0x00 && packet[4] == 0x00) {
			WIZ550SR_Config wiz550sr_config = new WIZ550SR_Config();
			wiz550sr_config.setData(packet);
			ip = String.format("%d.%d.%d.%d",
					(0xFF&wiz550sr_config.network_info_common.local_ip[0]), (0xFF&wiz550sr_config.network_info_common.local_ip[1]),
					(0xFF&wiz550sr_config.network_info_common.local_ip[2]), (0xFF&wiz550sr_config.network_info_common.local_ip[3]));
			mac = wiz550sr_config.network_info_common.mac;
		}

		try {
			inet = InetAddress.getByName(ip);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		startThread();

		WIZnet_Header wiznet_header = new WIZnet_Header();
		byte[] sendMsg = wiznet_header.firmware_upload(mac, serverIp, serverPort, fileName, set_pw);
		wiznet_header.set_unicast(true, sendMsg);
		DatagramPacket sendPack = new DatagramPacket(sendMsg, sendMsg.length, inet, remote_port);

		try {
			udpsocket.send(sendPack);
		} catch (IOException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}

	}

	public void reset(String host, byte[] dst_mac_address, String set_pw) {
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}

		startThread();

		WIZnet_Header wiznet_header = new WIZnet_Header();
		byte[] sendMsg = wiznet_header.reset(dst_mac_address, set_pw);
		if(host == "255.255.255.255") {
			wiznet_header.set_unicast(false, sendMsg);
		}
		else {
			wiznet_header.set_unicast(true, sendMsg);
		}
		DatagramPacket sendPack = new DatagramPacket(sendMsg, sendMsg.length, inet, remote_port);

		try {
			udpsocket.send(sendPack);
		} catch (IOException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}
	}

	public void factory_reset(String host, byte[] dst_mac_address, String set_pw) {
		try {
			inet = InetAddress.getByName(host);
		} catch (UnknownHostException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}

		startThread();

		WIZnet_Header wiznet_header = new WIZnet_Header();
		byte[] sendMsg = wiznet_header.factory_reset(dst_mac_address, set_pw);
		if(host == "255.255.255.255") {
			wiznet_header.set_unicast(false, sendMsg);
		}
		else {
			wiznet_header.set_unicast(true, sendMsg);
		}
		DatagramPacket sendPack = new DatagramPacket(sendMsg, sendMsg.length, inet, remote_port);

		try {
			udpsocket.send(sendPack);
		} catch (IOException e1) {
			udpsocket.close();
			e1.printStackTrace();
		}
	}
}
