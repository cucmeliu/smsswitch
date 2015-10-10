package com.ikohoo.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.ikohoo.api.SendSMSCF;
import com.ikohoo.dao.SMSSendDao;
import com.ikohoo.domain.Config;
import com.ikohoo.domain.SMSSendBean;
import com.ikohoo.domain.SMSSendParams;
import com.ikohoo.factory.BasicFactory;
import com.ikohoo.service.SMSSendService;
import com.ikohoo.util.SMSUtils;

public class SMSSendServiceImpl implements SMSSendService {
	static Logger logger = Logger.getLogger(SMSSendServiceImpl.class);
	
	SMSSendDao dao = BasicFactory.getFactory().getInstance(SMSSendDao.class);
	static long fromId = 0;
	static long toId = 0;
	private Config config;

	// private int count;

	@Override
	public List<SMSSendBean> getNewSMS(int count) {
		return dao.getNewSMS(count);
	}

	@Override
	public int[] updateState2DB(List<SMSSendBean> list) {
		return dao.updateSendState(list);
	}

	@Override
	public int insert2DB(SMSSendBean record) {
		return dao.insert(record);
	}

	@Override
	public int insert2DB(List<SMSSendBean> list) {
		int[] a = dao.insert(list);
		int sum = 0;
		for (int i = 0; i < a.length; i++)
			sum += a[i];
		return sum;
	}

	private boolean isDuplicatPhone(List<SMSSendBean> list, String phone) {
		for (SMSSendBean ssb : list)
			if (ssb.getPhone().equals(phone))
				return true;

		return false;
	}

	/**
	 * 取不超过50条短信
	 * 
	 * @param list
	 * @return
	 */
	private List<SMSSendBean> getOnePack(List<SMSSendBean> list) {
		List<SMSSendBean> rst = new ArrayList<SMSSendBean>();
		SMSSendBean ss = null;
		int count = 0;
		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext()
				&& count++ < config.getPackMax(); ) {
			ss = it.next();
			if (!isDuplicatPhone(rst, ss.getPhone())) {
				rst.add(ss);
				it.remove();
			}
		}
		return rst;
	}

	@Override
	public int packSend(List<SMSSendBean> list) {
		List<SMSSendBean> rstList = new ArrayList<SMSSendBean>();
		//System.out.println("*****重点测试*****，超过50条时，是否正确拼接＊＊＊＊");
		int count = 0;
		try {
			while (list.size() > 0) {
				List<SMSSendBean> oneList = new ArrayList<SMSSendBean>(
						getOnePack(list));

				System.out.println("One pack ..size: " + oneList.size());
				logger.info("One pack ..size: " + oneList.size());

				count += sendOnePack(oneList);
				rstList.addAll(oneList);
				// 每两条发送，间隔一个时间
				Thread.sleep(config.getSendPause());
				System.out.println("sleep " + config.getSendPause() + " ms");
				
//				for (SMSSendBean ssb : oneList) {
//					System.out.println(ssb.toString());
//					logger.info(ssb.toString());
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getStackTrace());
			throw new RuntimeException(e);
		}

		// 做为返回引用，传回去
		list.addAll(rstList);

		return count;
	}

	@Override
	public int sendOnePack(List<SMSSendBean> list) {
		SendSMSCF sendSms = new SendSMSCF(config);
		StringBuilder sb = new StringBuilder("");
		for (SMSSendBean ssb : list) {
			sb.append(ssb.getPhone()).append("|~|").append(ssb.getContent())
					.append("|-|");
		}

		sb.delete(sb.length()-3, sb.length());
		
		SMSSendParams msg = new SMSSendParams();
		msg.setChannel(SMSSendParams.channelSMS);
		msg.setMsg(sb.toString());
		System.out.println("send one packet: " + sb.toString());
		logger.info("send one packet: " + sb.toString());

		long sendrst = sendSms.sendPack(msg);
		// TODO 测试使用，发布时改为上面的语句
		// long sendrst = sendSms.sendTest(msg);

		int state;
		if (sendrst < 0) {
			state = (int) sendrst;
		} else {
			state = SMSSendBean.STATE_SUBSUCC;
		}

		Timestamp sendtime = new Timestamp(System.currentTimeMillis());

		for (SMSSendBean ssb : list) {
			ssb.setStatcode(sendrst);
			ssb.setState(state);

			ssb.setSendtime(sendtime);
		}
		return list.size();
	}

	@Override
	public int sendSMSList(List<SMSSendBean> list) {
		SendSMSCF sendSms = new SendSMSCF(config);
		SMSSendParams msg = new SMSSendParams();
		SMSSendBean ss = null;
		int succCount = 0;

		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext();) {
			ss = it.next();

			msg.setDestNo(ss.getPhone());
			msg.setMsg(ss.getContent());
			msg.setChannel(SMSSendParams.channelSMS);

			long sendRst = sendSms.send(msg);
			// TODO 测试使用，发布时改为上面的语句
			// long sendRst = sendSms.sendTest(msg);
			// if (batchNumber>0)
			// { 不论返回批次号还是失败码，都更新，
			ss.setSendtime(new Timestamp(System.currentTimeMillis()));
			if (sendRst < 0) {
				ss.setState((int) sendRst);
			} else {
				ss.setState(SMSSendBean.STATE_SUBSUCC);
				ss.setStatcode(sendRst);
				succCount++;
			}
		}
		return succCount;
	}

	@Override
	public void addNewListToTotal(List<SMSSendBean> total,
			List<SMSSendBean> newList) {
		if (null == newList)
			return;

		for (SMSSendBean ss : newList)
			SMSUtils.binaryInsSMS(total, ss);
	}

	@Override
	public int dealSentSMS(List<SMSSendBean> list) {
		List<SMSSendBean> sentList = new ArrayList<SMSSendBean>();

		SMSSendBean ss;
		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext();) {
			ss = it.next();
			if (ss.getState() != 0) {
				sentList.add(ss);
				it.remove();
			}
		}
		int[] a = updateState2DB(sentList);
		int rst = 0;
		for (int i = 0; i < a.length; i++)
			rst += a[i];

		return rst;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;

	}

}
