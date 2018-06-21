package com.ai.smart.common.helper.weixin.pay.entity.req;

/**
 * 微信支付接口——退款申请接口请求实体类
 *
 */
public class RefundRequest extends BaseRequest {

	// 设备号 否 String(32) 微信支付分配的终端设备号
	private String device_info;

	// 微信订单号 否 String(32) 微信的订单号，优先使用
	private String transaction_id;
	// 商户订单号 是 String(32) 商户系统内部的订单号，transaction_id 、out_trade_no 二选一， 如果同时存在优先级 ：transaction_id>out_trade_no
	private String out_trade_no;
	// 商户退款单号 否 String(32) 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
	private String out_refund_no;
	// 总金额 是 Int 订单总金额，单位为分
	private int total_fee;
	// 退款金额 是 Int 退款总金额， 单位为分,可以做部分退款
	private int refund_fee;
	// 操作员 是 String(32) 操作员帐号, 默认为商户号
	private String op_user_id;

	// ------ Getter & Setter ------

	/**
	 * @return the device_info
	 */
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @param device_info the device_info to set
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	/**
	 * @return the transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transaction_id the transaction_id to set
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * @return the out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no the out_trade_no to set
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @return the out_refund_no
	 */
	public String getOut_refund_no() {
		return out_refund_no;
	}

	/**
	 * @param out_refund_no the out_refund_no to set
	 */
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	/**
	 * @return the total_fee
	 */
	public int getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee the total_fee to set
	 */
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @return the refund_fee
	 */
	public int getRefund_fee() {
		return refund_fee;
	}

	/**
	 * @param refund_fee the refund_fee to set
	 */
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	/**
	 * @return the op_user_id
	 */
	public String getOp_user_id() {
		return op_user_id;
	}

	/**
	 * @param op_user_id the op_user_id to set
	 */
	public void setOp_user_id(String op_user_id) {
		this.op_user_id = op_user_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RefundRequest [device_info=" + device_info + ", transaction_id=" + transaction_id + ", out_trade_no="
				+ out_trade_no + ", out_refund_no=" + out_refund_no + ", total_fee=" + total_fee + ", refund_fee="
				+ refund_fee + ", op_user_id=" + op_user_id + ", super.toString()=" + super.toString() + "]";
	}

}