package com.ikohoo.util;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import com.ikohoo.main.SmsMainFrame;

public class JTextAreaAppender extends AppenderSkeleton {

	/**
	 * 需要日志显示到的位置（这里是JTextArea对象）
	 */
	private JTextArea textArea = SmsMainFrame.txtLog;
	/**
	 * 最多显示条数
	 */
	protected int maxEntries = 200;
	/**
	 * 已经在JTextArea上显示的条数
	 */
	private int entries = 0;
	
	
	@Override
	public void close() {
		textArea.setText("");
		entries = 0;

	}

	@Override
	public boolean requiresLayout() {
		
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		String text = this.layout.format(event);
		Document doc = null;
		try {
			doc = textArea.getDocument();
			if (entries >= maxEntries) {
				int endOfs = textArea.getLineEndOffset(entries - maxEntries);
				doc.remove(0, endOfs);
				entries = entries - 1;
			}
			entries = entries + 1;
			textArea.append(text);
			textArea.setCaretPosition(doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

}
