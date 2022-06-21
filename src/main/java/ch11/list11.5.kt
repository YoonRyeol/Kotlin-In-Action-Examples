package ch11.list_16

class Greeter(val greeting: String) {
    operator fun invoke(name: String){
        println("$greeting, $name!")
    }
}

data class Issue(
    val id: String, val project: String, val type: String,
    val priority: String, val description: String
)

class ImportantIssuesPredicate(val project: String) : (Issue) -> Boolean {
    override fun invoke(issue: Issue): Boolean {
        return issue.project == project && issue.isImportant()
    }

    private fun Issue.isImportant() : Boolean {
        return type == "Bug" &&
                (priority == "Major" || priority == "Critical")
    }

}

fun main(){
    val bavarianGreeter = Greeter("Servus")
    bavarianGreeter("Dmitry")
}
