import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.b
import kotlinx.html.body
import kotlinx.html.br
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
                id = "instructions"
                +"Welcome to Healthprize with Outcomes!"
                br {  }
                +"Please fill out this quick form so we can set up your account."
            }
            div {
                form {
                    id = "signup-form"
                    div("form-line") {
                        label { +"Email" }
                        input(InputType.email) {
                            id = "email"
                        }
                    }

                    div("form-line") {
                        label { +"Member Id" }
                        input(InputType.text) {
                            id = "member-id"
                        }
                    }

                    div("form-line") {
                        label { +"First Name" }
                        input(InputType.text) {
                            id = "first-name"
                        }
                    }

                    div("form-line") {
                        label { +"Last Name" }
                        input(InputType.text) {
                            id = "last-name"
                        }
                    }

                    submitInput {
                        id = "submit-signup"
                    }
                    onSubmitFunction = { event ->
                        event.preventDefault()

                        val form = document.getElementById("signup-form") as HTMLFormElement
                        val firstName = (form["first-name"] as HTMLInputElement).value
                        val lastName = (form["last-name"] as HTMLInputElement).value
                        val memberId = (form["member-id"] as HTMLInputElement).value
                        val email = (form["email"] as HTMLInputElement).value

                        println(listOf(firstName, lastName, memberId, email).joinToString())
                    }
                }
            }
        }
    }
}
