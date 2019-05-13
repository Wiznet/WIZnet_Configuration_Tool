import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JPasswordField;


public class FwUploadUI extends JDialog {

	private static final long serialVersionUID = 1475957581978595490L;
	private final JPanel contentPanel = new JPanel();
	public JTextField txtServerIp;
	public JTextField txtServerPort;
	public JTextField txtFileName;
	public boolean isOK = false;
	public JPasswordField pwdPassword;
	public final String INIFileName = new String("WIZnet_Configuration_Tool.ini");

	private void closeDialog() {
		this.dispose();
	}

	/**
	 * Create the dialog.
	 */
	public FwUploadUI() {
		setResizable(false);
		setModal(true);
		setTitle("TFTP Server Information");
		setSize(350, 220);
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC,},
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
		{
			JLabel lblTftpServerInformation = new JLabel("TFTP Server Information");
			contentPanel.add(lblTftpServerInformation, "2, 2, 3, 1");
		}
		{
			JLabel lblServerIp = new JLabel("Server IP");
			contentPanel.add(lblServerIp, "2, 4, right, default");
		}
		{
			txtServerIp = new JTextField();
			contentPanel.add(txtServerIp, "4, 4, fill, default");
			txtServerIp.setColumns(10);
		}
		{
			JLabel lblServerPort = new JLabel("Server Port");
			contentPanel.add(lblServerPort, "2, 6, right, default");
		}
		{
			txtServerPort = new JTextField();
			txtServerPort.setText("69");
			contentPanel.add(txtServerPort, "4, 6, fill, default");
			txtServerPort.setColumns(10);
		}
		{
			JLabel lblFileName = new JLabel("File Name");
			contentPanel.add(lblFileName, "2, 8, right, default");
		}
		{
			txtFileName = new JTextField();
			contentPanel.add(txtFileName, "4, 8, fill, top");
			txtFileName.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "2, 10, right, default");
		}
		{
			pwdPassword = new JPasswordField();
			contentPanel.add(pwdPassword, "4, 10, fill, default");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						InputValidation valid = new InputValidation();

						if(!valid.IpValid(txtServerIp.getText().trim())) {
							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Server IP input error");
							txtServerIp.requestFocus();
							return;
						}
						if(!valid.PortValid(txtServerPort.getText().trim())) {
							JFrame frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Server Port input error");
							txtServerPort.requestFocus();
							return;
						}

						try {
							FileWriter fw = new FileWriter(INIFileName);
							BufferedWriter bw = new BufferedWriter(fw);

							bw.write(txtServerIp.getText().trim());
							bw.newLine();
							bw.write(txtServerPort.getText().trim());
							bw.newLine();
							bw.write(txtFileName.getText().trim());
							bw.newLine();

							bw.close();
							fw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						isOK = true;
						closeDialog();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						isOK = false;
						closeDialog();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

		try {
			FileReader fr = new FileReader(INIFileName);
			BufferedReader br = new BufferedReader(fr);

			String ini;
			ini = br.readLine();
			txtServerIp.setText(ini);
			ini = br.readLine();
			txtServerPort.setText(ini);
			ini = br.readLine();
			txtFileName.setText(ini);

			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
