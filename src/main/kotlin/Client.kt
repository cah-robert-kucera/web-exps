import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.label
import kotlinx.html.submitInput
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.Node
import org.w3c.dom.get

fun main() {

    window.onload = {
        document.title = "Healthprize Signup"
        document.body?.sayHello()
    }
}

fun Node.sayHello() {
    append {
        body {
            h1 {
                +"Healthprize Signup"
            }
            div {
                form {
                    id = "signupForm"
                    label { +"Email" }
                    input(InputType.email) {
                        id = "Email"
                    }

                    label { +"Member Id" }
                    input(InputType.text) {
                        id = "MemberId"
                    }

                    label { +"First Name" }
                    input(InputType.text) {
                        id = "FirstName"
                    }

                    label { +"Last Name" }
                    input(InputType.text) {
                        id = "LastName"
                    }

                    submitInput { }
                    onSubmitFunction = { event ->
                        event.preventDefault()

                        val form = document.getElementById("signupForm") as HTMLFormElement
                        val firstName = (form["FirstName"] as HTMLInputElement).value
                        val lastName = (form["LastName"] as HTMLInputElement).value
                        val memberId = (form["MemberId"] as HTMLInputElement).value
                        val email = (form["Email"] as HTMLInputElement).value

                        println(listOf(firstName,lastName,memberId,email).joinToString())
                    }
                }
            }
        }
    }
}
