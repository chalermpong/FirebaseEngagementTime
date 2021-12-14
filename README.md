# FirebaseEngagementTime
MCVE for Firebase Analytics issue [Engagement Time is logged incorrectly when disable screen view tracking](https://github.com/firebase/firebase-android-sdk/issues/3240)

# Instruction
1. Setup a Firebase project and create an Android app to get your own google-services.json file
2. Run the app. This app already disable screen view tracking. You should be able to see the delayed `Recording user engagement, ms: xxx` in Logcat.
```
2021-12-14 13:42:56.270 16014-16064/my.app.package V/FA: Activity paused, time: 246391932
2021-12-14 13:42:58.272 16014-16064/my.app.package D/FA: Application going to the background
2021-12-14 13:42:58.283 16014-16064/my.app.package V/FA: Recording user engagement, ms: 34745
```
3. Open app again. This time quickly kill the app before `Recording user engagement` happens.
4. Use [this page](https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages/send?apix_params=%7B%22parent%22%3A%22projects%2Fyour_project_id%22%2C%22resource%22%3A%7B%22validateOnly%22%3Afalse%2C%22message%22%3A%7B%22token%22%3A%22your_fcm_token%22%2C%22name%22%3A%22test%22%2C%22data%22%3A%7B%22test%22%3A%22test%22%7D%7D%7D%7D) to send data message to your device 
5. After app received the data message, open the app again and then exit the app to see incorrect `Recording user engagement` in Logcat
