# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in G:\anzhuangbao\eclispe_android\eclipse_en_32_4.4\sdk/tools/proguard/proguard-android.txt
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
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keepattributes Exceptions,InnerClasses,Signature
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable


#okhttputils
-dontwarn com.zhy.http.**
-keep class com.zhy.http.**{*;}
#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
#okio
-dontwarn okio.**
-keep class okio.**{*;}


#utovr
-dontwarn android.support.v4.**
-dontwarn com.google.**
-dontwarn android.media.**
-dontwarn com.utovr.**
-keepattributes InnerClasses, Signature, *Annotation*
-keep class android.support.v4.** {*;}
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep class android.media.** {*;}
-keep class com.utovr.** {*;}
-keep class com.google.** {*;}

#system
-keep class **.R$* {*;}
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.view.View {*;}
-keep class * extends java.lang.annotation.Annotation { *; }
-keepattributes SourceFile, LineNumberTable

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  	public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class * {
   public <init>(org.json.JSONObject);
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#realm
-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**

# 保留support下的所有类及其内部类
-keep class android.support.** {*;}
# Retrolambda
-dontwarn java.lang.invoke.*



-keep  class vr.xinjing.com.vrmc.activity.welcomeActivity{*;}
-keep  class vr.xinjing.com.vrmc.AuthorizationAcivity{*;}
-keep  class vr.xinjing.com.vrmc.PlayerActivity{*;}
-keep  class vr.xinjing.com.vrmc.Utils{*;}
-keep  class vr.xinjing.com.vrmc.VideoController{*;}
-keep  class vr.xinjing.com.vrmc.PlayerFragment{*;}
-keep  class vr.xinjing.com.vrmc.PlayerFragmentActivity{*;}
