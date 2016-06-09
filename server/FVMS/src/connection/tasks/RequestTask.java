package connection.tasks;

import java.util.HashMap;

import utils.Logger;

import com.rabbitmq.client.QueueingConsumer;

public abstract class RequestTask extends Task {
	public RequestTask(QueueingConsumer loginQ) {
		super(loginQ);
	}

	@Override
	public void run() {
		HashMap<String, Object> request = null;
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = getQueue().nextDelivery();
				request = getConn().getMessage(delivery.getBody());
				HashMap<String, Object> responseMap = getResponse(request);
				getConn().sendResponse(delivery.getProperties(), responseMap);
			} catch (Exception e) {
				Logger.logERROR(e);
				run();
			}
		}

	}

	protected abstract HashMap<String, Object> getResponse(
			HashMap<String, Object> request);

}
