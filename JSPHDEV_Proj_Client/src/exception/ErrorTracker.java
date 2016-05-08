package exception;

/**
 * Enumeration for storing Error Numbers and Error Messages
 * @author Amrata Kasture
 *
 */

public enum ErrorTracker {
	WRONG_FILE_PATH(1,"The given File location is not found."),
	IO_ISSUE(2,"Input/Output Operations are disabled or Interrupted on the Input File."),
	BASE_PRICE_MISSING(3,"Base Price for Model is Mandatory field which is missing in Input file."),
	OPTION_VALUES_MISSING(4,"Option Values are missing. Validate Input file content."),
	INCOMPATIBLE_FILE_FORMAT(5,"One of OptionSet(Property) Name is missing in File."),
	FILE_ACCESS_DENIED(6,"Access is denied on this File.");
	
	  private final int code;
	  private final String description;

	  private ErrorTracker(int code, String description) {
	    this.code = code;
	    this.description = description;
	  }

	  public String getDescription() {
	     return description;
	  }

	  public int getCode() {
	     return code;
	  }

	  @Override
	  public String toString() {
	    return code + ": " + description;
	  }
}
