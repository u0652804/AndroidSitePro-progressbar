# AndroidSitePro-progressbar

Src of side project description :

1. CustomerProgressbar : Test customer progressbar to loop show 0 > 35 > 100 percent

2. DownloadHelperProgressbar : 

   Packaged donwloadHelper(with AsyncTask) and show download percent with progressbar.

   Can start and stop download file

   files description :

1. 
 - drawable > custom_progressbar_horizontal.xml 
 - values > colors.xml
 - values > styles.xml
 - MainActivity
 - layout > activity_main.xml

2. 
 - PermissionHelper : help to check and grant permissions such as R/W storage
 - DownloadHelper : use AsyncTask to download file to local and call callback

Demo result :

1.

![Android Demo](https://github.com/u0652804/AndroidSitePro-progressbar/blob/main/demo/demo1-1.png)

2.

![Android Demo](https://github.com/u0652804/AndroidSitePro-progressbar/blob/main/demo/demo2-1.png)

References :

 - donwload asyncTask : https://www.itread01.com/content/1546513758.html
 - customer progressbar : https://stackoverflow.com/questions/22177006/android-horizontal-progress-bar
