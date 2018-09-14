package CodeSplitJava;

import java.io.File;

public class InputArgs {
	private String sourceFolder;
	private String outputFolder;
	private String mode;
	
	public InputArgs() {
		//It is invoked only in case of error
	}

	public InputArgs(String inputFolderPath, String outputFolderPath, String mode) {
		sourceFolder = inputFolderPath;
		outputFolder = outputFolderPath;
		this.mode = mode;
		checkEssentialInputs();
	}

	public String getSourceFolder() {
		return sourceFolder;
	}

	public String getOutputFolder() {
		return outputFolder;
	}

	public String getMode()
	{
		return mode;
	}
	//At least, the source folder must be specified
	private void checkEssentialInputs() {
		if (sourceFolder==null)
		{
			throw new IllegalArgumentException("Input source folder is not specified.");
		}
		File folder = new File(sourceFolder);
		if (!(folder.exists() && folder.isDirectory()))
		{
			throw new IllegalArgumentException("Input source folder path is not valid.");
		}
		File outFolder = new File(outputFolder);
		if (outFolder.exists() && outFolder.isFile())
		{
			throw new IllegalArgumentException("The specified output folder path is not valid.");
		}
		if (!(mode.equals("method") || mode.equals("class")))
		{
			throw new IllegalArgumentException("The specified mode is not valid.");
		}
	}
	
	/***
	 * Analyzes the provided <b>sourceFolder</b> variable and 
	 * extracts the name of the project. 
	 * @return A String with the name of the project. 
	 * When the given sourceFolder has a value of <i>src</i> 
	 * or <i>source</i> then the method returns 
	 * the name of the direct parent directory
	 */
	public String getProjectName() {
		File temp = new File(sourceFolder);
		if (temp.getName().equals("src") || temp.getName().equals("source")) {
			return new File(temp.getParent()).getName();
		} else {
			return new File(sourceFolder).getName();
		}
	}
}
