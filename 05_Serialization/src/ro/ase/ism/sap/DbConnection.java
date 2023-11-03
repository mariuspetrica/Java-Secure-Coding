package ro.ase.ism.sap;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class DbConnection implements Serializable{
	
	//called once, when the class is loaded by the JVM
	static {//static = attribute defined inside the class but global for the objects of the class type
		System.out.println(DbConnection.class.getName() + "loaded ");
		System.out.println("Loading configuration");
		
	}
	
	//called before each constructor
	{	
	System.out.println("Initializing a DbConnection");
	}
	
	private static final long serialVersionUID = 4988458112122659323l;
			
			
	private String serverName;
	private int port;
	transient private byte[] password; //transiet attributes are not serializable
	private String userId;
	private boolean useSecureConnection;
	private int newAttr;

	
	public DbConnection(String serverName, int port, byte[] password, String userId, boolean useSecureConnection) {
		this.serverName = serverName;
		this.port = port;
		this.password = password;
		this.userId = userId;
		this.useSecureConnection = useSecureConnection;
	}

	public String getServerName() {
		return serverName;
	}

	public int getPort() {
		return port;
	}
//dont return a refference:
//	public byte[] getPassword() {
//		return password;
//	}
	
	//instead return a string
		public String getPassword() {
			if(password == null) {
				return "";
			}else
			return new String(password);
		}


	public String getUserId() {
		return userId;
	}

	public boolean isUseSecureConnection() {
		return useSecureConnection;
	}
 
	public void writeObject(DataOutputStream dos) throws IOException {
		//we serialize the password
		//we have full control over what to serialize
		dos.writeUTF(serverName);
		dos.writeInt(port);
		dos.writeUTF(userId);
		//serialize the password byte array
		short passSize = (short) password.length;
		dos.writeShort(passSize);
		dos.write(password);
		dos.writeBoolean(useSecureConnection);
		
	}
	//password is shownn up as a size+value (2 bytes for size and the following are contents)
	public void readObject(DataInputStream dis) throws IOException
	{
		this.serverName = dis.readUTF();
		this.port = dis.readInt();
		this.userId = dis.readUTF();
		short passSize = dis.readShort();
		this.password = new byte[passSize];
		dis.read(password);
		this.useSecureConnection = dis.readBoolean();
	
	}
	
	
}
