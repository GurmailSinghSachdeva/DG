package com.gurmail.singh.discountgali.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.gurmail.singh.discountgali.BuildConfig;
import com.gurmail.singh.discountgali.R;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.net.URL;

/**
 * Image Loader Utils class.
 */
public class ImageLoaderUtils {

    private static DisplayImageOptions deafultDisplayImageOptions = null;
    private static DisplayImageOptions rectDisplayImageOptions = null;

    private static DisplayImageOptions defaultNew = null;
    /* ************** init for UIL version >= 1.9.3 ************* */
    public static void initImageLoader(Context context) {
        try {
            // Default Display Image Options for Image Loader
            deafultDisplayImageOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                            //.displayer(new FadeInBitmapDisplayer(300))
                            //.resetViewBeforeLoading(true)
                            //.cacheInMemory(true)
//                    .showImageOnLoading(R.drawable.file_image)
//                    .showImageOnFail(R.drawable.file_image)
//                    .showImageForEmptyUri(R.drawable.file_image)
                    .showImageOnLoading(R.drawable.ic_image_default)
                    .showImageOnFail(R.drawable.ic_image_default)
                    .showImageForEmptyUri(R.drawable.ic_image_default)
                    .build();

            defaultNew = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .cacheInMemory(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                    //.displayer(new FadeInBitmapDisplayer(300))
                    //.resetViewBeforeLoading(true)
                    //.cacheInMemory(true)
                    .showImageOnLoading(R.drawable.ic_image_default)
                    .showImageOnFail(R.drawable.ic_image_default)
                    .showImageForEmptyUri(R.drawable.ic_image_default)
                    .build();

            com.nostra13.universalimageloader.utils.L.writeLogs(BuildConfig.DEBUG);


            rectDisplayImageOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .considerExifParams(true)
                            //.displayer(new FadeInBitmapDisplayer(300))
                            //.resetViewBeforeLoading(true)
                            //.cacheInMemory(true)
    //                .showImageOnLoading(R.mipmap.profilepic)
//                    .showImageOnFail(R.mipmap.profilepic)
//                    .showImageForEmptyUri(R.mipmap.profilepic)
                    .build();


            // Max. image size
            Resources resources = context.getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels / 2; // half of screen height
            //L.d("CacheDirectory= " + StorageUtils.getCacheDirectory(context) + " MAX W= " + width + " MAX H= " + height);

            // This configuration tuning is custom. You can tune every option, you
            // may tune some of them,
            // or you can create default configuration by
            // ImageLoaderConfiguration.createDefault(this);
            // method.
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .threadPoolSize(3)
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .denyCacheImageMultipleSizesInMemory()
                    .diskCache(getDiscCache(context))
                    .defaultDisplayImageOptions(deafultDisplayImageOptions)
                    .memoryCacheExtraOptions(width, height)
                    //.memoryCache(new LruMemoryCache(500))
                    .memoryCacheSizePercentage(10)
                    .diskCacheExtraOptions(width, height, null)
                            //.memoryCache(DefaultConfigurationFactory.createMemoryCache(context, 0)) // use default memory cache
                            //.writeDebugLogs() // Remove for release app
                    .build();


            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DiskCache getDiscCache(Context context) {
        if (context != null) {
            try {
                return new LruDiskCache(StorageUtils.getCacheDirectory(context), null, new Md5FileNameGenerator(
                ), 200 * 1024 * 1024
                        , 1000);
            } catch (Exception e) {
                try {
                    return new UnlimitedDiskCache(StorageUtils.getCacheDirectory(context));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }

    public static DisplayImageOptions getDeafultDisplayImageOptions() {
        return deafultDisplayImageOptions;
    }
    public static void loadImageOnly(final String url, ImageLoadingListener imageLoadingListener) {
        try {


            ImageLoader.getInstance().loadImage(url, imageLoadingListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void loadImageSelective(final String url, final ImageView view) {
//        try {
//            /* 1 */
//            if (!isValidURL(url))
//                view.setImageResource(R.drawable.chat121_ic_bottom_smiley);
//
//            Object oldTag = view.getTag();
//
//            if (oldTag != null && oldTag.equals(url)) {
//                return;
//            }
//            view.setTag(url);
//
//
//            //ImageAware imageAware = new ImageViewAware(view, false);
//            //ImageLoader.getInstance().displayImage(url, imageAware, deafultDisplayImageOptions);
//            ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions, );
//
//            /* 2 */
////            view.post(new Runnable() {
////                @Override
////                public void run() {
////                    ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions);
////                }
////            });
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
//    }


    public static void loadImage(final String url, final ImageView view) {
        try {
            /* 1 */
            if (!isValidURL(url))
                view.setImageResource(R.drawable.ic_image_default);

            Object oldTag = view.getTag();

            if (oldTag != null && oldTag.equals(url)) {
                return;
            }
            view.setTag(url);


            //ImageAware imageAware = new ImageViewAware(view, false);
            //ImageLoader.getInstance().displayImage(url, imageAware, deafultDisplayImageOptions);
            ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions);

            /* 2 */
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//                    ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions);
//                }
//            });
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    public static void loadImage(final String url, final ImageView view, final ImageView ImageViewDefault) {
        try {
            /* 1 */
            if (!isValidURL(url))
            {
                view.setVisibility(View.GONE);
                ImageViewDefault.setVisibility(View.VISIBLE);
                ImageViewDefault.setImageResource(R.drawable.ic_image_default);
            }


            Object oldTag = view.getTag();

            if (oldTag != null && oldTag.equals(url)) {
                return;
            }
            view.setTag(url);


            //ImageAware imageAware = new ImageViewAware(view, false);
            //ImageLoader.getInstance().displayImage(url, imageAware, deafultDisplayImageOptions);
            ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions);

            /* 2 */
//            view.post(new Runnable() {
//                @Override
//                public void run() {
//                    ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions);
//                }
//            });
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    public static void loadRectImage(String url, ImageView view) {
        try {
            ImageLoader.getInstance().displayImage(url, view, rectDisplayImageOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadImage(String url, ImageView view, ImageLoadingListener imageLoadingListener) {
        try {
            ImageLoader.getInstance().displayImage(url, view, deafultDisplayImageOptions, imageLoadingListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

      /*  try {
            ImageLoader.getInstance().displayImage(url, view, defaultNew, imageLoadingListener);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }



    public static void clearAllImages() {
        try {
            ImageLoader.getInstance().clearMemoryCache();
            ImageLoader.getInstance().clearDiskCache();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidURL(String url) {
        if (url == null || url.isEmpty())
            return false;

        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            if (BuildConfig.DEBUG)
                Log.e("ImageLoaderUtils", "Invalid image url: " + url + "cause: " + e);
        }
        return false;
    }
}
