package com.tis.merchant.app.fcm;


/**
 * Created by Nanthakorn on 8/21/2017
 */

//public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
//    private static final String TAG = "dam";
//
//    /**
//     * Called if InstanceID token is updated. This may occur if the security of
//     * the previous token had been compromised. Note that this is called when the InstanceID token
//     * is initially generated so this is where you would retrieve the token.
//     */
//    @Override
//    public void onTokenRefresh()
//    {
//        super.onTokenRefresh();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
//        Singleton.getInstance().getUserInformation().setPushToken(refreshedToken);
//        Log.d(TAG, "Refreshed token: " + refreshedToken);
//    }
//
//    /**
//     * Persist token to third-party servers.
//     *
//     * Modify this method to associate the user's FCM InstanceID token with any server-side account
//     * maintained by your application.
//     *
//     * @param token The new token.
//     */
//}
