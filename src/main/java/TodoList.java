import jakarta.persistence.*;
import java.util.List;

public class TodoList {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("todo-pu");

    public void addTask(String description) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = new Task(description);
        em.persist(task);
        em.getTransaction().commit();
        em.close();
    }

    public void markTaskDoneById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Task task = em.find(Task.class, id);
        if (task != null && !task.isCompleted()) {
            task.markDone();
            em.merge(task);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void removeTaskById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Task task = em.find(Task.class, id);
        if (task != null) {
            em.remove(task);
        }

        em.getTransaction().commit();
        em.close();
    }


    public List<Task> getTasks() {
        EntityManager em = emf.createEntityManager();
        List<Task> tasks = em.createQuery(
                "SELECT t FROM Task t ORDER BY t.completed ASC, LOWER(t.description) ASC", Task.class
        ).getResultList();
        em.close();
        return tasks;
    }

    public int size() {
        return getTasks().size();
    }


    public Task getTask(int index) {
        List<Task> tasks = getTasks();
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }

    public void markTaskUndoneById(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Task task = em.find(Task.class, id);
        if (task != null) {
            task.setCompleted(false);  // Setter completed til false
            em.merge(task);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void updateTaskDescription(Long id, String newDescription) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Task task = em.find(Task.class, id);
        if (task != null) {
            task.setDescription(newDescription);
            em.merge(task);  // Ikke strengt nødvendig her, men greit å gjøre
        }

        em.getTransaction().commit();
        em.close();
    }
}
