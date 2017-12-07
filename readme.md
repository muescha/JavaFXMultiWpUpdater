# JavaFX MultiWPUpdater
MultiWPUpdater should be a help for people who want to update a lot of WordPress instances at the same time by uploading the downloaded
plugin or theme directly on the choosen WordPress instances at the same time.

It is for all the people who are not working with auto updates in WordPress because of security reasons.

The idea was to insert a WordPress instance (FTP Crendentials and Base directory from the instance) via our JavaFX Interface into
a local MySQL Database.

If you want to update a plugin, you can choose your created WordPress instances and update directly all the selected installations.

If a plugin already exits we should rename the folder to pluginname_bkp and upload the new one but only if there is no folder
like pluginname_bkp. If the folder is already there we should remove the old _bkp folder and rename the actual to _bkp and upload
the latest version.

If we do it like this we can go back to the older version if there are some errors appearing.

Required: JDK 8

## TODO:
### Version 1
- Encrypt passwords on inserting into database and decrypt on selecting
- Finish all started methods like for example PluginUpload.checkPluginBackupExists() or PluginUpload.deleteBackupPlugin()
- Extend for themes
- Implement SSH/SFTP Connections

### Version 2
- Check installed WordPress Themes and Plugins including version no.
...

Feel free to join us!

PLEASE NOTE:
THIS APPLICATION IS NOT WORKING AT THE MOMENT, IT IS IN DEVELOPMENT STATE AT THE MOMENT.

# Database
## Create Database
CREATE TABLE WP_INSTANCE (
  ID int(11) unsigned NOT NULL AUTO_INCREMENT,
  NAME varchar(255) DEFAULT NULL,
  HOST varchar(255) DEFAULT NULL,
  USER varchar(255) DEFAULT NULL,
  PASSWORD varchar(255) DEFAULT NULL,
  PORT varchar(255) DEFAULT NULL,
  BASE_DIR varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;