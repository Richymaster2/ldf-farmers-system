package info.camposha.firebasecrudkotlin.helpers


import android.content.ContentValues.TAG
import android.os.Handler
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import info.camposha.firebasecrudkotlin.activities.ScientistsActivity
import info.camposha.firebasecrudkotlin.data.MyAdapter
import info.camposha.firebasecrudkotlin.data.Scientist
import info.camposha.firebasecrudkotlin.helpers.Utils.DataCache
import info.camposha.firebasecrudkotlin.helpers.Utils.hideProgressBar
import info.camposha.firebasecrudkotlin.helpers.Utils.openActivity
import info.camposha.firebasecrudkotlin.helpers.Utils.show
import info.camposha.firebasecrudkotlin.helpers.Utils.showInfoDialog
import info.camposha.firebasecrudkotlin.helpers.Utils.showProgressBar


class FirebaseCRUDHelper {
    /**
     * This method will allow us post into firebase realtime database
     * @param a
     * @param pb
     * @param scientist
     */
    fun insert(a: AppCompatActivity?, mdb: FirebaseFirestore, pb: ProgressBar?, scientist: Scientist?) { //check if they have passed us a valid scientist. If so then return false.
        if (scientist == null) {
            showInfoDialog(a!!, "VALIDATION FAILED", "Scientist is null")
            return
        } else { //otherwise try to push data to firebase database.
            showProgressBar(pb!!)
            //push data to FirebaseDatabase. Table or Child called Scientist will be created
            mdb.collection("milkCollections").add(scientist)
                .addOnCompleteListener { task ->
                    hideProgressBar(pb)
                    if (task.isSuccessful) {
                        openActivity(a!!, ScientistsActivity::class.java)
                        show(a, "Congrats! INSERT SUCCESSFUL")
                    } else {
                        showInfoDialog(a!!, "UNSUCCESSFUL", task.exception!!.message)
                    }
                }
        }
    }

    /**
     * This method will allow us retrieve from firebase realtime database
     * @param a
     * @param db
     * @param pb
     * @param rv
     * @param adapter
     */
    fun select( a: AppCompatActivity?,mdb: FirebaseFirestore, pb: ProgressBar?, rv: RecyclerView, adapter: MyAdapter) {
        showProgressBar(pb!!)
        mdb.collection("milkCollections")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
            if (task.isSuccessful) {
                DataCache.clear()
                for (document in task.result!!) {
                    val scientist: Scientist = document.toObject(Scientist::class.java)!!
                    //scientist.key = document.key
                    DataCache.add(scientist)
                }
                adapter.notifyDataSetChanged()
                Handler().post {
                    hideProgressBar(pb)
                    rv.smoothScrollToPosition(DataCache.size)
                 }
                }
             })

            }



    /**
     * This method will allow us update existing data in firestore
     * @param a
     * @param pb
     * @param updatedScientist
     */
    fun update(a: AppCompatActivity?,mdb: FirebaseFirestore, pb: ProgressBar?, updatedScientist: Scientist?) {
        if (updatedScientist == null) {
            showInfoDialog(a!!, "VALIDATION FAILED", "Farmer is null")
            return
        }
        showProgressBar(pb!!)
        //val db = FirebaseFirestore.getInstance()
        mdb.collection("milkCollections").document(updatedScientist.key!!).set(updatedScientist)
            .addOnCompleteListener { task ->
                hideProgressBar(pb)
                if (task.isSuccessful) {
                    show(a, updatedScientist.name.toString() + " Update Successful.")
                    openActivity(a!!, ScientistsActivity::class.java)
                } else {
                    showInfoDialog(a!!, "UNSUCCESSFUL", task.exception!!.message)
                }
            }
    }

    /**
     * This method will allow us to delete from firestore
     * @param a
     * @param pb
     * @param selectedScientist
     */
    fun delete(a: AppCompatActivity?,mdb: FirebaseFirestore, pb: ProgressBar?, selectedScientist: Scientist) {
        showProgressBar(pb!!)
        val selectedScientistKey: String = selectedScientist.key!!
        Log.d(TAG, "Document:.. ${selectedScientistKey}")

        //val db = FirebaseFirestore.getInstance()
        mdb.collection("milkCollections").document(selectedScientistKey).delete()
            .addOnCompleteListener { task ->
                hideProgressBar(pb)
                if (task.isSuccessful) {
                    show(a, selectedScientist.name.toString() + " Successfully Deleted.")
                    openActivity(a!!, ScientistsActivity::class.java)
                } else {
                    showInfoDialog(a!!, "UNSUCCESSFUL", task.exception!!.message)
                }
            }
    }
}