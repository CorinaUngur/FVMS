package connection.tasks;

import java.util.HashMap;

import utils.Logger;

import com.rabbitmq.client.QueueingConsumer;

import connection.Connector;

public abstract class NoticeTask extends Task {

	public NoticeTask(QueueingConsumer loginQ, Connector conn) {
		super(loginQ, conn);
	}
	
	@Override
	public void run() {
		HashMap<String, Object> request = null;
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = getLoginQ().nextDelivery();
				request = getConn().getMessage(delivery.getBody());
				executeRequest(request);
			} catch (Exception e) {
				Logger.logERROR(e);
				run();
			}
		}
	}

	protected abstract void executeRequest(HashMap<String, Object> request);

}
