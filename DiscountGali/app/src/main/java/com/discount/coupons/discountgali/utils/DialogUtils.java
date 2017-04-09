package com.discount.coupons.discountgali.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.discount.coupons.discountgali.R;


public class DialogUtils {

    public static void showAlert(Context context, String message) {
        showAlert(context, message, null);
    }
    public static void showAlertOk(Context context, String message,
                                 final Runnable handler) {
        if (context != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name));
            dialog.setMessage(message);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (handler != null) {
                        handler.run();
                    }
                }
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    if (handler != null) {
//                        handler.run();
                    }

                }
            });
            dialog.show();
        }
    }

    public static void showAlert(Context context, String message,
                                 final Runnable handler) {
        if (context != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name));
            dialog.setMessage(message);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (handler != null) {
                        handler.run();
                    }
                }
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    if (handler != null) {
                        handler.run();
                    }

                }
            });
            dialog.show();
        }
    }

    public static void showAlert(Context context, String message, String title,
                                 final Runnable handler) {
        if (context != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(title);
            dialog.setMessage(message);
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (handler != null) {
                        handler.run();
                    }
                }
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    if (handler != null) {
                        handler.run();
                    }

                }
            });
            dialog.show();
        }
    }

    public static void showAlertWithOkAndCancel(Context context, String message,
                                                final Runnable handlerOk, final Runnable handlerCanel) {
        if (context != null) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name));
            dialog.setCancelable(false);
            dialog.setMessage(message);
            dialog.setPositiveButton(context.getString(R.string.dialog_ok), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (handlerOk != null) {
                        handlerOk.run();
                    }
                }
            });
            dialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (handlerCanel != null) {
                        handlerCanel.run();
                    }
                }
            });

            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
//                if (handler != null) {
//                    handler.run();
//                }

                }
            });
            dialog.show();
        }
    }

    // @Atul
//    public static void showCustomAlert(final Context context, String title, String message
//            , String okButtonLabel, String cancelButtonLabel
//            , final Runnable okRunnable, final Runnable cancelRunnable, final boolean isCancelable) {
//        if (context != null) {
//            final Dialog dialog = new Dialog(context);
//
//            try {
//                LayoutInflater li = (LayoutInflater) context.
//                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = li.inflate(R.layout.dialog_delete_roast, null, false);
//                TextView txTitle = (TextView) view.findViewById(R.id.txTitle);
//                txTitle.setText(title);
//
//                TextView txMessage = (TextView) view.findViewById(R.id.txMessage);
//                txMessage.setText(message);
//                Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
//                btnCancel.setText(cancelButtonLabel);
//                ;
//                Button btnYes = (Button) view.findViewById(R.id.btnYes);
//                btnYes.setText(okButtonLabel);
//
//                btnCancel.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//
//                        if (cancelRunnable != null) {
//                            cancelRunnable.run();
//                        }
//                    }
//                });
//                btnYes.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (isCancelable)
//                            dialog.dismiss();
//
//                        if (okRunnable != null) {
//                            okRunnable.run();
//                        }
//                    }
//                });
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(view);
//                if (isCancelable)
//                    dialog.setCancelable(true);
//                else
//                    dialog.setCancelable(false);
//                dialog.show();
//
//            } catch (Exception e) {
//                e.getMessage();
//            }
//        }
//    }

//    public static void showImageSelectDialog(final Context context, String message, final Runnable galleryRunnable, final Runnable cameraRunnable) {
//        if (context != null) {
//            final Dialog dialog = new Dialog(context);
//
//            try {
//                LayoutInflater li = (LayoutInflater) context.
//                        getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View view = li.inflate(R.layout.dialog_roast_image_select, null, false);
//
//                Button btnGallery = (Button) view.findViewById(R.id.btnGallary);
//                Button btnCamera = (Button) view.findViewById(R.id.btnCamera);
//
//                ImageView imgCross = (ImageView) view.findViewById(R.id.imgCross);
//
//                btnGallery.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        if (galleryRunnable != null) {
//                            galleryRunnable.run();
//                        }
//                    }
//                });
//                btnCamera.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                        if (cameraRunnable != null) {
//                            cameraRunnable.run();
//                        }
//                    }
//                });
//
//                imgCross.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(view);
//                dialog.setCancelable(false);
//                dialog.show();
//
//            } catch (Exception e) {
//                e.getMessage();
//            }
//        }
//
//    }

    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static ProgressDialog getProgressDialog(Context context) {
        if (context != null) {
            return getProgressDialog(context, context.getString(R.string.progress_dialog));
        }
        return null;
    }

    public static ProgressDialog getProgressDialog(Context context, String message) {
        if (context != null) {
            ProgressDialog dialog = new ProgressDialog(context, android.R.style.Theme_DeviceDefault_Dialog);
            dialog.setMessage(message == null ? context.getString(R.string.progress_dialog) : message);
            dialog.setCancelable(false);
            return dialog;
        }
        return null;
    }

    public static void hideProgressDialog(ProgressDialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}