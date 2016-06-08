package com.ikohoo.main;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import com.ikohoo.domain.Config;
import com.ikohoo.test.DBTester;
import com.ikohoo.test.SendSMSTester;
import com.ikohoo.util.ConfigReader;

public class SmsMainFrame {

	JFrame frame = new JFrame("Sms Switch");
	JPanel pnlTop = new JPanel();
	JButton btStart = new JButton("Start");
	JButton btStop = new JButton("Stop");
	JCheckBox cbSend = new JCheckBox("send");
	JCheckBox cbRecv = new JCheckBox("recv");
	JCheckBox cbRept = new JCheckBox("rept");
	JCheckBox cbDbTest = new JCheckBox("test");

	// 单次发送内容
	JPanel pnlBottom = new JPanel();
	JLabel lbNum = new JLabel("手机号");
	JTextField txtPhone = new JTextField(8);

	JLabel lbContent = new JLabel("短信内容");
	JTextField txtContent = new JTextField(30);
	
	
	JButton btSendOne = new JButton("发送");
	JButton btSendOnce = new JButton("发一次");

	JPanel pnlLog = new JPanel(new BorderLayout());
	// JCheckBox cbShowlog = new JCheckBox("显示日志");

	public static JTextArea txtLog = new JTextArea();

	SMSMain smsMain = new SMSMain();
	SendSMSTester test = new SendSMSTester();

	// Container c;

	public SmsMainFrame() {
		// frame = new JFrame("Sms Switch");
		frame.setLayout(new BorderLayout());

		// pnlButtons = new JPanel();
		frame.add(pnlTop, "North");
		frame.add(pnlBottom, "South");

		// btStart = new JButton("Start");
		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] args = { "", "", "", "" };

				if (!cbSend.isSelected())
					args[0] = "";
				else {
					Config config = ConfigReader.loadConfig();
					if (config.getBizType().equals("yunxin")) {
						args[0] = "yunxin";
					} else if (config.getBizType().equals("chufa")) {
						args[0] = "chufa";
					} else {
						txtLog.append("Param error, please check the project.properies file.\n");
						return;
					}
				}
				if (cbRecv.isSelected())
					args[1] = "recv";
				if (cbRept.isSelected())
					args[2] = "rept";
				if (cbDbTest.isSelected())
					args[3] = "test";

				btStart.setEnabled(false);
				btStop.setEnabled(true);

				smsMain.start(args);
				/// test.startTimer();
			}

		});
		pnlTop.add(btStart);

		// btStop = new JButton("Stop");
		btStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// txtLog.append("stop");
				smsMain.stop();
				// test.stopTimer();

				btStart.setEnabled(true);
				btStop.setEnabled(false);
			}

		});

		btStop.setEnabled(false);

		pnlTop.add(btStop);

		cbSend.setSelected(true);
		cbRecv.setSelected(true);
		cbRept.setSelected(true);
		cbDbTest.setSelected(false);

		pnlTop.add(cbSend);
		pnlTop.add(cbRecv);
		pnlTop.add(cbRept);
		pnlTop.add(cbDbTest);
		
		btSendOnce.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtPhone.getText().trim().equals("") || txtContent.getText().trim().equals("")) {
					// 电话号码 和 短信内容

					return;
				} else {
					new DBTester(new Config(), true).sendOnce();
				}
			}
		});
		pnlTop.add(btSendOnce);
		

		txtPhone.setText("13000000000");
		txtContent.setText("這是測試短信【測試】");
		pnlBottom.add(lbNum);
		pnlBottom.add(txtPhone);
		pnlBottom.add(lbContent);
		pnlBottom.add(txtContent);
				
		btSendOne.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtPhone.getText().trim().equals("") || txtContent.getText().trim().equals("")) {
					// 电话号码 和 短信内容

					return;
				} else {
					new DBTester(new Config(), true).sendOne(txtPhone.getText().trim(), txtContent.getText().trim());
				}
			}
		});

		pnlBottom.add(btSendOne);

		// cbShowlog = new JCheckBox("显示日志");
		// pnlButtons.add(cbShowlog);

		// pnlLog = new JPanel(new BorderLayout());
		frame.add(pnlLog, "Center");

		// txtLog = new JTextArea();
		JScrollPane sp = new JScrollPane(txtLog);
		pnlLog.add(sp, "Center");

		frame.setSize(800, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeApp();
			}

			private void closeApp() {
				Toolkit.getDefaultToolkit().beep();
				int answer = JOptionPane.showConfirmDialog(frame, "您真的要退出此系统？", "退出程序", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null);

				if (answer == JOptionPane.YES_OPTION) // 选择“是”
				{

					smsMain.stop();
					System.exit(0);
				} else if (answer == JOptionPane.NO_OPTION) // 选择“否”
				{
					return;
				}
			}
		});
	}

	public void createComps() {

	}

	public static void log(String str) {
		// txtLog.append("start");
	}

	public static void main(String args[]) {
		new SmsMainFrame();
	}

}
