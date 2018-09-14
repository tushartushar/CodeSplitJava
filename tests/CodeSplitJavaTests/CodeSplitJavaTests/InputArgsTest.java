package CodeSplitJavaTests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import CodeSplitJava.InputArgs;

public class InputArgsTest extends CodeSplitJavaTests {

	// Negative case- folder path specified rather than input batch file
	@Test(expected = IllegalArgumentException.class)
	public void testInputArgs_negative_folder() {
		new InputArgs(null, getTestingPath(), "method");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputArgs_passingInvalidFilePath_ReturnsException() {
		new InputArgs(getTestingPath() + File.separator + "invalidFile.txt", getTestingPath(), "method");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInputArgs_passingFilePath_ReturnsException() {
		new InputArgs(getTestingPath() + File.separator + "TestFiles" + File.separator + "singleJavaFile.txt", getTestingPath(), "method");
	}
	
	@Test
	public void testInputArgs_getProjectName() {
		InputArgs args = new InputArgs(System.getProperty("user.dir"), getTestingPath(), "method");
		String currentProjectDir = new File(System.getProperty("user.dir")).getName();
		assertEquals(currentProjectDir, args.getProjectName());
	}
	
	@Test
	public void testInputArgs_getProjectName_src() {
		InputArgs args = new InputArgs(System.getProperty("user.dir") + File.separator + "src", getTestingPath(), "method");
		String currentProjectDir = new File(System.getProperty("user.dir")).getName();
		assertEquals(currentProjectDir, args.getProjectName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInputArgs_passingInvalidMode_ReturnsException() {
		new InputArgs(System.getProperty("user.dir"), getTestingPath(), "statement");
	}
}