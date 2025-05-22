import jakarta.persistence.*;
import java.util.List;

public class TodoList {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("todo-pu");

    public void addTask(String description) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = new Task(description);
        em.persist(task);
        em.getTransaction().commit();
        em.close();
    }

    public void markTaskDone(int index) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Task> tasks = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            task.markDone();
            em.merge(task);
        }

        em.getTransaction().commit();
        em.close();
    }

    public void removeTask(int index) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        List<Task> tasks = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            em.remove(em.contains(task) ? task : em.merge(task));
        }

        em.getTransaction().commit();
        em.close();
    }

    public List<Task> getTasks() {
        EntityManager em = emf.createEntityManager();
        List<Task> tasks = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
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
}
