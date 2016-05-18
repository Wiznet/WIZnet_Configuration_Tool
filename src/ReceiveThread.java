import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * 
 */

/**
 * @author Raphael
 *
 */
public class ReceiveThread extends Thread {
	private DatagramSocket udpsocket = null;
	private ReceiveCallback callback = null;

	public ReceiveThread(DatagramSocket udpsocket, ReceiveCallback callback) {
		this.udpsocket = udpsocket;
		this.callback = callback;
	}

	public void run() {
		byte[] buffer = new byte[1024]; 
		DatagramPacket receivePack = new DatagramPacket(buffer, 1024);
		try {
			while(!Thread.currentThread().isInterrupted()) {
				udpsocket.receive(receivePack);

				if(receivePack.getLength() >= 7) {	// 7 Bytes is the header size
					WIZnet_Header wiznet_header = new WIZnet_Header();
					WIZnet_Header.Header header = new WIZnet_Header.Header();

					byte[] recv = receivePack.getData();
					if(wiznet_header.parse_header(recv, header) == false)
						continue;
					if(header.op_code[1] != wiznet_header.WIZNET_REPLY)
						continue;

					if((header.valid & 0x80) == 0x80)
						wiznet_header.decrypt((byte)(header.valid & 0x7F), recv, header.length);

					if(header.op_code[0] == wiznet_header.DISCOVERY_ALL
							|| header.op_code[0] == wiznet_header.DISCOVERY_PRODUCT_CODE
							|| header.op_code[0] == wiznet_header.DISCOVERY_MAC_ADDRESS
							|| header.op_code[0] == wiznet_header.DISCOVERY_ALIAS
							|| header.op_code[0] == wiznet_header.DISCOVERY_MIXED_COND
							) {
						WIZnet_Header.Discovery_Reply discovery_reply = new WIZnet_Header.Discovery_Reply();

						System.arraycopy(recv, 0, discovery_reply.product_code, 0, 3);
						System.arraycopy(recv, 3, discovery_reply.fw_version, 0, 3);
						System.arraycopy(recv, 6, discovery_reply.mac_address, 0, 6);

						callback.receivedPacket(header.op_code[0], discovery_reply);
						break;
					}
					else if(header.op_code[0] == wiznet_header.GET_INFO) {
						WIZnet_Header.Get_Info_Reply get_info_reply = new WIZnet_Header.Get_Info_Reply();
						short length = (short) ((0xFF&recv[6]) + ((0xFF&recv[7]) << 8));
						byte[] data = new byte[length];

						System.arraycopy(recv, 0, get_info_reply.src_mac_address, 0, 6);
						System.arraycopy(recv, 6, data, 0, data.length);
						get_info_reply.system_info = data;

						callback.receivedPacket(wiznet_header.GET_INFO, get_info_reply);
						break;
					}
					else if(header.op_code[0] == wiznet_header.SET_INFO) {
						WIZnet_Header.Set_Info_Reply set_info_reply = new WIZnet_Header.Set_Info_Reply();
						callback.receivedPacket(wiznet_header.SET_INFO, set_info_reply);
						break;
					}
					else if(header.op_code[0] == wiznet_header.FIRMWARE_UPLOAD_INIT) {
						WIZnet_Header.Firmware_Upload_Init_Reply firmware_upload_init_reply = new WIZnet_Header.Firmware_Upload_Init_Reply();
						callback.receivedPacket(wiznet_header.FIRMWARE_UPLOAD_INIT, firmware_upload_init_reply);
					}
					else if(header.op_code[0] == wiznet_header.FIRMWARE_UPLOAD_DONE) {
						WIZnet_Header.Firmware_Upload_Done_Reply firmware_upload_done_reply = new WIZnet_Header.Firmware_Upload_Done_Reply();
						callback.receivedPacket(wiznet_header.FIRMWARE_UPLOAD_DONE, firmware_upload_done_reply);
						break;
					}
					else if(header.op_code[0] == wiznet_header.REMOTE_RESET) {
						WIZnet_Header.Reset_Reply reset_reply = new WIZnet_Header.Reset_Reply();
						callback.receivedPacket(wiznet_header.REMOTE_RESET, reset_reply);
						break;
					}
					else if(header.op_code[0] == wiznet_header.FACTORY_RESET) {
						WIZnet_Header.Factory_Reset_Reply facroty_reset_reply = new WIZnet_Header.Factory_Reset_Reply();
						callback.receivedPacket(wiznet_header.FACTORY_RESET, facroty_reset_reply);
						break;
					}
					else {
						break;
					}
				}
			}
		} catch (IOException e) {
			udpsocket.close();
			//e.printStackTrace();
		} finally {
		}
	}
}
