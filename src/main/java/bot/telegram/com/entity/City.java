package bot.telegram.com.entity;

import javax.persistence.*;


@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;
    @Column
    private String description;

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getDescription() {
        return description;
    }
}
