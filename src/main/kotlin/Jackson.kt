import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.lang.IllegalArgumentException

fun main() {
    val student = Student("Kaushik", 15, 10, StudentType.REGULAR)
    val asString = ObjectMapper().writeValueAsString(student)
    println(asString)

    val parsedStudent = jacksonObjectMapper().readValue(asString, Student::class.java)
    println(parsedStudent)
}

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder("name", "age", "class", "student-type")
data class Student (
    val name: String,
    val age: Int,
    @get:JsonProperty("class")
    val standard: Int,
    @get:JsonProperty("student-type")
    val type: StudentType
)

enum class StudentType (
    val code: Int
) {
    REGULAR(0),
    PART_TIME(1);

    @JsonValue
    fun toValue(): Int {
        return code
    }

    @JsonCreator
    fun toObject(code: Int): StudentType {
        return parse(code)
    }

    companion object {
        @JvmStatic
        fun parse(code: Int): StudentType {
            values().forEach {
                if (it.code == code)
                    return it
            }

            throw IllegalArgumentException("There is no student type with code $code")
        }
    }
}