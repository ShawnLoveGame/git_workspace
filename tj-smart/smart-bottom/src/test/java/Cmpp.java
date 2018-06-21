import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Cmpp {

    private int submitLong(AbstractSMSMsg msg) throws Exception {
        int sendReturn = 0;
        if (msg.getContent() == null || msg.getContent().trim().length() == 0) {
            return 0;
        }
        try {
            // 对原短信内容按照长短信最大长度3*134进行拆分
            String[] cotentText = this.separateMsg(msg.getContent(), 3*134);
            for (int k = 0; k < cotentText.length; k++) {
                String[] arrMsg = this.separateMsg(cotentText[k], 140);
                if (1 >= arrMsg.length) {
                    // 如果为单条短信则按照一般短信下发
                    msg.setContent(arrMsg[0]);
                    return submit(msg);
                }
                else {
                    log.info("submit ..." + cotentText[k]);
//                    // 如果为长短信则按照长短信规则进行下发
//                    arrMsg = this.separateMsg(cotentText[k], 134);
//                    CmppSubmitBuilder cm = new CmppSubmitBuilder();
//                    cm.createSequenceId();
//                    cm.createMsgId();
//                    cm.setRegisterDelivery(0);
//                    cm.setMsgLevel(1);
//                    if (msg.getServType() == null || msg.getServType().equals("")) {
//                        msg.setServType("CZ");
//                    }
//                    cm.setServiceId(msg.getServType().trim());
//                    if (cm.getServiceId() == null || cm.getServiceId().equals("")) {
//                        cm.setServiceId("00");
//                    }
//                    cm.setFeeUserType(0);
//                    cm.setFeeTermId(msg.getSerialNumber());
//                    cm.setFeeTermType(1);
//                    cm.setTpPid(0);
//                    // cm.setTpUdhi(0);
//                    cm.setMsgFmt(8);// 长短信采用UCS2编码
//                    cm.setMsgSrc(sp_id);
//                    log.info("SPID:" + sp_id);
//                    cm.setFeeType("01");
//                    cm.setFeeCode("0");
//                    cm.setAtTime("");
//                    cm.setValidTime("");
//                    cm.setSrcTermId(msg.getSourceNumber());
//                    cm.addDstTermId(msg.getSerialNumber());
//                    cm.setDstTermType(0);
//                    cm.setLinkId("");
//                    cm.setReserve("");
//
//                    // (1)TP_udhi设为“1”代表内容含有协议头信息
//                    cm.setTpUdhi(1);
//                    // (2)长短信6位协议头格式：05 00 03 XX MM NN
//                    byte[] hb = new byte[6];
//                    hb[0] = 0x05;
//                    hb[1] = 0x00;
//                    hb[2] = 0x03;
//                    hb[3] = (byte) getRefNumber();// 长短信信息序列号
//                    hb[4] = (byte) arrMsg.length;// 长短信包含数据包个数
//                    cm.setPkTotal(arrMsg.length);
//                    for (int i = 0; i < arrMsg.length; i++) {
//                        hb[5] = (byte) (i + 1);// 长短信第几个数据包
//                        cm.setPkNumber(i + 1);
//
//                        // 组装长短信消息头与消息内容
//                        byte[] cb = arrMsg[i].getBytes("UnicodeBigUnmarked");
//                        byte[] encodeMsg = new byte[hb.length + cb.length];
//                        System.arraycopy(hb, 0, encodeMsg, 0, hb.length);
//                        System.arraycopy(cb, 0, encodeMsg, hb.length, cb.length);
//
//                        synchronized (sendmb) {
//                            if (apieceNum > 0) {
//                                long sendTime = System.currentTimeMillis();
//                                long lastSendTime = lastTimeData.lastTime;
//                                System.out.println("lastSendTime1:" + lastTimeData.lastTime);
//                                long sendSpaceNum = sendTime - lastSendTime;
//                                System.out.println("sendSpaceNum=sendTime  -  lastSendTime:" + sendSpaceNum + ":" + sendTime + ":"
//                                        + lastSendTime);
//                                if (this.apieceNum > sendSpaceNum) {
//                                    long sleepTime = apieceNum - sendSpaceNum;
//                                    System.out.println("sleepTime:" + sleepTime);
//                                    log.info("Control sender sleep:" + sleepTime);
//                                    Thread.sleep(sleepTime);
//                                }
//                            }
//                            sendReturn = sendmb.syncSendSubmitUnresp(cm, encodeMsg);
//                            lastTimeData.lastTime = System.currentTimeMillis();
//                            System.out.println("lastSendTime2:" + lastTimeData.lastTime);
//                            if (sendReturn == -1) {// 超时，认为已和网关断开需要重新连接
//                                throw new Exception("超时");
//                            }
//                            log.info("Source: " + msg.getSourceNumber());
//                            log.info("Destination: " + msg.getSerialNumber());
//                            if (sendReturn != 0) {
//                                log.info("sendReturn: " + sendReturn);
//                                return sendReturn;
//                            }
//                        }
//                    }
                }
            }
        }
        catch (Exception ex) {
            log.warn("发送短信失败", ex);
            throw new Exception("submit", ex);
        }
        return sendReturn;
    }

    public int submit(AbstractSMSMsg msg){
        return 1;
    }

    public String[] separateMsg(String content,int length){
        int size = content.length() / length;
        if (content.length() % length != 0) {
            size += 1;
        }
        String[] res = new String[size];
        for(int i=0;i<size;i++){
            res[i] = content.substring(0,length);
            content = content.substring(length);
        }
        return res;
    }
}
