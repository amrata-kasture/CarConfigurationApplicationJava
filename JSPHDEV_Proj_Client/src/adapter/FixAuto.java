package adapter;

/**Interface to access Fix methods of AutoException class
 * @author Amrata Kasture
 *
 */
public interface FixAuto {
	public String wrapFix(int errNo);
	public void fix(int errNo);
}
