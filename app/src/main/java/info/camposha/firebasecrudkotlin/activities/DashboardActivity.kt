package info.camposha.firebasecrudkotlin.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.camposha.firebasecrudkotlin.R
import info.camposha.firebasecrudkotlin.helpers.Utils.openActivity
import info.camposha.firebasecrudkotlin.helpers.Utils.showInfoDialog
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {
    /**
     * Let's initialize our cards  and listen to their click events
     */
    private fun initializeWidgets() {
        viewScientistsCard.setOnClickListener {
            openActivity(
                this@DashboardActivity,
                ScientistsActivity::class.java
            )
        }
        addScientistCard.setOnClickListener {
            openActivity(
                this@DashboardActivity,
                CRUDActivity::class.java
            )
        }
        third.setOnClickListener {
            showInfoDialog(
                this@DashboardActivity, "YEEES",
                "Hey You can Display another page when this is clicked"
            )
        }
        closeCard.setOnClickListener { finish() }
    }

    /**
     * Let's override the attachBaseContext() method so that custom fonts can
     * be used here as well
     */
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    /**
     * When the back button is pressed finish this activity
     */
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }

    /**
     * Let's override the onCreate() and call our initializeWidgets()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initializeWidgets()
    }
}