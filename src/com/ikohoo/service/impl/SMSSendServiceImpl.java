package com.ikohoo.service.impl;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
import com.ikohoo.util.SMSSortByContent;
import com.ikohoo.util.SMSUtils;

public class SMSSendServiceImpl implements SMSSendService {
	static Logger logger = Logger.getLogger(SMSSendServiceImpl.class);

	SMSSendDao dao = BasicFactory.getFactory().getInstance(SMSSendDao.class);
	// static long fromId = 0;
	// static long toId = 0;
	private Config config;

	@Override
	public List<SMSSendBean> getNewSMS(int count) {
		try {
			// dao.setTable(config.getTableSend());
			return dao.getNewSMS(count);
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public List<SMSSendBean> getNewSMS(int count, int mod, int remainder) {
		try {
			// dao.setTable(config.getTableSend());
			return dao.getNewSMS(count, mod, remainder);
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}
	}

	@Override
	public int[] updateState2DB(List<SMSSendBean> list) {
		try {
			// dao.setTable(config.getTableSend());
			return dao.updateSendState(list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	@Override
	public int insert2DB(SMSSendBean record) {
		try {
			// dao.setTable(config.getTableSend());
			return dao.insert(record);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return 0;
	}

	@Override
	public int insert2DB(List<SMSSendBean> list) {
		int[] a;
		try {
			// dao.setTable(config.getTableSend());
			a = dao.insert(list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
			return 0;
		}

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
		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext() && count++ < config.getPackMax();) {
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
		int count = 0;
		try {
			while (list.size() > 0) {
				List<SMSSendBean> oneList = new ArrayList<SMSSendBean>(getOnePack(list));

				// System.out.println("One pack ..size: " + oneList.size());
				logger.info("One pack ..size: " + oneList.size());

				count += sendOnePack(oneList);
				rstList.addAll(oneList);
				// 每两条发送，间隔一个时间
				Thread.sleep(config.getSendPause());
				System.out.println("sleep " + config.getSendPause() + " ms");

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			// throw new RuntimeException(e);
		}

		// 做为返回引用，传回去
		list.addAll(rstList);

		return count;
	}

	@Override
	public int sendOnePack(List<SMSSendBean> list) throws Exception {
		SendSMSCF sendSms = new SendSMSCF(config);
		StringBuilder sb = new StringBuilder("");
		for (SMSSendBean ssb : list) {
			sb.append(ssb.getPhone()).append("|~|").append(ssb.getContent()).append("|-|");
		}

		sb.delete(sb.length() - 3, sb.length());

		SMSSendParams msg = new SMSSendParams();
		msg.setChannel(config.getChannal()); // (SMSSendParams.channelSMS);
		msg.setMsg(sb.toString());
		// System.out.println("send one packet: " + sb.toString());
		logger.info("send one packet: " + sb.toString());

		try {

			String sendrst = sendSms.sendPack(msg, config.getCmdSendIndiv());
			// TODO 测试使用，发布时改为上面的语句
			// long sendrst = sendSms.sendTest(msg);

			logger.info(" result: " + sendrst);

			int state = SMSSendBean.STATE_SUBSUCC;

			try {
				if (sendrst.length() < 4) {
					state = Integer.parseInt(sendrst);
				}
			} catch (Exception e) {
				// －6:keyWords
				state = -6;
				e.printStackTrace();
				logger.error(e);
			}

			Timestamp sendtime = new Timestamp(System.currentTimeMillis());

			for (SMSSendBean ssb : list) {
				ssb.setStatcode(sendrst);
				ssb.setState(state);

				ssb.setSendtime(sendtime);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
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
			msg.setChannel(config.getChannal()); // (SMSSendParams.channelSMS);

			String sendrst;
			try {
				sendrst = sendSms.send(msg, config.getCmdSend());
				// TODO 测试使用，发布时改为上面的语句
				// long sendRst = sendSms.sendTest(msg);
				// if (batchNumber>0)
				// { 不论返回批次号还是失败码，都更新，
				ss.setSendtime(new Timestamp(System.currentTimeMillis()));

				int state = SMSSendBean.STATE_SUBSUCC;

				try {
					if (sendrst.length() < 4) {
						state = Integer.parseInt(sendrst);

					}
					ss.setState(state);
					ss.setStatcode(sendrst);
					if (state > 0)
						succCount++;
					//
					// if (Integer.parseInt(sendRst) <= 0) {
					// ss.setState(Integer.parseInt(sendRst));
					// } else {
					// ss.setState(SMSSendBean.STATE_SUBSUCC);
					// ss.setStatcode(sendRst);
					// succCount++;
					// }

				} catch (Exception e) {
					// －6:keyWords
					state = -6;
					e.printStackTrace();
					logger.error(e);
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
				// sendrst = "";
			}

		}
		return succCount;
	}

	@Override
	public void addNewListToTotal(List<SMSSendBean> total, List<SMSSendBean> newList) {
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
		// update 原数据表
		int[] a = updateState2DB(sentList);

		int rst = 0;
		for (int i = 0; i < a.length; i++)
			rst += a[i];

		return rst;
	}

	private int[] deleteSent(List<SMSSendBean> list) {
		try {
			// dao.setTable(config.getTableSend());
			return dao.delSentRec(list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	private int[] insertSent(List<SMSSendBean> list) {
		try {
			// dao.setTable(config.getTableSent());
			return dao.insSentRec(list);
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	@Override
	public void setConfig(Config config) {
		this.config = config;
		dao.setTable(config.getTableSend());
		dao.setTableSent(config.getTableSent());

	}

	/**
	 * 拼云信的普通短信包：内容相同，号码拼包
	 * 
	 * @param list
	 * @return
	 * @deprecated
	 */
	private List<SMSSendParams> parseYunXinSmsList(List<SMSSendBean> list) {
		List<SMSSendParams> ret = new ArrayList<SMSSendParams>();

		if (null == list || 0 == list.size())
			return null;

		// 简化，连续的相同内容短信作为一条，不连续的即使内容相同也当成几条处理
		// int count = 0;
		// Boolean started = false;
		String msg = null;
		SMSSendBean ss;
		SMSSendParams ssp = null;
		StringBuilder sb = null;
		// SMSSendBean lastIt = null;

		// 拼相同内容的包
		while (list.size() > 0) {
			int count = 0;
			Boolean started = false;
			sb = new StringBuilder("");
			for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext();) {
				ss = it.next();
				// System.out.println(" parse content pack: " + ss.toString());
				if (!started || count > config.getSendMax()) {
					count = 0;
					started = true;

					ssp = new SMSSendParams();
					ret.add(ssp);

					msg = ss.getContent();
					ssp.setMsg(msg);
					ssp.setChannel(config.getChannal());
					sb.append(ss.getPhone()).append(",");
					it.remove();
					count++;

				} else {
					if (ss.getContent().equals(msg)) {
						sb.append(ss.getPhone()).append(",");
						it.remove();
						count++;
					}
				}
			}
			// System.out.println(".......one pack........");
			// 去掉最后的逗号
			sb.delete(sb.length() - 1, sb.length());
			ssp.setDestNo(sb.toString());

			// 相同内容的短信少于packMin后，采用个性化发送
			if (count < config.getPackMin()) {
				break;
			}
		}

		return ret;
	}

	/**
	 * 
	 * @param list
	 * @return
	 * @deprecated
	 */
	private List<SMSSendParams> parseYunXinIndividualList(List<SMSSendBean> list) {

		if (null == list || 0 == list.size())
			return null;

		// 拼个性化包
		List<SMSSendParams> ret = new ArrayList<SMSSendParams>();
		SMSSendParams ssp;
		try {
			while (list.size() > 0) {
				ssp = new SMSSendParams();
				ssp.setChannel(config.getChannal());
				ssp.setMsg(getOneYunXinPackStr(list));

				ret.add(ssp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return ret;
	}

	/**
	 * 拼一个个性化的短信内容字符串
	 * 
	 * @param list
	 * @return
	 */
	private String getOneYunXinPackStr(List<SMSSendBean> list) {
		SMSSendBean ss = null;
		int count = 0;
		StringBuilder sb = new StringBuilder();
		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext() && count++ < config.getPackMax();) {
			ss = it.next();

			sb.append(ss.getPhone()).append("|!|").append(ss.getContent()).append("|^|");

			// it.remove();
		}
		System.out.println(" list.size()=" + list.size() + ", sb: " + sb.toString());

		sb.delete(sb.length() - 3, sb.length());
		return sb.toString();
	}

	@Override
	public int sendYunXin(List<SMSSendBean> list) {

		if (null == list || list.size() == 0)
			return 0;

		Collections.sort(list, new SMSSortByContent());

		SendSMSCF sendSms = new SendSMSCF(config);
		List<SMSSendBean> rstList = new ArrayList<SMSSendBean>();
		List<SMSSendBean> notFitList = new ArrayList<SMSSendBean>();

		// int count = 0;

		// 先处理内容相同的，按电话号码打包
		try {
			while (list.size() > 0) {

				List<SMSSendBean> oneList = getOneYunXinSmsPack(list, notFitList);

				if (null == oneList)
					break;

				String sendrst = sendSms.send(packYunXinSms(oneList), config.getCmdSend());
				logger.info("sending yunxin sms:  result: " + sendrst);
				// TODO String sendrst =
				// sendSms.sendTest(packYunXinSms(oneList));

				int state = SMSSendBean.STATE_SUBSUCC;

				//BigInteger bg = null;
				try {

					if (sendrst.length() < 4) {
						state = Integer.parseInt(sendrst);
					} else if (sendrst.contains(":")) {
						state = -6;
					}

					// bg = new BigInteger(sendrst);
					// if(bg.longValue()<0) {
					// if (Integer.parseInt(sendrst) < 0) {
					// state = Integer.parseInt(sendrst);
					// }
				} catch (Exception e) {
					// －6:keyWords
					state = -6;
				}
				Timestamp sendtime = new Timestamp(System.currentTimeMillis());

				for (SMSSendBean ssb : oneList) {
					ssb.setStatcode(sendrst);
					ssb.setState(state);
					ssb.setSendtime(sendtime);
				}

				// count = oneList.size();
				rstList.addAll(oneList);

				// 当都少于最小包大小时，采用个性化
				// if (count < config.getPackMin())
				// break;

				Thread.sleep(config.getSendPause());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

		// 剩下的按个性化打包 notFitList
		if ((null != notFitList) && (notFitList.size() > 0)) {

			SMSSendParams ssp = new SMSSendParams();

			ssp.setChannel(config.getChannal());
			ssp.setMsg(getOneYunXinPackStr(notFitList));

			try {
				String sendrst = sendSms.sendPack(ssp, config.getCmdSendIndiv());
				logger.info("sending yunxin individual, " + ssp.getMsg() + " result: " + sendrst);

				int state = SMSSendBean.STATE_SUBSUCC;

				try {

					if (sendrst.length() < 4) {
						state = Integer.parseInt(sendrst);
					} else if (sendrst.contains(":")) {
						state = -6;
					}

					// if (Integer.parseInt(sendrst) < 0) {
					// state = Integer.parseInt(sendrst);
					// }
				} catch (Exception e) {
					// －6:keyWords
					state = -6;
				}
				Timestamp sendtime = new Timestamp(System.currentTimeMillis());

				for (SMSSendBean ssb : notFitList) {
					ssb.setStatcode(sendrst);
					ssb.setState(state);
					ssb.setSendtime(sendtime);
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e);
			}
		}
		list.addAll(notFitList);
		list.addAll(rstList);

		return list.size();

		// /////////////////////////////////////////////
		// 先按电话号码打包
		// List<SMSSendParams> yxList = parseYunXinSmsList(list);
		//
		// if (null != yxList) {
		// System.out.println("yunxin sms: " + list.size());
		// try {
		// for (SMSSendParams ssp : yxList) {
		// logger.info("sending yunxin sms: " + ssp.toString());
		// // TO DO sendSms.send(ssp, SendSMSCF.sendMes);
		//
		// sendSms.sendTest(ssp);
		//
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// logger.error(e);
		// }
		// }
		//
		// // 剩下的按个性化打包
		// System.out.println("before individual: " + list.size());
		// yxList = parseYunXinIndividualList(list);
		//
		// if (null != yxList) {
		// System.out.println(" individual: " + yxList.size());
		// try {
		// for (SMSSendParams ssp : yxList) {
		// logger.info("sending yunxin individual: " + ssp.toString());
		// // sendSms.sendPack(ssp, SendSMSCF.IndividualSm);
		// sendSms.sendTest(ssp);
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// logger.error(e);
		// }
		// }
	}

	/**
	 * 将list打包成云信的相同内容包
	 * 
	 * @param list
	 *            ，已经过滤了的相同内容的短信
	 * @return
	 */
	private SMSSendParams packYunXinSms(List<SMSSendBean> list) {
		SMSSendParams ssp = new SMSSendParams();
		SMSSendBean ss = null;
		StringBuilder phones = new StringBuilder();
		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext();) {
			ss = it.next();
			phones.append(ss.getPhone()).append(",");
		}
		phones.delete(phones.length() - 1, phones.length());
		ssp.setDestNo(phones.toString());
		ssp.setMsg(ss.getContent());
		ssp.setChannel(config.getChannal());

		return ssp;
	}

	/**
	 * 取一串相同内容的短信列表出来
	 * 
	 * @param list
	 *            已按内容排序的短信列表
	 * @param notFitList
	 *            ，数量不够的，做为个性化的，引用返回
	 * @return
	 */
	private List<SMSSendBean> getOneYunXinSmsPack(List<SMSSendBean> list, List<SMSSendBean> notFitList) {

		if (null == list || 0 == list.size())
			return null;

		// List<SMSSendBean> ret = new ArrayList<SMSSendBean>();
		// 不满足条件的，暂存，返回

		String msg = null;
		SMSSendBean ss;

		// 拼相同内容的包
		while (list.size() > 0) {
			int count = 0;
			Boolean started = false;
			List<SMSSendBean> oneList = new ArrayList<SMSSendBean>();

			for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext();) {
				ss = it.next();

				if (!started) {
					started = true;
					oneList.add(ss);
					msg = ss.getContent();
					it.remove();
					count++;
				} else {
					if (ss.getContent().equals(msg)) {
						oneList.add(ss);
						it.remove();
						count++;
					} else {
						// 排过序了，所以遇到不相同的就下一轮
						break;
					}
				}
			}

			if (count < config.getPackMin())
				notFitList.addAll(oneList);
			else
				return oneList;
		}
		return null;
	}

	@Override
	public int dealSentSMSYunXin(List<SMSSendBean> list) {
		List<SMSSendBean> sentList = new ArrayList<SMSSendBean>();

		SMSSendBean ss;
		for (Iterator<SMSSendBean> it = list.iterator(); it.hasNext();) {
			ss = it.next();
			// System.out.println("deal: " + ss.toString());
			if (ss.getState() != 0) {
				sentList.add(ss);
				it.remove();
			}
		}
		//
		long start = System.currentTimeMillis();

		int[] a = insertSent(sentList);

		int rst = 0;
		if (null != a) {
			for (int i = 0; i < a.length; i++)
				rst += a[i];
		}
		logger.info("  insert sent record: " + rst + ", Cost time: " + (System.currentTimeMillis() - start) + " ms\n");

		start = System.currentTimeMillis();
		a = deleteSent(sentList);
		rst = 0;
		for (int i = 0; i < a.length; i++)
			rst += a[i];
		logger.info("  delete sent record: " + rst + ", Cost time: " + (System.currentTimeMillis() - start) + " ms\n");

		return rst;
	}
}
