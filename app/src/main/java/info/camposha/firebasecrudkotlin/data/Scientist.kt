package info.camposha.firebasecrudkotlin.data

import com.google.firebase.firestore.Exclude
import java.io.Serializable

class Scientist : Serializable {
    var id: String? = ""
    var name: String? = ""
    var weight: String? = ""
    @get:Exclude
    @set:Exclude
    var key: String? = ""

    constructor() { //empty constructor needed
    }

    constructor(
        key: String?,
        name: String,
        weight: String?
    ) {
        var name = name
        if (name.trim { it <= ' ' } == "") {
            name = "Farmer NoName"
        }
        this.key = key
        this.name = name
        this.weight = weight
    }

    override fun toString(): String {
        return name!!
    }

}