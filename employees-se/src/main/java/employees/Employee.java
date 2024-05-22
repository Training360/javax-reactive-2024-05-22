package employees;

public record Employee(String name, int yearOfBirth) {

    int getAgeAt(int year) {
        if (year < yearOfBirth) {
            throw new IllegalArgumentException("Year is too early: %d".formatted(year));
        }
        return year - yearOfBirth;
    }
}
