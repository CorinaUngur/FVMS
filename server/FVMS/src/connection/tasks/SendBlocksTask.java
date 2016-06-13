package connection.tasks;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import utils.Logger;
import connection.Connector;
import connection.tools.Config;
import connection.tools.SendFileDetails;

public class SendBlocksTask implements Runnable {
	private SendFileDetails details;

	public SendBlocksTask(SendFileDetails details) {
		this.details = details;
	}

	@Override
	public void run() {
		String path = details.path;
		File file = new File(path);
		long file_size = file.length();
		int blockSize = Config.FileBlockSize.getInt();
		long completeBlocksNo = file_size / blockSize;

		try {
			FileInputStream reader = new FileInputStream(file);
			long blockNo = 1;
			while (blockNo < completeBlocksNo) {
				sendBlock(blockSize, reader, blockNo);
				blockNo++;
			}
			if (file_size % completeBlocksNo != 0) {
				long lastBlockSize = file_size % completeBlocksNo;
				sendBlock(lastBlockSize, reader, blockNo);
			}
		} catch (FileNotFoundException e) {
			Logger.logERROR(e);
		} catch (IOException e) {
			Logger.logERROR(e);
		}

	}

	private void sendBlock(long lastBlockSize, FileInputStream reader,
			long blockNo) throws IOException {
		byte[] byteArray = new byte[(int) lastBlockSize];
		DataInputStream din = new DataInputStream(reader);
		for (int i = 0; i <= lastBlockSize; i++) {
			byteArray[0] = (byte) din.readUnsignedByte();
		}
		HashMap<String, Object> blockMessage = new HashMap<String, Object>();
		blockMessage.put("blockNo", blockNo);
		blockMessage.put("fileBlock", byteArray);
		blockMessage.put("blockSize", lastBlockSize);
		Connector.getInstance().sentMessage(details.queue, blockMessage);
	}

}
