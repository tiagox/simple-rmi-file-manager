import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IfaceRemoteFileManager extends Remote {

	public int read(String filename, int offset, int length, byte[] buffer) throws RemoteException;

	public int write(String filename, byte[] buffer) throws RemoteException;
}
