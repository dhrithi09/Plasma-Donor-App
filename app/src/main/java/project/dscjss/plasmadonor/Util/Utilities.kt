package project.dscjss.plasmadonor.Util

import android.content.Context
import android.widget.Toast

public class Utilities {

    companion object {

        public fun showShortToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        public fun showLongToast(context: Context, message: String) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}