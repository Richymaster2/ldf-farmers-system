package info.camposha.firebasecrudkotlin.helpers

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import info.camposha.firebasecrudkotlin.data.Scientist
import info.camposha.firebasecrudkotlin.R
import info.camposha.firebasecrudkotlin.activities.DashboardActivity
import java.util.*

object Utils {
    const val DATE_FORMAT = "yyyy-MM-dd"
    var DataCache: ArrayList<Scientist> = ArrayList()
    var searchString = ""
    /**
     * This method allows you easily show a toast message from any activity
     * @param c
     * @param message
     */
    @JvmStatic
    fun show(c: Context?, message: String?) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * You can pass an arbitrary number of edittexts into this method, then
     * we pick the first three and validate them
     * @param editTexts
     * @return
     */
    fun validate(vararg editTexts: EditText): Boolean {
        val nameTxt = editTexts[0]
        val weightTxt = editTexts[1]
        if (nameTxt.text == null || nameTxt.text.toString().isEmpty()) {
            nameTxt.error = "Name is Required Please!"
            return false
        }
        if (weightTxt.text == null || weightTxt.text.toString().isEmpty()) {
            weightTxt.error = "Description is Required Please!"
            return false
        }
        return true
    }

    /**
     * This method will allow easily open any activity
     * @param c
     * @param clazz
     */
    @JvmStatic
    fun openActivity(c: Context, clazz: Class<*>?) {
        val intent = Intent(c, clazz)
        c.startActivity(intent)
    }

    /**
     * This method will allow us show an Info dialog anywhere in our app.
     */
    @JvmStatic
    fun showInfoDialog(activity: AppCompatActivity, title: String?, message: String?) {
        LovelyStandardDialog(activity, LovelyStandardDialog.ButtonLayout.HORIZONTAL)
            .setTopColorRes(R.color.indigo)
            .setButtonsColorRes(R.color.darkDeepOrange)
            .setIcon(R.drawable.m_info)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { }
            .setNeutralButton("Home") {
                openActivity(activity, DashboardActivity::class.java)
            }
            .setNegativeButton("Back") { activity.finish() }
            .show()
    }


    /**
     * This method will allow us send a serialized scientist objec  to a specified
     * activity
     */
    fun sendScientistToActivity(c: Context, scientist: Scientist?, clazz: Class<*>?) {
        val i = Intent(c, clazz)
        i.putExtra("SCIENTIST_KEY", scientist)
        c.startActivity(i)
    }

    /**
     * This method will allow us receive a serialized scientist, deserialize it and return it,.
     */
    fun receiveScientist(intent: Intent, c: Context?): Scientist? {
        try {
            return intent.getSerializableExtra("SCIENTIST_KEY") as Scientist
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    @JvmStatic
    fun showProgressBar(pb: ProgressBar) {
        pb.visibility = View.VISIBLE
    }

    @JvmStatic
    fun hideProgressBar(pb: ProgressBar) {
        pb.visibility = View.GONE
    }

    @JvmStatic
    fun valOf(editText: EditText): String{
        if (editText.text==null){
            return ""
        }
        return editText.text.toString()
    }
    val databaseRefence: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()
}