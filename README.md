Introduction
=====================
The Eclipse plugins help coders to navigate from java codes to the IBatis/MyBatis configuration xml files. The sqlmap elements of IBatis/MyBatis can be shown when coders press Ctrl and click the sqlmap element id in Java code. The plugin also provide a dialog to find the IBatis/MyBatis sqlmap elements.

User Guide
=====================

 Install
------------------
The MyBatisLink plugin can be installed by the following two types:
 1 .  Use the update link: http://mybatis-link.googlecode.com/svn/trunk/. Open Help -> Install New Software, then input the update link to install the plugins.
 2 .  Download the [com.netease.mybatislink_1.0.0.jar](https://sourceforge.net/projects/mybatislink/files/latest/download) and copy it to the eclipse/plugins folder.

 How to use
------------------
 1 .  Open the Java source files which use the IBatis/MyBatis APIs, then press CTRL and click the IBatis/MyBatis id (the argument of IBatis/MyBatis API like update, insert or selectList). The xml file which defines the IBatis/MyBatis sql map configuration will be open and the id will be marked.

 ![Navigate from Java to XML](https://sourceforge.net/p/mybatislink/screenshot/screenshot1.PNG)

 2 .  Open IBatis/MyBatis config file, then press CTRL and click the reference of resultMap. The xml file resultMap will be marked.

 ![Navigate from XML to XML](https://sourceforge.net/p/mybatislink/screenshot/screenshot2.PNG)

 3 .  Open the button on the bar called "Open iBatis Config", one dialog will be shown. Then input the id name you want to search, the IBatis/MyBatis statement id can be found in the list view.

 ![Open IBatis/MyBatis Config Dialog](https://sourceforge.net/p/mybatislink/screenshot/screenshot3.PNG)

[[project_admins]]
[[download_button]]