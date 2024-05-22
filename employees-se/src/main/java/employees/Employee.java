package employees;

import java.util.ArrayList;
import java.util.List;

public record Employee(String name, int yearOfBirth, List<String> skills) {

    int getAgeAt(int year) {
        if (year < yearOfBirth) {
            throw new IllegalArgumentException("Year is too early: %d".formatted(year));
        }
        return year - yearOfBirth;
    }

    public Employee(String name, int yearOfBirth) {
        this(name, yearOfBirth, new ArrayList<>());
    }
}
