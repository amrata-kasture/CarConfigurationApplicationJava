package server;

import java.util.Properties;

/**Interface for hiding implementations of BuildCarModelOptions
 * @author Amrata Kasture
 *
 */

public interface AutoServer {
	public boolean buildAutoFromProp(Properties clientProp);
	public void buildAutoFromPropFile(String filename);
	public String[] listAllModels();
	public String serializeAndSend(String ModelName);
}
