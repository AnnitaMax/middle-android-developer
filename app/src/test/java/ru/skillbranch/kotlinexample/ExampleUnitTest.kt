package ru.skillbranch.kotlinexample

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun register_user() {
        val holder:UserHolder = UserHolder
        holder.registerUser("John Doe", "John_Doe@unknown.com", "testPass")
        val expectedInfo:String = """
            firstName: John
            lastName: Doe
            login: john_doe@unknown.com
            fullName: John Doe
            initials: J D
            email: John_Doe@unknown.com
            phone: null
            meta: {auth=password}
        """.trimIndent()

        val failResult: String? = holder.loginUser("John_Doe@unknown.com", "testPass")
        val successResult: String? = holder.loginUser("john_doe@unknown.com", "testPass")

        Assert.assertEquals(null, failResult)
        Assert.assertEquals(expectedInfo, successResult)

        holder.registerUser("John May", "John_Doe@unknown.com", "testPass2")

    }

}