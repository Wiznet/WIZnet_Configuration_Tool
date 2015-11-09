import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.RowSpec;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;


public class GUI extends JFrame implements ReceiveCallback {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4765951169868128409L;
	private LinkedList<WIZnet_Header.Discovery_Reply> list = new LinkedList<WIZnet_Header.Discovery_Reply>();
	private WIZDatagramSocket socket = new WIZDatagramSocket(this);
	private String selectedMac;
	private int treeIndex = 0;
	private Timer timer = new Timer(true);

	public JPanel contentPane;
	public JPanel panel_ListOfModules;
	public JPanel panel_SearchMethod;
	public JPanel panel_Network;
	public JPanel panel_Ip;
	public JPanel panel_Port;
	public JPanel panel_WorkingMode;
	public JPanel panel_Timer;
	public JPanel panel_Serial;
	public JPanel panel_Usart;
	public JPanel panel_AtCommand;
	public JPanel panel_PackingConditions;
	public JPanel panel_Char;
	public JPanel panel_Options;
	public JPanel panel_ModuleName;
	public JPanel panel_Password;
	public JPanel panel_Dhcp;
	public JPanel panel_Dns;
	public JPanel panel_Buttons;

	public JTextField txtIp;
	public JTextField txtGateway;
	public JTextField txtSubnet;
	public final ButtonGroup btngrpWorkingMode = new ButtonGroup();
	public final JRadioButton rdbtnTcpClient = new JRadioButton("TCP Client");
	public final JRadioButton rdbtnTcpServer = new JRadioButton("TCP Server");
	public final JRadioButton rdbtnTcpMixed = new JRadioButton("TCP Mixed");
	public final JRadioButton rdbtnUdp = new JRadioButton("UDP");
	public final JComboBox<Object> cmbBaudRate = new JComboBox<Object>();
	public final JComboBox<Object> cmbDataBits = new JComboBox<Object>();
	public final JComboBox<Object> cmbParity = new JComboBox<Object>();
	public final JComboBox<Object> cmbStopBits = new JComboBox<Object>();
	public final JComboBox<Object> cmbFlow = new JComboBox<Object>();
	public final JCheckBox chckbxEnableAtCommand = new JCheckBox("Enable");
	public JTextField txtTrigger1;
	public JTextField txtTrigger2;
	public JTextField txtTrigger3;
	public JTextField txtLocalPort;
	public JTextField txtRemotePort;
	public JTextField txtHostIp;
	public final JTree tree = new JTree();
	public JTextField txtFirmwareVersion;
	public JTextField txtNetworkStatus;
	public JTextField txtInactivity;
	public JTextField txtReconnection;
	public JTextField txtPackingTime;
	public JTextField txtPackingSize;
	public final JComboBox<Object> cmbPackingDelimiterLength = new JComboBox<Object>();
	public JTextField txtPackingDelimiter1;
	public JTextField txtPackingDelimiter2;
	public JTextField txtPackingDelimiter3;
	public JTextField txtPackingDelimiter4;
	public final JCheckBox chckbxUseDhcp = new JCheckBox("Use DHCP");
	public final JComboBox<Object> cmbDataAppendix = new JComboBox<Object>();
	public JTextField txtConnectionPassword;
	public final JCheckBox chckbxUseDns = new JCheckBox("Use DNS");
	public JTextField txtDnsServerIp;
	public JTextField txtDomain;
	public final ButtonGroup btngrpSearchMethod = new ButtonGroup();
	public final JRadioButton rdbtnBroadcast = new JRadioButton("Broadcast");
	public final JRadioButton rdbtnIpAddress = new JRadioButton("IP Address");
	public final JRadioButton rdbtnMacAddress = new JRadioButton("MAC Address");
	public JTextField txtIpAddress;
	public JTextField txtMacAddress;
	public JTextField txtModuleName;
	public JPasswordField pwdSettingPassword;
	public JButton btnSearch = new JButton("Search");
	private JScrollPane scrollPane;

	private class treeSelectionTimer extends TimerTask {
		public void run() {
			if(treeIndex != 0) {
				// 가장 최근에 추가된 MAC 주소를 선택하도록 한다.
				//tree.setSelectionRow(treeIndex);
				// 가장 상단에 추가된 MAC 주소를 선택하도록 한다.
				tree.setSelectionRow(2);
			}

			btnSearch.setEnabled(true);
			btnSearch.requestFocus();

			timer.cancel();
			timer = new Timer();
		}
	}

	private void treeUpdate(WIZnet_Header.Discovery_Reply packet) {
		boolean exist;
		int i;

		String module_type = new String();
		String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
				packet.mac_address[0], packet.mac_address[1],
				packet.mac_address[2], packet.mac_address[3],
				packet.mac_address[4], packet.mac_address[5]);

		// Search Method가 Mac Address로 설정된 경우
		if(rdbtnMacAddress.isSelected() && txtMacAddress.getText().length() != 0) {
			// Mac Address를 입력 받는 텍스트 박스의 문자열과 현재 수신된 패킷의 MAC Address가 동일한지 검사
			if(txtMacAddress.getText().trim().toUpperCase(Locale.ENGLISH).equals(mac_address.toUpperCase(Locale.ENGLISH)) == false)
				return;
		}

		list.add(packet);

		DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
		DefaultMutableTreeNode child = null;

		// 모듈의 타입을 확인한다.
		if(packet.product_code[0] == 0x00 && packet.product_code[1] == 0x00 && packet.product_code[2] == 0x00) {
			module_type = "WIZ550S2E";
		}
		else if(packet.product_code[0] == 0x01 && packet.product_code[1] == 0x02 && packet.product_code[2] == 0x00) {
			module_type = "WIZ550WEB";
		}
		else {
			return;
		}

		// 모듈을 타입별로 구분하기 위해 트리에 카테고리를 만든다.
		treeIndex = root.getChildCount();
		if(treeIndex == 0) {
			// 현재 트리에 아무런 카테고리가 없는 경우
			child = new DefaultMutableTreeNode(module_type);
			root.add(child);
			treeIndex++;
		}
		else {
			// 현재 트리에 1개 이상의 카테고리가 있는 경우
			exist = false;
			child = (DefaultMutableTreeNode)root.getFirstChild();

			for(i=0; i<root.getChildCount(); i++) {
				if(child.getUserObject() == module_type) {
					exist = true;
					break;
				}
				treeIndex += child.getChildCount();
				child = (DefaultMutableTreeNode) root.getChildAfter(child);
			}

			// 모듈의 타입과 동일한 카테고리가 없는 경우
			if(exist == false) {
				child = new DefaultMutableTreeNode(module_type);
				root.add(child);
				treeIndex++;
			}
		}

		// 위에서 선택된 카테고리에 수신한 패킷의 MAC 주소를 추가한다.
		DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(mac_address);
		child.add(leaf);
		model.reload(root);

		// 모든 카테고리를 확장한다.
		for (int j = 0; j < tree.getRowCount(); j++) {
			tree.expandRow(j);
			//tree.collapseRow(j);
		}

		// 가장 최근에 추가된 MAC 주소를 선택하도록 한다.
		treeIndex += model.getIndexOfChild(child, leaf) + 1;
		timer.schedule(new treeSelectionTimer(), 500);
	}

	// ReceiveThread가 패킷을 수신하면 이 함수를 호출한다.
	public void receivedPacket(byte op, Object packet) {
		WIZnet_Header wiznet_header = new WIZnet_Header();

		if((byte)(op & 0xF0) == (byte)wiznet_header.DISCOVERY) {
			WIZnet_Header.Discovery_Reply discovery_reply = (WIZnet_Header.Discovery_Reply) packet;
			list.add(discovery_reply);
			treeUpdate(discovery_reply);
		}
		else if(op == wiznet_header.GET_INFO) {
			WIZnet_Header.Get_Info_Reply get_info_reply = (WIZnet_Header.Get_Info_Reply) packet;

			/* WIZ550S2E */
			if(get_info_reply.system_info[2] == 0x00 && get_info_reply.system_info[3] == 0x00 && get_info_reply.system_info[4] == 0x00) {
				WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
				wiz550s2e_config.setData(get_info_reply.system_info);
				PanelManager.updateToPanel(this, wiz550s2e_config);
			}
			/* WIZ550WEB */
			else if(get_info_reply.system_info[2] == 0x01 && get_info_reply.system_info[3] == 0x02 && get_info_reply.system_info[4] == 0x00) {
				WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
				wiz550web_config.setData(get_info_reply.system_info);
				PanelManager.updateToPanel(this, wiz550web_config);
			}
		}
		else if(op == wiznet_header.SET_INFO) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Success Set");
		}
		else if(op == wiznet_header.FIRMWARE_UPLOAD_INIT) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Firmware Uploading will Start");
		}
		else if(op == wiznet_header.FIRMWARE_UPLOAD_DONE) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Success Firmware Uploading");
		}
		else if(op == wiznet_header.REMOTE_RESET) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Success Reset");
		}
		else if(op == wiznet_header.FACTORY_RESET) {
			JFrame frame = new JFrame();
			JOptionPane.showMessageDialog(frame, "Success Factory Reset");
		}
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setResizable(false);
		setTitle("WIZnet Configuration Tool Version 1.02");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(860, 710);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{210, 210, 210, 210, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		panel_ListOfModules = new JPanel();
		panel_ListOfModules.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "List of Modules", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_ListOfModules = new GridBagConstraints();
		gbc_panel_ListOfModules.insets = new Insets(0, 0, 5, 5);
		gbc_panel_ListOfModules.fill = GridBagConstraints.BOTH;
		gbc_panel_ListOfModules.gridx = 0;
		gbc_panel_ListOfModules.gridy = 0;
		contentPane.add(panel_ListOfModules, gbc_panel_ListOfModules);
		panel_ListOfModules.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_ListOfModules.add(scrollPane, "2, 2, fill, fill");
				scrollPane.setViewportView(tree);
		
				tree.addTreeSelectionListener(new TreeSelectionListener() {
					public void valueChanged(TreeSelectionEvent e) {
						JTree tree = (JTree) e.getSource();
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		
						selectedMac = null;
						if(selectedNode == null) return;
						String selectedNodeName = selectedNode.toString();
		
						if(selectedNode.isLeaf()) {
							WIZnet_Header.Discovery_Reply discovery_reply;
							for(int i=0; i<list.size(); i++) {
								discovery_reply = list.get(i);
								String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
										discovery_reply.mac_address[0], discovery_reply.mac_address[1],
										discovery_reply.mac_address[2], discovery_reply.mac_address[3],
										discovery_reply.mac_address[4], discovery_reply.mac_address[5]);
								if(selectedNodeName.equals(mac_address)) {
									selectedMac = mac_address;
									socket.get_info("255.255.255.255", discovery_reply.mac_address);
									break;
								}
							}
						}
					}
				});
				
				tree.setModel(new DefaultTreeModel(
					new DefaultMutableTreeNode("WIZnet")
				));
		
		panel_SearchMethod = new JPanel();
		panel_SearchMethod.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search Method", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_ListOfModules.add(panel_SearchMethod, "2, 4, fill, fill");
		panel_SearchMethod.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		rdbtnBroadcast.setEnabled(false);
		
		rdbtnBroadcast.setSelected(true);
		rdbtnBroadcast.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				JRadioButton radiobutton = (JRadioButton) e.getSource();

				if(radiobutton.isSelected()) {
					txtIpAddress.setEnabled(false);
					txtMacAddress.setEnabled(false);
				}
			}
		});
		btngrpSearchMethod.add(rdbtnBroadcast);
		panel_SearchMethod.add(rdbtnBroadcast, "2, 2");
		rdbtnIpAddress.setEnabled(false);
		
		rdbtnIpAddress.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				JRadioButton radiobutton = (JRadioButton) e.getSource();

				if(radiobutton.isSelected()) {
					txtIpAddress.setEnabled(true);
					txtMacAddress.setEnabled(false);

					txtIpAddress.setVisible(true);
					txtMacAddress.setVisible(false);

					txtIpAddress.requestFocus();
				}
			}
		});
		btngrpSearchMethod.add(rdbtnIpAddress);
		panel_SearchMethod.add(rdbtnIpAddress, "2, 4");
		rdbtnMacAddress.setEnabled(false);
		
		rdbtnMacAddress.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				JRadioButton radiobutton = (JRadioButton) e.getSource();

				if(radiobutton.isSelected()) {
					txtIpAddress.setEnabled(false);
					txtMacAddress.setEnabled(true);

					txtIpAddress.setVisible(false);
					txtMacAddress.setVisible(true);

					txtMacAddress.requestFocus();
				}
			}
		});
		btngrpSearchMethod.add(rdbtnMacAddress);
		panel_SearchMethod.add(rdbtnMacAddress, "2, 6");
		
		txtIpAddress = new JTextField();
		txtIpAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtIpAddress.setEnabled(false);
		panel_SearchMethod.add(txtIpAddress, "2, 8, fill, default");
		txtIpAddress.setColumns(10);
		
		txtMacAddress = new JTextField();
		txtMacAddress.setHorizontalAlignment(SwingConstants.CENTER);
		txtMacAddress.setEnabled(false);
		panel_SearchMethod.add(txtMacAddress, "2, 8, fill, default");
		txtMacAddress.setColumns(10);

		panel_Network = new JPanel();
		panel_Network.setBorder(new TitledBorder(null, "Network Setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Network = new GridBagConstraints();
		gbc_panel_Network.insets = new Insets(0, 0, 5, 5);
		gbc_panel_Network.fill = GridBagConstraints.BOTH;
		gbc_panel_Network.gridx = 1;
		gbc_panel_Network.gridy = 0;
		contentPane.add(panel_Network, gbc_panel_Network);
		GridBagLayout gbl_panel_Network = new GridBagLayout();
		gbl_panel_Network.columnWidths = new int[]{0, 0};
		gbl_panel_Network.rowHeights = new int[]{111, 0, 0, 0, 0};
		gbl_panel_Network.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_Network.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_Network.setLayout(gbl_panel_Network);
		
		panel_Ip = new JPanel();
		panel_Ip.setBorder(new TitledBorder(null, "IP", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Ip = new GridBagConstraints();
		gbc_panel_Ip.insets = new Insets(0, 0, 5, 0);
		gbc_panel_Ip.fill = GridBagConstraints.BOTH;
		gbc_panel_Ip.gridx = 0;
		gbc_panel_Ip.gridy = 0;
		panel_Network.add(panel_Ip, gbc_panel_Ip);
		panel_Ip.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("21px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblIp = new JLabel("IP");
		panel_Ip.add(lblIp, "2, 1, right, default");
		
		txtIp = new JTextField();
		txtIp.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Ip.add(txtIp, "4, 1, fill, default");
		txtIp.setColumns(10);
		
		JLabel lblGateway = new JLabel("Gateway");
		panel_Ip.add(lblGateway, "2, 3, right, default");
		
		txtGateway = new JTextField();
		txtGateway.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Ip.add(txtGateway, "4, 3, fill, default");
		txtGateway.setColumns(10);
		
		JLabel lblSubnet = new JLabel("Subnet");
		panel_Ip.add(lblSubnet, "2, 5, right, default");
		
		txtSubnet = new JTextField();
		txtSubnet.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Ip.add(txtSubnet, "4, 5, fill, default");
		txtSubnet.setColumns(10);
		
		JLabel lblHostIp = new JLabel("Host IP");
		panel_Ip.add(lblHostIp, "2, 7, right, default");
		
		txtHostIp = new JTextField();
		txtHostIp.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Ip.add(txtHostIp, "4, 7, fill, default");
		txtHostIp.setColumns(10);
		
		panel_Port = new JPanel();
		panel_Port.setBorder(new TitledBorder(null, "Port", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Port = new GridBagConstraints();
		gbc_panel_Port.insets = new Insets(0, 0, 5, 0);
		gbc_panel_Port.fill = GridBagConstraints.BOTH;
		gbc_panel_Port.gridx = 0;
		gbc_panel_Port.gridy = 1;
		panel_Network.add(panel_Port, gbc_panel_Port);
		panel_Port.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblLocalPort = new JLabel("Local Port");
		panel_Port.add(lblLocalPort, "2, 2, right, default");
		
		txtLocalPort = new JTextField();
		txtLocalPort.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Port.add(txtLocalPort, "4, 2, fill, default");
		txtLocalPort.setColumns(10);
		
		JLabel lblRemotePort = new JLabel("Remote Port");
		panel_Port.add(lblRemotePort, "2, 4, right, default");
		
		txtRemotePort = new JTextField();
		txtRemotePort.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Port.add(txtRemotePort, "4, 4, fill, default");
		txtRemotePort.setColumns(10);
		
		panel_WorkingMode = new JPanel();
		panel_WorkingMode.setBorder(new TitledBorder(null, "Working Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_WorkingMode = new GridBagConstraints();
		gbc_panel_WorkingMode.insets = new Insets(0, 0, 5, 0);
		gbc_panel_WorkingMode.fill = GridBagConstraints.BOTH;
		gbc_panel_WorkingMode.gridx = 0;
		gbc_panel_WorkingMode.gridy = 2;
		panel_Network.add(panel_WorkingMode, gbc_panel_WorkingMode);
		panel_WorkingMode.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		btngrpWorkingMode.add(rdbtnTcpClient);
		panel_WorkingMode.add(rdbtnTcpClient, "2, 2");
		
		btngrpWorkingMode.add(rdbtnTcpServer);
		panel_WorkingMode.add(rdbtnTcpServer, "2, 4");
		
		btngrpWorkingMode.add(rdbtnTcpMixed);
		panel_WorkingMode.add(rdbtnTcpMixed, "2, 6");
		
		btngrpWorkingMode.add(rdbtnUdp);
		panel_WorkingMode.add(rdbtnUdp, "2, 8");
		
		panel_Timer = new JPanel();
		panel_Timer.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Timer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Timer = new GridBagConstraints();
		gbc_panel_Timer.fill = GridBagConstraints.BOTH;
		gbc_panel_Timer.gridx = 0;
		gbc_panel_Timer.gridy = 3;
		panel_Network.add(panel_Timer, gbc_panel_Timer);
		panel_Timer.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblInactivity = new JLabel("Inactivity");
		panel_Timer.add(lblInactivity, "2, 2");
		
		txtInactivity = new JTextField();
		txtInactivity.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Timer.add(txtInactivity, "2, 4, 3, 1, fill, default");
		txtInactivity.setColumns(10);
		
		JLabel lblSeconds = new JLabel("Seconds");
		panel_Timer.add(lblSeconds, "6, 4");
		
		JLabel lblReconnection = new JLabel("Reconnection");
		panel_Timer.add(lblReconnection, "2, 6");
		
		txtReconnection = new JTextField();
		txtReconnection.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Timer.add(txtReconnection, "2, 8, 3, 1, fill, default");
		txtReconnection.setColumns(10);
		
		JLabel lblMs = new JLabel("ms");
		panel_Timer.add(lblMs, "6, 8");
		
		panel_Serial = new JPanel();
		panel_Serial.setBorder(new TitledBorder(null, "Serial Setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Serial = new GridBagConstraints();
		gbc_panel_Serial.insets = new Insets(0, 0, 5, 5);
		gbc_panel_Serial.fill = GridBagConstraints.BOTH;
		gbc_panel_Serial.gridx = 2;
		gbc_panel_Serial.gridy = 0;
		contentPane.add(panel_Serial, gbc_panel_Serial);
		GridBagLayout gbl_panel_Serial = new GridBagLayout();
		gbl_panel_Serial.columnWidths = new int[]{0, 0};
		gbl_panel_Serial.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_Serial.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_Serial.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel_Serial.setLayout(gbl_panel_Serial);
		
		panel_Usart = new JPanel();
		panel_Usart.setBorder(new TitledBorder(null, "USART", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Usart = new GridBagConstraints();
		gbc_panel_Usart.insets = new Insets(0, 0, 5, 0);
		gbc_panel_Usart.fill = GridBagConstraints.BOTH;
		gbc_panel_Usart.gridx = 0;
		gbc_panel_Usart.gridy = 0;
		panel_Serial.add(panel_Usart, gbc_panel_Usart);
		panel_Usart.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblBaudRate = new JLabel("Baud Rate");
		panel_Usart.add(lblBaudRate, "2, 2, right, default");
		
		cmbBaudRate.setModel(new DefaultComboBoxModel<Object>(new String[] {"300", "600", "1200", "2400", "4800", "9600", "19200", "38400", "57600", "115200", "230400"}));
		panel_Usart.add(cmbBaudRate, "4, 2, fill, default");
		
		JLabel lblDataBits = new JLabel("Data Bits");
		panel_Usart.add(lblDataBits, "2, 4, right, default");
		
		cmbDataBits.setModel(new DefaultComboBoxModel<Object>(new String[] {"7", "8"}));
		panel_Usart.add(cmbDataBits, "4, 4, fill, default");
		
		JLabel lblParity = new JLabel("Parity");
		panel_Usart.add(lblParity, "2, 6, right, default");
		
		cmbParity.setModel(new DefaultComboBoxModel<Object>(new String[] {"None", "Odd", "Even"}));
		panel_Usart.add(cmbParity, "4, 6, fill, default");
		
		JLabel lblStopBits = new JLabel("Stop Bits");
		panel_Usart.add(lblStopBits, "2, 8, right, default");
		
		cmbStopBits.setModel(new DefaultComboBoxModel<Object>(new String[] {"1", "2"}));
		panel_Usart.add(cmbStopBits, "4, 8, fill, default");
		
		JLabel lblFlow = new JLabel("Flow");
		panel_Usart.add(lblFlow, "2, 10, right, default");
		
		cmbFlow.setModel(new DefaultComboBoxModel<Object>(new String[] {"None", "RTS/CTS", "RS422", "RS485"}));
		panel_Usart.add(cmbFlow, "4, 10, fill, default");
		
		panel_AtCommand = new JPanel();
		panel_AtCommand.setBorder(new TitledBorder(null, "AT Command", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_AtCommand = new GridBagConstraints();
		gbc_panel_AtCommand.insets = new Insets(0, 0, 5, 0);
		gbc_panel_AtCommand.fill = GridBagConstraints.BOTH;
		gbc_panel_AtCommand.gridx = 0;
		gbc_panel_AtCommand.gridy = 1;
		panel_Serial.add(panel_AtCommand, gbc_panel_AtCommand);
		panel_AtCommand.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		chckbxEnableAtCommand.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JCheckBox checkbox = (JCheckBox) e.getSource();

				if(checkbox.isSelected()) {
					txtTrigger1.setEnabled(true);
					txtTrigger2.setEnabled(true);
					txtTrigger3.setEnabled(true);
				}
				else {
					txtTrigger1.setEnabled(false);
					txtTrigger2.setEnabled(false);
					txtTrigger3.setEnabled(false);
				}
			}
		});
		
		panel_AtCommand.add(chckbxEnableAtCommand, "2, 2, 3, 1");
		
		JLabel lblTriggerCode = new JLabel("Trigger Code (in HEX)");
		panel_AtCommand.add(lblTriggerCode, "2, 4, 5, 1");
		
		txtTrigger1 = new JTextField();
		txtTrigger1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_AtCommand.add(txtTrigger1, "2, 6, fill, default");
		txtTrigger1.setColumns(10);
		
		txtTrigger2 = new JTextField();
		txtTrigger2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_AtCommand.add(txtTrigger2, "4, 6, fill, default");
		txtTrigger2.setColumns(10);
		
		txtTrigger3 = new JTextField();
		txtTrigger3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_AtCommand.add(txtTrigger3, "6, 6, fill, default");
		txtTrigger3.setColumns(10);
		
		panel_PackingConditions = new JPanel();
		panel_PackingConditions.setBorder(new TitledBorder(null, "Packing Conditions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_PackingConditions = new GridBagConstraints();
		gbc_panel_PackingConditions.insets = new Insets(0, 0, 5, 0);
		gbc_panel_PackingConditions.fill = GridBagConstraints.BOTH;
		gbc_panel_PackingConditions.gridx = 0;
		gbc_panel_PackingConditions.gridy = 2;
		panel_Serial.add(panel_PackingConditions, gbc_panel_PackingConditions);
		panel_PackingConditions.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblTime = new JLabel("Time");
		panel_PackingConditions.add(lblTime, "2, 2, right, default");
		
		txtPackingTime = new JTextField();
		txtPackingTime.setHorizontalAlignment(SwingConstants.CENTER);
		panel_PackingConditions.add(txtPackingTime, "4, 2, 5, 1, fill, default");
		txtPackingTime.setColumns(10);
		
		JLabel lblSize = new JLabel("Size");
		panel_PackingConditions.add(lblSize, "2, 4, right, default");
		
		txtPackingSize = new JTextField();
		txtPackingSize.setHorizontalAlignment(SwingConstants.CENTER);
		panel_PackingConditions.add(txtPackingSize, "4, 4, 5, 1, fill, default");
		txtPackingSize.setColumns(10);
		
		panel_Char = new JPanel();
		panel_PackingConditions.add(panel_Char, "1, 6, 8, 1, fill, fill");
		panel_Char.setBorder(new TitledBorder(null, "Char (in HEX)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_Char.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblLength = new JLabel("Length");
		panel_Char.add(lblLength, "2, 2, 3, 1, right, default");
		panel_Char.add(cmbPackingDelimiterLength, "6, 2, 7, 1");
		
		cmbPackingDelimiterLength.setModel(new DefaultComboBoxModel<Object>(new String[] {"0", "1", "2", "3", "4"}));
		
		txtPackingDelimiter1 = new JTextField();
		panel_Char.add(txtPackingDelimiter1, "4, 4");
		txtPackingDelimiter1.setHorizontalAlignment(SwingConstants.CENTER);
		txtPackingDelimiter1.setColumns(10);
		
		txtPackingDelimiter2 = new JTextField();
		panel_Char.add(txtPackingDelimiter2, "6, 4");
		txtPackingDelimiter2.setHorizontalAlignment(SwingConstants.CENTER);
		txtPackingDelimiter2.setColumns(10);
		
		txtPackingDelimiter3 = new JTextField();
		panel_Char.add(txtPackingDelimiter3, "8, 4");
		txtPackingDelimiter3.setHorizontalAlignment(SwingConstants.CENTER);
		txtPackingDelimiter3.setColumns(10);
		
		txtPackingDelimiter4 = new JTextField();
		panel_Char.add(txtPackingDelimiter4, "10, 4");
		txtPackingDelimiter4.setHorizontalAlignment(SwingConstants.CENTER);
		txtPackingDelimiter4.setColumns(10);
		
		JLabel lblAppendix = new JLabel("Append");
		panel_Char.add(lblAppendix, "2, 6, 3, 1, right, default");
		panel_Char.add(cmbDataAppendix, "6, 6, 7, 1");
		
		cmbDataAppendix.setModel(new DefaultComboBoxModel<Object>(new String[] {"0", "1", "2"}));
		
		panel_Options = new JPanel();
		panel_Options.setBorder(new TitledBorder(null, "Options Setting", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Options = new GridBagConstraints();
		gbc_panel_Options.insets = new Insets(0, 0, 5, 0);
		gbc_panel_Options.fill = GridBagConstraints.BOTH;
		gbc_panel_Options.gridx = 3;
		gbc_panel_Options.gridy = 0;
		contentPane.add(panel_Options, gbc_panel_Options);
		GridBagLayout gbl_panel_Options = new GridBagLayout();
		gbl_panel_Options.columnWidths = new int[]{0, 0};
		gbl_panel_Options.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_Options.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_Options.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_Options.setLayout(gbl_panel_Options);
		
		panel_ModuleName = new JPanel();
		panel_ModuleName.setBorder(new TitledBorder(null, "Module Name", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_ModuleName = new GridBagConstraints();
		gbc_panel_ModuleName.insets = new Insets(0, 0, 5, 0);
		gbc_panel_ModuleName.fill = GridBagConstraints.BOTH;
		gbc_panel_ModuleName.gridx = 0;
		gbc_panel_ModuleName.gridy = 0;
		panel_Options.add(panel_ModuleName, gbc_panel_ModuleName);
		panel_ModuleName.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		txtModuleName = new JTextField();
		txtModuleName.setHorizontalAlignment(SwingConstants.CENTER);
		panel_ModuleName.add(txtModuleName, "2, 2, fill, default");
		txtModuleName.setColumns(10);
		
		panel_Password = new JPanel();
		panel_Password.setBorder(new TitledBorder(null, "Password", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Password = new GridBagConstraints();
		gbc_panel_Password.insets = new Insets(0, 0, 5, 0);
		gbc_panel_Password.fill = GridBagConstraints.BOTH;
		gbc_panel_Password.gridx = 0;
		gbc_panel_Password.gridy = 1;
		panel_Options.add(panel_Password, gbc_panel_Password);
		panel_Password.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblSettingPassword = new JLabel("Setting Password");
		panel_Password.add(lblSettingPassword, "2, 2");
		
		pwdSettingPassword = new JPasswordField();
		pwdSettingPassword.setHorizontalAlignment(SwingConstants.CENTER);
		pwdSettingPassword.setColumns(16);
		panel_Password.add(pwdSettingPassword, "2, 4, fill, default");
		
		JLabel lblConnectionPassword = new JLabel("Connection Password");
		panel_Password.add(lblConnectionPassword, "2, 6");
		
		txtConnectionPassword = new JTextField();
		txtConnectionPassword.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Password.add(txtConnectionPassword, "2, 8, fill, default");
		txtConnectionPassword.setColumns(16);
		
		panel_Dhcp = new JPanel();
		panel_Dhcp.setBorder(new TitledBorder(null, "DHCP", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Dhcp = new GridBagConstraints();
		gbc_panel_Dhcp.insets = new Insets(0, 0, 5, 0);
		gbc_panel_Dhcp.fill = GridBagConstraints.BOTH;
		gbc_panel_Dhcp.gridx = 0;
		gbc_panel_Dhcp.gridy = 2;
		panel_Options.add(panel_Dhcp, gbc_panel_Dhcp);
		panel_Dhcp.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		chckbxUseDhcp.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JCheckBox checkbox = (JCheckBox) e.getSource();

				if(checkbox.isSelected()) {
					txtIp.setEnabled(false);
					txtGateway.setEnabled(false);
					txtSubnet.setEnabled(false);
				}
				else {
					txtIp.setEnabled(true);
					txtGateway.setEnabled(true);
					txtSubnet.setEnabled(true);
				}
			}
		});
		
		panel_Dhcp.add(chckbxUseDhcp, "2, 2");
		
		panel_Dns = new JPanel();
		panel_Dns.setBorder(new TitledBorder(null, "DNS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_Dns = new GridBagConstraints();
		gbc_panel_Dns.fill = GridBagConstraints.BOTH;
		gbc_panel_Dns.gridx = 0;
		gbc_panel_Dns.gridy = 3;
		panel_Options.add(panel_Dns, gbc_panel_Dns);
		panel_Dns.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		chckbxUseDns.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JCheckBox checkbox = (JCheckBox) e.getSource();

				if(checkbox.isSelected()) {
					txtHostIp.setEnabled(false);
				}
				else {
					txtHostIp.setEnabled(true);
				}
			}
		});
		
		panel_Dns.add(chckbxUseDns, "2, 2");
		
		JLabel lblDnsServerIp = new JLabel("DNS Server IP");
		panel_Dns.add(lblDnsServerIp, "2, 4");
		
		txtDnsServerIp = new JTextField();
		txtDnsServerIp.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Dns.add(txtDnsServerIp, "2, 6, fill, default");
		txtDnsServerIp.setColumns(10);
		
		JLabel lblDomain = new JLabel("Domain");
		panel_Dns.add(lblDomain, "2, 8");
		
		txtDomain = new JTextField();
		txtDomain.setHorizontalAlignment(SwingConstants.CENTER);
		panel_Dns.add(txtDomain, "2, 10, fill, default");
		txtDomain.setColumns(10);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 3;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JLabel lblFirmwareVersion = new JLabel("Firmware Version");
		panel.add(lblFirmwareVersion, "2, 2");
		
		JLabel lblNetworkStatus = new JLabel("Network Status");
		panel.add(lblNetworkStatus, "4, 2");
		
		txtFirmwareVersion = new JTextField();
		txtFirmwareVersion.setHorizontalAlignment(SwingConstants.CENTER);
		txtFirmwareVersion.setEditable(false);
		panel.add(txtFirmwareVersion, "2, 4, fill, default");
		txtFirmwareVersion.setColumns(10);
		
		txtNetworkStatus = new JTextField();
		txtNetworkStatus.setHorizontalAlignment(SwingConstants.CENTER);
		txtNetworkStatus.setEditable(false);
		panel.add(txtNetworkStatus, "4, 4, fill, default");
		txtNetworkStatus.setColumns(10);
		
		panel_Buttons = new JPanel();
		GridBagConstraints gbc_panel_Buttons = new GridBagConstraints();
		gbc_panel_Buttons.anchor = GridBagConstraints.NORTH;
		gbc_panel_Buttons.gridwidth = 4;
		gbc_panel_Buttons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_Buttons.gridx = 0;
		gbc_panel_Buttons.gridy = 2;
		contentPane.add(panel_Buttons, gbc_panel_Buttons);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputValidation valid = new InputValidation();
				DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
				WIZnet_Header wiznet_header = new WIZnet_Header();

				treeIndex = 0;
				btnSearch.setEnabled(false);
				timer.schedule(new treeSelectionTimer(), 1000);

				if(rdbtnIpAddress.isSelected()) {
					if(txtIpAddress.getText().length() > 0) {
						if(!valid.IpValid(txtIpAddress.getText().trim())) {
							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Search IP input error");
							return;
						}
						root.removeAllChildren();
						model.reload(root);
						list.clear();
						socket.discovery(txtIpAddress.getText().trim(), wiznet_header.DISCOVERY_ALL);
					}
					else {
						socket.discovery("255.255.255.255", wiznet_header.DISCOVERY_ALL);
						root.removeAllChildren();
						model.reload(root);
						list.clear();
					}
				}
				else if(rdbtnMacAddress.isSelected()) {
					if(txtMacAddress.getText().length() > 0) {
						if(!valid.MacValid(txtMacAddress.getText().trim())) {
							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Search MAC input error");
							return;
						}
					}
					root.removeAllChildren();
					model.reload(root);
					list.clear();
					socket.discovery("255.255.255.255", wiznet_header.DISCOVERY_ALL);
				}
				else {
					root.removeAllChildren();
					model.reload(root);
					list.clear();
					socket.discovery("255.255.255.255", wiznet_header.DISCOVERY_ALL);
				}
			}
		});
		panel_Buttons.add(btnSearch);
		
		JButton btnSetting = new JButton("Setting");
		class btnSettingListener implements ActionListener
		{
			private GUI gui = null;
			btnSettingListener(GUI gui) {
				this.gui = gui;
			}

			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if(selectedNode == null) return;

				WIZnet_Header.Discovery_Reply discovery_reply;
				for(int i=0; i<list.size(); i++) {
					String selectedNodeName = selectedNode.toString();
					discovery_reply = list.get(i);
					String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
							discovery_reply.mac_address[0], discovery_reply.mac_address[1],
							discovery_reply.mac_address[2], discovery_reply.mac_address[3],
							discovery_reply.mac_address[4], discovery_reply.mac_address[5]);

					if(selectedNodeName.equals(mac_address)) {
						PasswordUI dialog = new PasswordUI();
						dialog.setVisible(true);

						if(dialog.isOK == false)
							return;

						String password = new String(dialog.pwdPassword.getPassword());

						/* WIZ550S2E */
						if(discovery_reply.product_code[0] == 0x00 && discovery_reply.product_code[1] == 0x00 && discovery_reply.product_code[2] == 0x00) {
							WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
							if(PanelManager.updateFromPanel(gui, selectedMac, wiz550s2e_config)) {
								socket.set_info("255.255.255.255", wiz550s2e_config.getData(), password.trim());
							}
						}
						/* WIZ550WEB */
						else if(discovery_reply.product_code[0] == 0x01 && discovery_reply.product_code[1] == 0x02 && discovery_reply.product_code[2] == 0x00) {
							WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
							if(PanelManager.updateFromPanel(gui, selectedMac, wiz550web_config)) {
								socket.set_info("255.255.255.255", wiz550web_config.getData(), password.trim());
							}
						}

						break;
					}
				}
			}
		}
		btnSetting.addActionListener(new btnSettingListener(this));
		panel_Buttons.add(btnSetting);
		
		
		JButton btnFwUploading = new JButton("F/W Uploading");
		class btnFwUploadingListener implements ActionListener
		{
			private GUI gui = null;
			btnFwUploadingListener(GUI gui) {
				this.gui = gui;
			}

			public void actionPerformed(ActionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if(selectedNode == null) return;

				WIZnet_Header.Discovery_Reply discovery_reply;
				for(int i=0; i<list.size(); i++) {
					String selectedNodeName = selectedNode.toString();
					discovery_reply = list.get(i);
					String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
							discovery_reply.mac_address[0], discovery_reply.mac_address[1],
							discovery_reply.mac_address[2], discovery_reply.mac_address[3],
							discovery_reply.mac_address[4], discovery_reply.mac_address[5]);

					if(selectedNodeName.equals(mac_address)) {
						FwUploadUI dialog = new FwUploadUI();
						dialog.setVisible(true);

						if(dialog.isOK == false)
							return;

						byte[] serverIp = new byte[4];
						String[] str_array = dialog.txtServerIp.getText().split("\\.");
						serverIp[0] = (byte) (0x00FF&Short.parseShort(str_array[0], 10));
						serverIp[1] = (byte) (0x00FF&Short.parseShort(str_array[1], 10));
						serverIp[2] = (byte) (0x00FF&Short.parseShort(str_array[2], 10));
						serverIp[3] = (byte) (0x00FF&Short.parseShort(str_array[3], 10));
						String password = new String(dialog.pwdPassword.getPassword());

						/* WIZ550S2E */
						if(discovery_reply.product_code[0] == 0x00 && discovery_reply.product_code[1] == 0x00 && discovery_reply.product_code[2] == 0x00) {
							WIZ550S2E_Config wiz550s2e_config = new WIZ550S2E_Config();
							if(PanelManager.updateFromPanel(gui, selectedMac, wiz550s2e_config)) {
								socket.firmware_upload(wiz550s2e_config.getData(), serverIp, Short.parseShort(dialog.txtServerPort.getText(), 10), dialog.txtFileName.getText().trim(), password.trim());
							}
						}
						/* WIZ550WEB */
						else if(discovery_reply.product_code[0] == 0x01 && discovery_reply.product_code[1] == 0x02 && discovery_reply.product_code[2] == 0x00) {
							WIZ550WEB_Config wiz550web_config = new WIZ550WEB_Config();
							if(PanelManager.updateFromPanel(gui, selectedMac, wiz550web_config)) {
								socket.firmware_upload(wiz550web_config.getData(), serverIp, Short.parseShort(dialog.txtServerPort.getText(), 10), dialog.txtFileName.getText().trim(), password.trim());
							}
						}
						break;
					}
				}
			}
		}
		btnFwUploading.addActionListener(new btnFwUploadingListener(this));
		panel_Buttons.add(btnFwUploading);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputValidation valid = new InputValidation();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if(selectedNode == null) return;

				WIZnet_Header.Discovery_Reply discovery_reply;
				for(int i=0; i<list.size(); i++) {
					String selectedNodeName = selectedNode.toString();
					discovery_reply = list.get(i);
					String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
							discovery_reply.mac_address[0], discovery_reply.mac_address[1],
							discovery_reply.mac_address[2], discovery_reply.mac_address[3],
							discovery_reply.mac_address[4], discovery_reply.mac_address[5]);

					if(selectedNodeName.equals(mac_address)) {
						PasswordUI dialog = new PasswordUI();
						dialog.setVisible(true);

						if(dialog.isOK == false)
							return;

						String password = new String(dialog.pwdPassword.getPassword());
						if(rdbtnIpAddress.isSelected()) {
							if(txtIpAddress.getText().length() > 0) {
								if(!valid.IpValid(txtIpAddress.getText().trim())) {
									JFrame frame = new JFrame();
									JOptionPane.showMessageDialog(frame, "Reset IP input error");
									return;
								}
								socket.reset(txtIpAddress.getText().trim(), discovery_reply.mac_address, password.trim());
							}

							else {
								socket.reset("255.255.255.255", discovery_reply.mac_address, password.trim());
							}
						}
						else {
							socket.reset("255.255.255.255", discovery_reply.mac_address, password.trim());
						}
						break;
					}
				}
			}
		});
		panel_Buttons.add(btnReset);
		
		JButton btnFactoryReset = new JButton("Factory Reset");
		btnFactoryReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputValidation valid = new InputValidation();
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if(selectedNode == null) return;

				WIZnet_Header.Discovery_Reply discovery_reply;
				for(int i=0; i<list.size(); i++) {
					String selectedNodeName = selectedNode.toString();
					discovery_reply = list.get(i);
					String mac_address = String.format("%02X:%02X:%02X:%02X:%02X:%02X",
							discovery_reply.mac_address[0], discovery_reply.mac_address[1],
							discovery_reply.mac_address[2], discovery_reply.mac_address[3],
							discovery_reply.mac_address[4], discovery_reply.mac_address[5]);

					if(selectedNodeName.equals(mac_address)) {
						PasswordUI dialog = new PasswordUI();
						dialog.setVisible(true);

						if(dialog.isOK == false)
							return;

						String password = new String(dialog.pwdPassword.getPassword());
						if(rdbtnIpAddress.isSelected()) {
							if(txtIpAddress.getText().length() > 0) {
								if(!valid.IpValid(txtIpAddress.getText().trim())) {
									JFrame frame = new JFrame();
									JOptionPane.showMessageDialog(frame, "Factory Reset IP input error");
									return;
								}
								socket.factory_reset(txtIpAddress.getText().trim(), discovery_reply.mac_address, password.trim());
							}

							else {
								socket.factory_reset("255.255.255.255", discovery_reply.mac_address, password.trim());
							}
						}
						else {
							socket.factory_reset("255.255.255.255", discovery_reply.mac_address, password.trim());
						}
						break;
					}
				}
			}
		});
		panel_Buttons.add(btnFactoryReset);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel_Buttons.add(btnExit);
	}

}
