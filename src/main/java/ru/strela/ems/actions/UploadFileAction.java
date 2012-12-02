package ru.strela.ems.actions;


import eu.medsea.mimeutil.MimeUtil;
import net.sf.jmimemagic.*;
import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.acting.AbstractAction;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Redirector;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.SourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ru.strela.ems.core.dao.ObjectTypeDao;
import ru.strela.ems.core.model.FileObject;
import ru.strela.ems.core.model.Folder;
import ru.strela.ems.core.model.ObjectType;
import ru.strela.ems.security.model.Customer;
import ru.strela.ems.security.service.CustomerService;
import ru.strela.ems.core.service.FileObjectService;
import ru.strela.ems.core.service.FolderService;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.file.FileAddition;
import ru.tastika.tools.util.Utilities;

import javax.servlet.ServletInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * User: hobal
 * Date: 16.09.2010
 * Time: 2:32:40
 */
public class UploadFileAction extends AbstractAction {

    private final static Logger log = LoggerFactory.getLogger(UploadFileAction.class);
    public Map act(Redirector redirector,
                   SourceResolver resolver,
                   Map objectModel,
                   String source,
                   Parameters params) {
        Map sitemapParams = null;
         MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        Request request = ObjectModelHelper.getRequest(objectModel);
        String filePath = request.getParameter("qqfile");
        if (filePath != null && filePath.trim().length() > 0) {
            String fullFileName = FileAddition.getFileName(filePath);
//            String ext = FileAddition.getFileExtension(fullFileName);

            String fileName = FileAddition.getFileBaseName(fullFileName);
            String fileExtension = FileAddition.getFileExtension(fullFileName);


            WebApplicationContext context = ServerTools.getWebApplicationContext();
            FileObjectService fileObjectService = (FileObjectService) context.getBean("fileObjectService");
            String contextRealPath = fileObjectService.getContextRealPath();
            String mediatekaFolder = ServerTools.getGlobalParameter("mediatekaFolder").toString();

            Integer folderId = Utilities.parseStringToInteger(request.getParameter("folderId"));
            String parentFolder = mediatekaFolder;
            Folder parentObject = null;
            if (folderId > 0) {
                FolderService folderService = (FolderService) context.getBean("folderService");
                parentObject = (Folder) folderService.getTypifiedObject(folderId);
                if (parentObject != null) {
                    parentFolder = parentObject.getPath();
                }
            }


//            FileObject fileObject = fileObjectService.getFileObjectByPath(parentFolder + fileName);
//            if (fileObject == null) {
            FileObject fileObject = new FileObject();

            int objectsCount;
            if (parentObject != null) {
                objectsCount = parentObject.getEmsObject().getChildrenCount();
            }
            else {
                ObjectTypeDao objectTypeDao = (ObjectTypeDao) context.getBean("objectTypeDao");
                ObjectType objectType = objectTypeDao.getObjectType(fileObject.getClass().getName());
                objectsCount = objectTypeDao.getRootObjectsForTypeCount(objectType);
            }
            fileObject.setPosition(objectsCount + 1);

            fileObject.setParent(parentObject);

            String freeSystemName = fileObjectService.getFreeSystemName();
            fileObject.setSystemName(freeSystemName);

            HashMap<String, Object> userData = ServerTools.getLoggedInUserData(request.getSession());

            /*UserService userService = (UserService) context.getBean("userService");
            User user = userService.getUser((String) userData.get(ServerTools.USER_DATA_ID));
            fileObject.setOwner(user);*/
            CustomerService customerService = (CustomerService) context.getBean("customerService");
            Integer userId = (Integer) userData.get(ServerTools.USER_DATA_ID);
            if (userId != null) {
                Customer customer = customerService.getCustomerByLogin(userData.get(ServerTools.USER_DATA_NAME).toString());
                fileObject.setOwner(customer);
            }
//            }

            File parentDir = new java.io.File(contextRealPath, parentFolder);
            boolean canCreate = true;
            if (!parentDir.isDirectory()) {
                canCreate = parentDir.mkdirs();
            }

            if (canCreate) {

                fileObject.setName(fileName);
                fileObjectService.save(fileObject);
                String fileRealName = fileObject.getId() + "-" + fileName + (fileExtension.length() > 0 ? "." + fileExtension : "");
                fileObject.setPath(parentFolder + fileRealName);
                File realFile = new File(contextRealPath, fileObject.getPath());
                if (realFile.isFile()) {
                    canCreate = realFile.delete();
                }

                if (canCreate) {
                    File file = new File(filePath);
                    if (file.isFile()) {
                        canCreate = file.renameTo(realFile);
                    }
                    else {
                        try {
                            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(realFile));
                            ServletInputStream inputStream = request.getInputStream();
                            int c;
                            while ((c = inputStream.read()) != -1) {
                                bufferedOutputStream.write(c);
                            }
                            inputStream.close();
                            bufferedOutputStream.close();
                        }
                        catch (IOException e) {
                            canCreate = false;
                            e.printStackTrace();
                        }
                    }
                }
                if (canCreate) {
                    String mimeType = "";
//                    MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
                    Collection<?> mimeTypes = MimeUtil.getMimeTypes(realFile);
                    //log.info(mimeTypes.toString());
                    if (mimeTypes.size() > 0) {
                        mimeType = mimeTypes.iterator().next().toString();
                    }
                    if (mimeType.length() == 0) {
                        try {
                            MagicMatch magicMatch = Magic.getMagicMatch(realFile, true);
                            mimeType = magicMatch.getMimeType();
                        }
                        catch (MagicParseException e) {
                            e.printStackTrace();
                        }
                        catch (MagicMatchNotFoundException e) {
                            //log.info("MagicMatchNotFoundException");
                        }
                        catch (MagicException e) {
                            e.printStackTrace();
                        }
                    }

                    fileObject.setContentType(mimeType);
                    fileObject.setSize((int) realFile.length());


                    fileObjectService.save(fileObject);

                    fileObject.createPreview(contextRealPath);
                    sitemapParams = new HashMap();
                }
                if (!canCreate) {
                    fileObjectService.deleteObject(fileObject);
                }
            }

        }
        return sitemapParams;
    }

}