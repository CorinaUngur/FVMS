package connection.tools;

import java.io.IOException;
import java.io.OutputStream;

public class StringOutputStream extends OutputStream {

	String builder;

	public StringOutputStream() {
		builder = new String();
	}

	@Override
	public void write(int b) throws IOException {
		byte by = (byte) b;
		builder = builder.concat(String.valueOf(by));
	}
	@Override
	public void write(byte[] b) throws IOException {
		builder = builder.concat(String.valueOf(b));
	}

	@Override
	public String toString() {
		return builder.toString();
	}

}
