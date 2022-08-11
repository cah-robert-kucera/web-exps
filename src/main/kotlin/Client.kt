import kotlinx.html.div
import kotlinx.html.dom.append
import org.w3c.dom.Node
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.input
import kotlinx.html.js.onSubmitFunction
import kotlinx.html.label
import kotlinx.html.onClick
import kotlinx.html.submitInput
import kotlinx.html.title

fun main() {

    window.onload = {
        println("loaded")
        document.head?.title = "Healthprize Signup"
        document.body?.sayHello()
    }
}

fun Node.sayHello() {
    append {
        body {
            h1 {
                +"Healthprize Signup 2"
            }
            div {
                form {
                    label { +"Email" }
                    input(InputType.email, name = "Email")

                    label { +"Member Id" }
                    input(InputType.text, name = "MemberId")

                    label { +"First Name" }
                    input(InputType.text, name = "FirstName")

                    label { +"Last Name" }
                    input(InputType.text, name = "LastName")

                    submitInput {
                    }
                    onSubmitFunction = { event ->
                        event.preventDefault()
                        println(event)
                    }
                }
            }
        }
    }
}
