package info.camposha.firebasecrudkotlin.activities

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import info.camposha.firebasecrudkotlin.R
import info.camposha.firebasecrudkotlin.data.Scientist
import info.camposha.firebasecrudkotlin.helpers.FirebaseCRUDHelper
import info.camposha.firebasecrudkotlin.helpers.Utils.databaseRefence
import info.camposha.firebasecrudkotlin.helpers.Utils.openActivity
import info.camposha.firebasecrudkotlin.helpers.Utils.receiveScientist
import info.camposha.firebasecrudkotlin.helpers.Utils.show
import info.camposha.firebasecrudkotlin.helpers.Utils.showInfoDialog
import info.camposha.firebasecrudkotlin.helpers.Utils.valOf
import info.camposha.firebasecrudkotlin.helpers.Utils.validate
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_crud.*

class CRUDActivity : AppCompatActivity() {
    //we'll have several instance fields
    private val c: Context = this@CRUDActivity
    private var receivedScientist: Scientist? = null
    private val crudHelper = FirebaseCRUDHelper()
    private val db =
        databaseRefence

    private fun insertData() {
        if (validate(nameTxt, weightTxt)) {
            val newScientist = Scientist(
                "", valOf(nameTxt),
                valOf(weightTxt)
            )
            crudHelper.insert(this, db, pb, newScientist)
        }
    }

    /**
     * Validate then update data
     */
    private fun updateData() {

        if (validate(nameTxt, weightTxt)) {

            val updatedScientist =
                Scientist(
                    receivedScientist!!.key, valOf(nameTxt),
                    valOf(weightTxt)
                )
            crudHelper.update(this, db, pb, updatedScientist)
        }
    }

    /**
     * Delete data from firebase
     */
    private fun deleteData() {
        crudHelper.delete(this, db, pb, receivedScientist!!)
    }

    /**
     * Show selected star in edittext
     */

    /**
     * Warn user if he clicks the back button
     */
    override fun onBackPressed() {
        showInfoDialog(
            this,
            "Warning",
            "Are you sure you want to exit?"
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (receivedScientist == null) {
            menuInflater.inflate(R.menu.new_item_menu, menu)
            headerTxt!!.text = "Add milk collection"
        } else {
            menuInflater.inflate(R.menu.edit_item_menu, menu)
            headerTxt!!.text = "Edit existing weights"
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.insertMenuItem -> {
                insertData()
                return true
            }
            R.id.editMenuItem -> {
                if (receivedScientist != null) {
                    updateData()
                } else {
                    show(this, "EDIT ONLY WORKS IN EDITING MODE")
                }
                return true
            }
            R.id.deleteMenuItem -> {
                if (receivedScientist != null) {
                    deleteData()
                } else {
                    show(this, "DELETE ONLY WORKS IN EDITING MODE")
                }
                return true
            }
            R.id.viewAllMenuItem -> {
                openActivity(this, ScientistsActivity::class.java)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Attach Base Context
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    /**
     * When our activity is resumed we will receive our data and set them to their editing
     * widgets.
     */
    override fun onResume() {
        super.onResume()
        val s =
            receiveScientist(intent, c)
        if (s != null) {
            receivedScientist = s
            nameTxt.setText(receivedScientist!!.name)
            weightTxt.setText(receivedScientist!!.weight)
        }
    }

    /**
     * Let's override our onCreate() method
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)
    }
}