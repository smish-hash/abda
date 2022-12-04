package com.smish.abda.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : ComponentActivity() {
    private var progressDialog: AlertDialog? = null

    /*private fun getAlertDialog(
        context: Context,
        message: String?
    ): MaterialAlertDialogBuilder {
        val binding = BaseProgressAlertdialogBinding.inflate(layoutInflater)
        val builder = MaterialAlertDialogBuilder(context)
        binding.dialogText.text = message ?: "Please wait"
        builder.setView(binding.root)
        builder.setCancelable(true)
        builder.background = ColorDrawable(Color.TRANSPARENT)
        return builder
    }*/

    fun showProgressDialog(message: String?) {
        /*if (progressDialog == null) {
            progressDialog = getAlertDialog(this, message).create()
        }
        progressDialog!!.show()*/
    }

    fun closeProgressDialog() {
        if (progressDialog?.isShowing == true){
            progressDialog?.dismiss()
        }
    }

    fun showSmallToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackBar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}