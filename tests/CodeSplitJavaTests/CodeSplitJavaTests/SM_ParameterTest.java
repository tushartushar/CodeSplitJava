package CodeSplitJavaTests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import CodeSplitJava.InputArgs;
import CodeSplitJava.SourceModel.SM_Method;
import CodeSplitJava.SourceModel.SM_Parameter;
import CodeSplitJava.SourceModel.SM_Project;

public class SM_ParameterTest extends CodeSplitJavaTests {
	
	private SM_Project project;
	private SM_Parameter newParameter;
	private List<SM_Method> methods;
	private List<SM_Parameter> parameters;

	@Before
	public void setUp() {
		project = new SM_Project(new InputArgs(getTestingPath() + File.separator + "test_inputs", getTestingPath(), "method"));
		project.parse();
		project.resolve();
		methods = project.getPackageList().get(0).getTypeList().get(0).getMethodList();
	}

	@Test
	public void SM_Parameter_check_getName() {
		parameters = getSpecificMethod("TestMethods").getParameterList();
		newParameter = parameters.get(0);
		assertEquals(newParameter.getName(), "name");
	}


	@Test
	public void SM_Parameter_getType_from_source() {
		parameters = getSpecificMethod("publicMethod").getParameterList();
		newParameter = parameters.get(0);
		assertEquals(newParameter.isPrimitiveType(), false);
		assertEquals(newParameter.getType().getName(), "TestMethods");
	}
	private SM_Method getSpecificMethod(String methodName) {
		for(SM_Method method:methods)
			if(method.getName().equals(methodName))
				return method;
		return null;
	}
}
