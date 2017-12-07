package main.upload;

import core.FTPUploader;
import javafx.collections.ObservableList;
import main.model.WpInstance;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Manuel Wimmer
 * Date: 30.11.17
 * Desc:
 */

public class PluginUpload {

    public void upload(ObservableList<WpInstance> uploadList, File localFolder) throws Exception {
        for (WpInstance item : uploadList) {
            FTPClient ftp = connect(item);
            if (ftp != null) {
                if (checkPluginExists(item, ftp, localFolder)) {

                    System.out.println("Plugin Exists");

//                    if (checkPluginBackupExists()) {
//                        //delete backup
//                        //rename folder to backup
//                    } else {
//                        //rename folder to backup
//                    }
                }
            } else {
                System.out.println("Connection to FTP Server failed.");
            }
        }
    }

    private boolean checkPluginExists(WpInstance instance, FTPClient ftp, File localFolder) throws Exception {
        //connect to instance ftp
        //goto /wp-content/plugins/
        //check if local upload folder name is already there
        //if there -> true, otherwise -> false

        System.out.println("Connecting to: " + instance.getInstanceName() + "(" + instance.getInstanceHost() + ")");
        FTPFile[] pluginDirectories = ftp.listDirectories(instance.getInstanceBaseDir() + "/wp-content/plugin/");

        for (int i = 0, pluginDirectoriesLength = pluginDirectories.length; i < pluginDirectoriesLength; i++) {
            FTPFile plugin = pluginDirectories[i];
            String dirName = localFolder.toString().substring(localFolder.toString().lastIndexOf(File.separator) + 1);
            return plugin.toString().equals(dirName);
        }
        return false;
    }

    private boolean checkPluginBackupExists(WpInstance instance, File uploadDir) {
        //get name from selected plugin
        //check if already exits for instance
        return false;
    }

    private void deleteBackupPlugin(WpInstance instance, File uploadDir) {
        //delete backup plugin
    }

    private void renameExistingPlugin(WpInstance instance, File uploadDir) {
        if (!checkPluginBackupExists(instance, uploadDir)) {
            if (checkPluginBackupExists(instance, uploadDir)) {
                deleteBackupPlugin(instance, uploadDir);
            }
        }
    }

    private FTPClient connect(WpInstance instance) {
        FTPClient ftpClient = new FTPClient();

        try {

            ftpClient.connect(instance.getInstanceHost(), instance.getInstancePort());
            showServerReply(ftpClient);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return null;
            }

            boolean success = ftpClient.login(instance.getInstanceUser(), instance.getInstancePw());
            showServerReply(ftpClient);

            if (success) {
                System.out.println("Connection successfully.");
                return ftpClient;
            } else {
                System.out.println("Could not login to the server");
                return null;
            }

        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        } finally {
            closeConnection(ftpClient);
        }
        return ftpClient;
    }

    private void closeConnection(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void printFileDetails(FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName();
            if (file.isDirectory()) {
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());

            System.out.println(details);
        }
    }

    private void printNames(String files[]) {
        if (files != null && files.length > 0) {
            for (String aFile: files) {
                System.out.println(aFile);
            }
        }
    }

    private void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}
