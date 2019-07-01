import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteFileManager extends UnicastRemoteObject implements IfaceRemoteFileManager {

	private static final long serialVersionUID = 1L;

	protected RemoteFileManager() throws RemoteException {
		super();
	}

	@Override
	public int read(String filename, int offset, int length, byte[] buffer) throws RemoteException {
		InputStream is;
		try {
			is = new FileInputStream("./" + filename);
			is.skip(offset);
			int readSize = is.read(buffer, 0, length);
			is.close();
			return readSize;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public int write(String filename, byte[] buffer) throws RemoteException {
		File file;
		OutputStream os;
		try {
			file = new File("./" + filename);
			file.createNewFile(); // Create a file if not exists, otherwise, do nothing
			os = new FileOutputStream(file, true);
			os.write(buffer);
			os.close();
			return buffer.length;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return -1;
	}

}
