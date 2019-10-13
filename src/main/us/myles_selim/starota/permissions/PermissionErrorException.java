package us.myles_selim.starota.permissions;

public class PermissionErrorException extends RuntimeException {

	public PermissionErrorException(String msg) {
		super(msg);
	}

	public PermissionErrorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PermissionErrorException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3792471944509026811L;

}
