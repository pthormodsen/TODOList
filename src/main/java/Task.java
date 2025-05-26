import jakarta.persistence.*;

@Entity
@Table(schema ="todo", name ="task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private boolean completed;

    public Task() {}

    public Task(String description) {
        this.description = description;
        this.completed = false; // default verdi
    }

    public Task(String description, boolean completed) {
        this.description = description;
        this.completed = completed;
    }

    // Gettere og settere

    public Long getId() {
        return id;
    }

    public void markDone() {
        this.completed = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        String status = completed ? "\u2713" : "\u25A1";  // ✓ eller □
        return String.format("%s  %s", status, description);
    }





}
