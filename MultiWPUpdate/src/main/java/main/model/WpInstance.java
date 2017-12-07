package main.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Manuel Wimmer
 * Date: 29.11.17
 * Desc:
 */

public class WpInstance {
    private IntegerProperty id;
    private StringProperty instanceName;
    private StringProperty instanceHost;
    private StringProperty instanceUser;
    private StringProperty instancePw;
    private IntegerProperty instancePort;
    private StringProperty instanceBaseDir;

    private WpInstance() {
        this(null);
    }

    private WpInstance(String instance) {
        this.instanceName = new SimpleStringProperty(instance);
    }

    public WpInstance(int id, String name, String host, String user, String pw, int port, String baseDir) {
        this.id = new SimpleIntegerProperty(id);
        this.instanceName = new SimpleStringProperty(name);
        this.instanceHost = new SimpleStringProperty(host);
        this.instanceUser = new SimpleStringProperty(user);
        this.instancePw = new SimpleStringProperty(pw);
        this.instancePort = new SimpleIntegerProperty(port);
        this.instanceBaseDir = new SimpleStringProperty(baseDir);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getInstanceName() {
        return instanceName.get();
    }

    public StringProperty instanceNameProperty() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName.set(instanceName);
    }

    public String getInstanceHost() {
        return instanceHost.get();
    }

    public StringProperty instanceHostProperty() {
        return instanceHost;
    }

    public void setInstanceHost(String instanceHost) {
        this.instanceHost.set(instanceHost);
    }

    public String getInstanceUser() {
        return instanceUser.get();
    }

    public StringProperty instanceUserProperty() {
        return instanceUser;
    }

    public void setInstanceUser(String instanceUser) {
        this.instanceUser.set(instanceUser);
    }

    public String getInstancePw() { return instancePw.get(); }

    public StringProperty instancePwProperty() {
        return instancePw;
    }

    public void setInstancePw(String instancePw) { this.instancePw.set(instancePw); }

    public int getInstancePort() {
        return instancePort.get();
    }

    public IntegerProperty instancePortProperty() {
        return instancePort;
    }

    public void setInstancePort(int instancePort) {
        this.instancePort.set(instancePort);
    }

    public String getInstanceBaseDir() {
        return instanceBaseDir.get();
    }

    public StringProperty instanceBaseDirProperty() {
        return instanceBaseDir;
    }

    public void setInstanceBaseDir(String instanceBaseDir) {
        this.instanceBaseDir.set(instanceBaseDir);
    }

}
