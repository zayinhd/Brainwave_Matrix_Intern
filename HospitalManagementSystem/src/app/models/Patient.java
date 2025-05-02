package app.models;

import javafx.beans.property.*;

public class Patient {

    private final IntegerProperty id;
    private final StringProperty name;
    private final IntegerProperty age;
    private final StringProperty gender;

    public Patient(int id, String name, int age, String gender) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.gender = new SimpleStringProperty(gender);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }
}
