package de.jgoldhammer.alfresco.jscript.file;

import com.google.common.base.Preconditions;
import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.alfresco.repo.jscript.ScriptNode;
import org.alfresco.repo.mail.AlfrescoJavaMailSender;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.model.FileFolderService;
import org.alfresco.service.cmr.model.FileFolderServiceType;
import org.alfresco.service.cmr.model.FileInfo;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.MimetypeService;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.io.FileUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * the script file writer can create a file on the filesystem, write to it and persist the file in the alfresco repository.
 * Examples are writing CSV files for reports or logging of a long running process peristently.
 *
 * When using this root object in several threads, please be careful that createFile is always
 * used in the same threads like the other methods.
 */
public class ScriptFileWriter extends BaseScopableProcessorExtension {

	NamespaceService namespaceService;
	FileFolderService fileFolderService;
	ServiceRegistry serviceRegistry;
	JavaMailSender mailService;
	MimetypeService mimetypeService;

	public void setMimetypeService(MimetypeService mimetypeService) {
		this.mimetypeService = mimetypeService;
	}

	public void setMailService(JavaMailSender mailService) {
		this.mailService = mailService;
	}

	public void setServiceRegistry(ServiceRegistry serviceRegistry) {
		this.serviceRegistry = serviceRegistry;
	}

	public void setFileFolderService(FileFolderService fileFolderService) {
		this.fileFolderService = fileFolderService;
	}

	public void setNamespaceService(NamespaceService namespaceService) {
		this.namespaceService = namespaceService;
	}

	ThreadLocal<File> files = new ThreadLocal<File>();

	/**
	 * create a new file and register it in the threadlocal
	 */
	public void createFile(){
		File tempFile = null;
		try {
			tempFile = File.createTempFile("scriptFileWriter","report");
		} catch (IOException e) {
			throw new AlfrescoRuntimeException("Cannot create the temp file for the report",e);
		}
		files.set(tempFile);
	}

	/**
	 * write one line to the current file. In favor of performance, the method writeLines should be used.
	 * @param content content of the line to write
	 */

	public void writeLine(String content){
		writeLine(content, false);

	}

	/**
	 * append one line to the current file. In favor of performance, the method appendLines should be used.
	 *
	 * @param content content of the line to write
	 */

	public void appendLine(String content){
		writeLine(content, true);
	}


	/**
	 * write or append one line to the current file. In favor of performance, the method writeLines should be used.
	 *
	 * @param content content of the line to write
	 * @param append true if the content should be appended, false if the previous content should be overwritten
	 */
	private void writeLine(String content, boolean append){
		File currentFile = files.get();
		Preconditions.checkNotNull(currentFile,"There is no file to write to- please create a file via createFile first... ");
		List<String> contents= new ArrayList<>();
		contents.add(content);

		try {
			FileUtils.writeLines(currentFile,"UTF-8", contents, append);
		} catch (IOException e) {
			throw new AlfrescoRuntimeException("Cannot write content "+content+" to file "+currentFile.getAbsolutePath(), e);
		}
	}



	/**
	 * write lines to the current file
	 *
	 * @param content the array of strings with content for each line
	 */
	public void writeLines(String[] content){
		writeLines(content, false);
	}

	/**
	 * append several lines to the current file.
	 *
	 * @param content the array of strings with content for each line
	 */
	public void appendLines(String[] content){
		writeLines(content, true);
	}


	/**
	 * write or append several lines to the current file
	 *
	 * @param content the array of strings with content for each line
	 * @param append true if the content should be appended, false if the previous content should be overwritten
	 */
	private void writeLines(String[] content, boolean append){
		File currentFile = files.get();
		Preconditions.checkNotNull(currentFile,"There is no file to write to- please create a file via createFile first... ");

		try {
			FileUtils.writeLines(currentFile,"UTF-8", Arrays.asList(content), append);
		} catch (IOException e) {
			throw new AlfrescoRuntimeException("Cannot write content "+content+" to file "+currentFile.getAbsolutePath(), e);
		}

	}

	/**
	 * persist the file in the alfresco repository by using the following parameters.
	 *
	 * @param fileName the filename for the written file in the alfresco repository
	 * @param folderNodeRef the parent folder id where the content should be written
	 * @param mimetype the mimetype to use, e.g. "text/csv" for csv mimetype
	 * @param shortTypeName optional - the short type name e.g. cm:content for the file being written.
	 * @param preserveFile optional - if true if the file should not be deleted. Consider always false!
	 */
	public ScriptNode persist(String fileName, String folderNodeRef, String mimetype, String shortTypeName, boolean preserveFile){
		Preconditions.checkNotNull(fileName);
		Preconditions.checkNotNull(folderNodeRef);
		Preconditions.checkArgument(NodeRef.isNodeRef(folderNodeRef),
				"folderNodeRef is not a valid noderef- please fix it baby!");

		if(shortTypeName==null){
			shortTypeName="cm:content";
		}
		QName fileContentType = QName.createQName(shortTypeName,namespaceService);
		Preconditions.checkArgument(fileFolderService.getType(fileContentType)== FileFolderServiceType.FILE,
				"The shortTypeName must be cm:content or a subtype of it...");

		FileInfo newFile = fileFolderService.create(new NodeRef(folderNodeRef),
				fileName,fileContentType);

		ContentWriter writer = fileFolderService.getWriter(newFile.getNodeRef());
		writer.setEncoding("UTF-8");
		writer.setMimetype(mimetype);
		writer.putContent(files.get());

		if(!preserveFile){
			File file = files.get();
			file.delete();
		}

		// remove the thread local reference to avoid memory errors
		files.remove();

		return new ScriptNode(newFile.getNodeRef(), serviceRegistry,getScope());
	}


	/**
	 * mail the result of the file to one or more recipients. Please be careful when sending big files.
	 * Some mailserver will reject attachment with sizes bigger than 10 MB or less.
	 *
	 * @param from the mail adress
	 * @param recipients the list of recipients
	 * @param subject subject of the mail  (not optional!)
	 * @param body optional - body of the mail
	 * @param reportName the name of the report- should include the extension of file already.
	 * @param preserveFile if true if the file should not be deleted. Consider always false!
	 *
	 * We are not using the mailaction from alfresco because it does not support sending attachments (since years!!)
	 */
	public void mail(String from, List<String> recipients, String subject, String body, String reportName, boolean preserveFile) {

		Preconditions.checkNotNull(from);
		Preconditions.checkNotNull(recipients);
		Preconditions.checkNotNull(subject);
		File file = Preconditions.checkNotNull(files.get());

		try {
			MimeMessage mimeMessage = mailService.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
			helper.setSubject(subject);

			if(body!=null){
				helper.setText(body,true);
			}

			helper.setFrom(new InternetAddress(from));
			helper.setTo(getAddresses(recipients));

			//add attachment
			if(reportName==null){
				reportName="Report.unknown";
			}
			helper.addAttachment(reportName, file);
			mailService.send(mimeMessage);
		}
		catch(MessagingException me) {
			throw new AlfrescoRuntimeException("Cannot send mail to "+from+" with subject "+subject+" and file"+(file),me);
		}

		if(!preserveFile){
			file.delete();
		}

		// remove the thread local reference to avoid memory errors
		files.remove();
	}

	private static InternetAddress[] getAddresses(List<String> emailIds) {
		InternetAddress[] toAddresses = new InternetAddress[emailIds.size()];
		try {
			for(int i=0; i<emailIds.size();i++ ) {
				toAddresses[i] = new InternetAddress(emailIds.get(i));
			}
		}
		catch(AddressException ae) {
			throw new AlfrescoRuntimeException("Cannot determine the recipient adresses",ae);
		}
		return toAddresses;
	}



}
