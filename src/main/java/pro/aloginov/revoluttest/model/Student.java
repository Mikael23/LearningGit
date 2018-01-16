package pro.aloginov.revoluttest.model;


import javax.inject.Singleton;


public class Student {
  public int universityId;
    public String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (dateOfBirth != student.dateOfBirth) return false;
        if (name != null ? !name.equals(student.name) : student.name != null) return false;
        return city != null ? city.equals(student.city) : student.city == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + dateOfBirth;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
      public int id;
    public int dateOfBirth;
    public String city;

    public Student(int id,String name, int dateOfBirth, String city,int universityId) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.universityId = universityId;
        this.id = id;
    }
}
