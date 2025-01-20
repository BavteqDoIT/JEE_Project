package jakarta.faces.simplesecurity;

import java.util.HashSet;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Class representing remote client in web applications.
 *   
 * @author Przemysław Kudłacik
 *
 * @param <T> Any class allowing to store additional information to session (i.e. User entity)
 */
public class RemoteClient<T> {

	private String login;
	private String passesId;
	private String name;
	private String remoteAddr;
	private String remoteHost;
	private int remotePort;
	private T details;

	private HashSet<String> roles = new HashSet<String>();

	public RemoteClient() {
	}

	public RemoteClient(ServletRequest request) {
		this.remoteAddr = request.getRemoteAddr();
		this.remoteHost = request.getRemoteHost();
		this.remotePort = request.getRemotePort();
	}

	public RemoteClient(String login, String passesId, String name, ServletRequest request) {
		this.login = login;
		this.passesId = passesId;
		this.name = name;
		if (request != null) {
			this.remoteAddr = request.getRemoteAddr();
			this.remoteHost = request.getRemoteHost();
			this.remotePort = request.getRemotePort();			
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassesId() {
		return passesId;
	}

	public void setPassesId(String passesId) {
		this.passesId = passesId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public T getDetails() {
		return details;
	}

	public void setDetails(T details) {
		this.details = details;
	}

	public HashSet<String> getRoles() {
		return roles;
	}

	public void setRoles(HashSet<String> roles) {
		this.roles = roles;
	}

	public boolean isInRole(String role) {
		return roles.contains(role);
	}

	public boolean isInOneRole(HashSet<String> roles) {
		boolean found = false;
		for (String role : roles) {
			if (this.roles.contains(role)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public void store(HttpServletRequest request) {
		this.remoteAddr = request.getRemoteAddr();
		this.remoteHost = request.getRemoteHost();
		this.remotePort = request.getRemotePort();
		HttpSession session = request.getSession();
		session.setAttribute("remoteClient", this);
	}

	public static RemoteClient load(HttpSession session) {
		return (RemoteClient) session.getAttribute("remoteClient");
	}

}
