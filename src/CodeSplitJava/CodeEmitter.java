package CodeSplitJava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import CodeSplitJava.SourceModel.SM_Method;
import CodeSplitJava.SourceModel.SM_Package;
import CodeSplitJava.SourceModel.SM_Project;
import CodeSplitJava.SourceModel.SM_Type;

public class CodeEmitter {

	private InputArgs inputArgs;
	private SM_Project project;

	public CodeEmitter(InputArgs argsObj, SM_Project project) {
		this.inputArgs = argsObj;
		this.project = project;
	}

	public void emitCode() {
		if (inputArgs.getMode().equals("method"))
			emitCodeByMethods();
		if (inputArgs.getMode().equals("class"))
			emitCodeByClasses();
	}

	private void emitCodeByClasses() {
		String prjFolder = inputArgs.getOutputFolder() + File.separator + project.getName();
		File folderObj = new File(prjFolder);
		if (folderObj.exists()) {
			System.out.println("Folder " + project.getName() + " exists; skipping.");
			return;
		}
		folderObj.mkdir();
		for (SM_Package pkg : project.getPackageList()) {
			Path curFolder = Paths.get(prjFolder, pkg.getName());
			if (!curFolder.toFile().exists())
				curFolder.toFile().mkdir();

			for (SM_Type type : pkg.getTypeList()) {
				Path classFile = Paths.get(curFolder.toString(), type.getName() + ".code");
					writeToFile(classFile.toAbsolutePath().toString(), type.getTypeDeclaration().toString());
				}
			}
		}

	private void emitCodeByMethods() {
		String prjFolder = inputArgs.getOutputFolder() + File.separator + project.getName();
		File folderObj = new File(prjFolder);
		if (folderObj.exists()) {
			System.out.println("Folder " + project.getName() + " exists; skipping.");
			return;
		}
		folderObj.mkdir();
		for (SM_Package pkg : project.getPackageList()) {
			Path curFolder = Paths.get(prjFolder, pkg.getName());
			if (!curFolder.toFile().exists())
				curFolder.toFile().mkdir();

			for (SM_Type type : pkg.getTypeList()) {
				Path classFolder = Paths.get(curFolder.toString(), type.getName());
				if (!classFolder.toFile().exists())
					classFolder.toFile().mkdir();
				for (SM_Method method : type.getMethodList()) {
					Path path = Paths.get(classFolder.toString(), method.getName() + ".code");
					writeToFile(path.toAbsolutePath().toString(), method.getMethodBody());
				}
			}
		}

	}

	private void writeToFile(String path, String str) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(path, true), StandardCharsets.UTF_8))) {
			writer.write(str);
			writer.close();
		} catch (IOException ex) {
			System.out.println("Exception occurred during writing to file. " + ex.getMessage());
		}
	}
}
