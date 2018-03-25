# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Android-SDK\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 5
-dontpreverify
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-verbose

#keep all classes that might be used in XML layouts
-keep public class * extends android.view.View
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference

-keepattributes InnerClasses,SourceFile,LineNumberTable,Signature,*Annotation*

-dontwarn org.apache.**
-dontwarn org.codehaus.**
-dontwarn org.cyberneko.**
-dontwarn org.fest.**
-dontwarn edu.emory.**
-dontwarn android.content.**
-dontwarn org.robolectric.**
-dontwarn org.springframework.**
-dontwarn javax.naming.**
-dontwarn com.squareup.**
-dontwarn net.sourceforge.**
-dontwarn com.google.android.gms.**

-keep class android.support.v4.** { *; }
-keep class org.bouncycastle.** { *; }
-keep class com.firebase.** { *; }

-dontwarn android.support.v7.**
-keep class android.support.v7.** { *; }
-keep interface android.support.v7.** { *; }

# When layoutManager xml attribute is used, RecyclerView inflates
#LayoutManagers' constructors using reflection.
-keep public class * extends android.support.v7.widget.RecyclerView$LayoutManager {
    public <init>(...);
}

-keepclasseswithmembernames class * {
    native <methods>;
}

#Gson
-keepattributes Signature
# Gson specific classes
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class mypersonalclass.data.model.** { *; }

#-assumenosideeffects class android.util.Log {
#    public static *** d(...);
#    public static *** v(...);
#    public static *** i(...);
#    public static *** w(...);
#}
#
#-assumenosideeffects class com.promptnow.utillity.UtilLog {
#    public static *** d(...);
#    public static *** v(...);
#    public static *** i(...);
#    public static *** w(...);
#    public static *** showDebugDlg(...);
#    public static *** logModel(...);
#}

-assumenosideeffects class com.promptnow.application.PromptNowApplication {
    private *** setDeveloperMode(...);
}


-keepclassmembers public class * extends com.promptnow.susanoo.model.CommonRequestModel {
  void set*(***);
  *** get*();
}
-keepclassmembers public class * extends com.promptnow.susanoo.model.CommonResponseModel {
  void set*(***);
  *** get*();
}

#Zxing
-keep class com.google.zxing.** {*;}


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# Parceler library
-keep interface org.parceler.Parcel
-keep @org.parceler.Parcel class * { *; }
-keep class **$$Parcelable { *; }


# Calligraphy
-keep class uk.co.chrisjenx.calligraphy.* { *; }
-keep class uk.co.chrisjenx.calligraphy.*$* { *; }
