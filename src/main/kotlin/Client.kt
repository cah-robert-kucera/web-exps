import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.html.BODY
import kotlinx.html.InputType
import kotlinx.html.TagConsumer
import kotlinx.html.body
import kotlinx.html.br
import kotlinx.html.classes
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
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event

val client = HttpClient(Js)

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
                br { }
                +"Please fill out this quick form so we can set up your account."
            }
            buildSignupForm()

            div {
                id = "submit-status"
            }
        }
    }
}

private fun BODY.buildSignupForm() {
    div {
        id = "signup-wrapper"
        form {
            id = "signup-form"
            div("form-line") {
                label { +"Email" }
                input(InputType.email) {
                    id = "email"
                    required = true
                    value = "robert.kucera@cardinalhealth.com"
                }
            }

            div("form-line") {
                label { +"Member Id" }
                input(InputType.text) {
                    id = "member-id"
                    required = true
                    value = "123"
                }
            }

            div("form-line") {
                label { +"First Name" }
                input(InputType.text) {
                    id = "first-name"
                    required = true
                    value = "Bob"
                }
            }

            div("form-line") {
                label { +"Last Name" }
                input(InputType.text) {
                    id = "last-name"
                    required = true
                    value = "Baggins"
                }
            }

            submitInput {
                classes = setOf("submit")
            }
            onSubmitFunction = ::submitSignup
        }
    }
}

private fun submitSignup(event: Event) {
    event.preventDefault()

    val submitStatus = document.getElementById("submit-status")!!
    val form = document.getElementById("signup-form") as HTMLFormElement
    val firstName = (form["first-name"] as HTMLInputElement).value
    val lastName = (form["last-name"] as HTMLInputElement).value
    val memberId = (form["member-id"] as HTMLInputElement).value
    val email = (form["email"] as HTMLInputElement).value

    println(listOf(firstName, lastName, memberId, email).joinToString())

    submitStatus.innerHTML = ""
    GlobalScope.launch {
        val response = client.post("http://localhost:8080/startEnrollment") {
            val patient = Json.encodeToString(PatientEnrollmentAttempt(memberId, email, firstName, lastName))
            println("patient: $patient")
            setBody(patient)
        }
        if (response.status == HttpStatusCode.OK) {
            println("Enrollment started")
            document.getElementById("signup-wrapper")?.innerHTML = ""
            submitStatus.append {
                div("submit-success") {
                    +"Enrollment started. Enter the code from your email!"
                }
                buildEmailForm()
            }
        } else {
            println("Enrollment Failed")
            submitStatus.append {
                div("submit-failed") {
                    +"Enrollment Failed. Please try again"
                }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.buildEmailForm() {
    div {
        id = "email-form-wrapper"
        br { }
        form {
            id = "email-code-form"
            div("form-line") {
                label { +"Code" }
                input(InputType.text) {
                    id = "code"
                    required = true
                    value = ""
                }
            }
            submitInput {
                id = "submit-email-code"
                classes = setOf("submit")
            }
            onSubmitFunction = ::submitVerification
        }
    }
}

private fun submitVerification(event: Event) {
    event.preventDefault()
    val submitStatus = document.getElementById("submit-status")!!
    val form = document.getElementById("email-code-form") as HTMLFormElement
    val code = (form["code"] as HTMLInputElement).value
    GlobalScope.launch {
        val response = client.post("http://localhost:8080/enroll/$code")
        if (response.status == HttpStatusCode.OK) {
            println("Enrollment Complete")
            document.getElementById("email-form-wrapper")?.innerHTML = ""
            document.getElementById("submit-status")?.innerHTML = ""
            submitStatus.append {
                div("submit-success") {
                    +"Enrollment Complete!"
                }

            }
        } else {
            println("Enrollment Failed")
            submitStatus.append {
                div("submit-failed") {
                    +"Enrollment Failed. Please try again."
                }
            }
        }
    }
}

@Serializable
data class PatientEnrollmentAttempt(
    val memberId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
)
