package adapter;

/**
 * @author Amrata Kasture
 *
 */
public interface UpdateAuto {
	public void updateOptionSetName(String Modelname, String OptionSetname, String newName);
	public void updateOptionPrice(String Modelname, String Optionname, String Option, float newprice);
	public void deleteAuto(String string);
	public void updateBasePrice(String string, float basePrice);
}
