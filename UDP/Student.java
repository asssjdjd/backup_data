package UDP;

import java.io.Serializable;

public class Student implements Serializable{

    static final long serialVersionUID = 20171107L;
    private String id;
    private String code;
    private String name;
    private String email;

    public Student(String code) {
        this.code = code;
    }

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public Student(){};

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Id= " + id + " Code= " + code + " Name= " + name + "; Email= " + email + ";";
    }
}
