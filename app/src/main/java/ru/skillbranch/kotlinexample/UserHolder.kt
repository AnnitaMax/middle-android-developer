package ru.skillbranch.kotlinexample

import androidx.annotation.VisibleForTesting
import java.lang.IllegalArgumentException

object UserHolder {

    private val map = mutableMapOf<String, User>()

    fun registerUser(
        fullName: String,
        email: String,
        password: String
    ) : User {

        if (!map.containsKey(email.toLowerCase()))
        {
            return User.makeUser(fullName, email = email, password = password)
                .also { user -> map[user.login] = user }
        } else {
            throw IllegalArgumentException("A user with this email already exists")
        }
    }

    fun registerUserByPhone(
        fullName: String,
        rawPhone: String
    ) : User {

        if(!rawPhone.replace(" ","") .matches("""^(\+7)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}${'$'}""".toRegex())){
            throw IllegalArgumentException("Enter a valid phone number starting with a + and containing 11 digits")
        }

        if (!map.containsKey(rawPhone.replace("""[^+\d]""".toRegex(), "")))
        {
            return User.makeUser(fullName, phone = rawPhone)
                .also { user -> map[user.login] = user }
        } else {
            throw IllegalArgumentException("A user with this phone already exists")
        }
    }


    fun loginUser(login: String, password: String): String? {

        var userLogin: String?
        userLogin = login.trim()
        if(userLogin.replace(" ","") .matches("""^(\+7)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}${'$'}""".toRegex())){
            userLogin = userLogin.replace("""[^+\d]""".toRegex(), "")
        }

        return map[userLogin]?.run {
            if (checkPassword(password)) this.userInfo
            else null
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    fun clearHolder(){
        map.clear()
    }

    fun requestAccessCode(login: String) {

        var userLogin: String?
        userLogin = login.trim()
        if(userLogin.replace(" ","") .matches("""^(\+7)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}${'$'}""".toRegex())){
            userLogin = userLogin.replace("""[^+\d]""".toRegex(), "")
        }

        map[userLogin]?.setAccessCodeAndPassword()
    }



}