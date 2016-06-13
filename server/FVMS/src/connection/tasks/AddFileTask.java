package connection.tasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.LinkedHashMap;

import utils.Logger;
import versioning.FileSystem;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

import config.Settings;
import connection.Connector;

public class AddFileTask implements Runnable {

	private LinkedHashMap<String, Object> file;
	private QueueingConsumer consumer;

	public AddFileTask(QueueingConsumer consumer,
			LinkedHashMap<String, Object> file) {
		this.file = file;
		this.consumer = consumer;
	}

	@Override
	public void run() {
		long blocksNo = Long.valueOf(file.get("blocksNo").toString());
		FileSystem fs = new FileSystem();
		String tempFile = Settings.TEMP_PATH + "/"
				+ fs.getNameFromPath(file.get("file_rpath").toString());
		File fileOnDisk = new File(tempFile);
		try {
			fileOnDisk.createNewFile();
			while (blocksNo > 0) {
				Delivery fileBlock = consumer.nextDelivery();
				byte[] body = fileBlock.getBody();
				HashMap<String, Object> fileDetails = Connector.getInstance()
						.getMessage(body);
				Files.write(Paths.get(tempFile), fileDetails.get("fileBlock")
						.toString().getBytes(), StandardOpenOption.APPEND);
				blocksNo--;
			}
			String owner = file.get("owner").toString();
			fs.add_newFile(fileOnDisk, file.get("file_rpath").toString(),
					owner, Integer.valueOf(file.get("pid").toString()));
		} catch (ShutdownSignalException e) {
			Logger.logERROR(e);
		} catch (ConsumerCancelledException e) {
			Logger.logERROR(e);
		} catch (FileNotFoundException e1) {
			Logger.logERROR(e1);
		} catch (IOException e) {
			Logger.logERROR(e);
		} catch (InterruptedException e) {
			Logger.logERROR(e);
		} catch (Throwable t) {
			Logger.logERROR(t, t.getMessage());

		}

	}

}
