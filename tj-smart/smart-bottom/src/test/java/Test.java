//import com.sun.jndi.cosnaming.IiopUrl;
//import ie.omk.smpp.Connection;
//import ie.omk.smpp.SMPPException;
//import ie.omk.smpp.event.ConnectionObserver;
//import ie.omk.smpp.event.SMPPEvent;
//import ie.omk.smpp.message.SMPPPacket;
//import ie.omk.smpp.message.SubmitSM;
//import ie.omk.smpp.util.UCS2Encoding;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class Test implements ConnectionObserver {
//
//    private SubmitSMResp submitLong(AbstractSMSMsg msg) throws SMPPException {
//
//        SubmitSM sm = null;
//        SubmitSMResp smr = null;
//        try
//        {
////            log.info("submit ..." + msg.toString());
////            sm = (SubmitSM) myConnection.newInstance(SMPPPacket.SUBMIT_SM);
////            sm.setAlphabet(UCS2Encoding.getInstance());
////            sm.setSource(new IiopUrl.Address(2, 1, msg.getSourceNumber()));
////            sm.setDestination(new IiopUrl.Address(2, 1, msg.getSerialNumber()));
////            sm.setSequenceNum(msg.getSequenceId());
////
////            /* 支持长短信下发：按照长短信要求进行分割 */
////            String[] arrMsg = this.separateLongMsg(msg.getContent());
////            int iArrMsgLen = arrMsg.length;
////
////            if (iArrMsgLen <= 1)
////            {
////                // 如果为单条短信则按照一般短信下发
////                sm.setMessageText(msg.getContent());
////                synchronized (myConnection)
////                {
////                    // 原信息：
////                    log.info("send...start");
////                    smr = (SubmitSMResp) myConnection.sendRequest(sm);
////                    log.info("smr.getCommandStatus:" + smr.getCommandStatus());
////                    log.info("Source: " + sm.getSource());
////                    log.info("Destination: " + sm.getDestination());
////                }
////            }
////            else
////            {
////                // 如果为长短信则按照长短信规则进行下发
////                // (1)esm_class参数设置为：0x40 (01000000), 代表短信内容含有协议头信息(长短信)
////                byte udhi = 0x40;
////                sm.setEsmClass(udhi);
////                // (2)长短信6位协议头格式：05 00 03 XX MM NN
////                byte[] hb = new byte[6];
////                hb[0] = 0x05;
////                hb[1] = 0x00;
////                hb[2] = 0x03;
////                hb[3] = (byte) this.getRefNumber();// 长短信信息序列号
////                hb[4] = (byte) iArrMsgLen;// 长短信包含数据包个数
////                for (int i = 0; i < iArrMsgLen; i++)
////                {
////                    hb[5] = (byte) (i + 1);// 长短信第几个数据包
////
////                    // 设置消息内容
////                    sm.setMessageText(arrMsg[i]);
////
////                    // 组装长短信消息头与消息内容
////                    byte[] cb = sm.getMessage();
////                    byte[] encodeMsg = new byte[hb.length + cb.length];
////                    System.arraycopy(hb, 0, encodeMsg, 0, hb.length);
////                    System.arraycopy(cb, 0, encodeMsg, hb.length, cb.length);
////
////                    // 设置组装后的消息内容
////                    sm.setMessage(encodeMsg, sm.getAlphabetEncoding());
////
////                    synchronized (myConnection)
////                    {
////                        // 原信息：
////                        log.info("send...start");
////                        smr = (SubmitSMResp) myConnection.sendRequest(sm);
////                        log.info("smr.getCommandStatus:" + smr.getCommandStatus());
////                        log.info("Source: " + sm.getSource());
////                        log.info("Destination: " + sm.getDestination());
////                    }
////                }
//            }
//
//            smr.setServiceType(sm.getServiceType());
//            return smr;
//        }
//        catch (Exception ex)
//        {
//            log.warn("发送短信失败", ex);
//            throw new SMPPException("submit", ex);
//        }
//    }
//
//    @Override
//    public void packetReceived(Connection connection, SMPPPacket smppPacket) {
//
//    }
//
//    @Override
//    public void update(Connection connection, SMPPEvent smppEvent) {
//
//    }
//
//    private class SubmitSMResp {
//    }
//}
