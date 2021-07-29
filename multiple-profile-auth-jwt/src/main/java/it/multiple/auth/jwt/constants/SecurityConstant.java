package it.multiple.auth.jwt.constants;

/**
 * @author gchiavolotti
 */
public class SecurityConstant {

	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_HEADER = "Authorization";
    public static final String JWT_SECRET = "yeWAgVDfb$!MFn@MCJVN7uqkznHbDLR#";
    
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private SecurityConstant(){}


}
