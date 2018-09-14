package CodeSplitJava.SourceModel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jdt.core.dom.CompilationUnit;

import CodeSplitJava.InputArgs;

public class SM_Package extends SM_SourceItem {
	private List<CompilationUnit> compilationUnitList;
	private List<SM_Type> typeList = new ArrayList<>();
	private SM_Project parentProject;
	private InputArgs inputArgs;

	public SM_Package(String packageName, SM_Project parentObj, InputArgs inputArgs) {
		name = packageName;
		compilationUnitList = new ArrayList<CompilationUnit>();
		parentProject = parentObj;
		this.inputArgs = inputArgs;
	}

	public SM_Project getParentProject() {
		return parentProject;
	}
	
	 public List<CompilationUnit> getCompilationUnitList() { 
		 return compilationUnitList; 
     }
	 
	public List<SM_Type> getTypeList() {
		return typeList;
	}

	void addCompilationUnit(CompilationUnit unit) {
		compilationUnitList.add(unit);
	}

	private void addNestedClass(List<SM_Type> list) {
		if (list.size() > 1) {
			for (int i = 1; i < list.size(); i++) {
				typeList.add(list.get(i));
				list.get(0).addNestedClass(list.get(i));
				list.get(i).setNestedClass(list.get(0).getTypeDeclaration());
			}
		}
	}

	private void parseTypes(SM_Package parentPkg) {
		for (SM_Type type : typeList) {
			type.parse();
		}
	}

	@Override
	public void printDebugLog(PrintWriter writer) {
		print(writer, "Package: " + name);
		for (SM_Type type : typeList) {
			type.printDebugLog(writer);
		}
		print(writer, "----");
	}
	
	@Override
	public void parse() {

		for (CompilationUnit unit : compilationUnitList) {
			TypeVisitor visitor = new TypeVisitor(unit, this, inputArgs);
			unit.accept(visitor);
			List<SM_Type> list = visitor.getTypeList();
			if (list.size() > 0) {
				if (list.size() == 1) {
					typeList.addAll(list); // if the compilation unit contains
											// only one class; simpler case,
											// there is no nested classes
				} else {
					typeList.add(list.get(0));
					addNestedClass(list);
				}
			}
		}
		parseTypes(this);
	}

	@Override
	public void resolve() {
		for (SM_Type type : typeList) { 
			type.resolve();
		}
	}
}
