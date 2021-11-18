package facades;

import dtos.RenameMeDTO;
import entities.RenameMe;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class RenameMeFacade {

    private static RenameMeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private RenameMeFacade() {
    }

    public static RenameMeFacade getFacadeExample(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RenameMeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public RenameMeDTO create(RenameMeDTO pn) {
        RenameMe renameMe =
                new RenameMe(pn.getDummyStr1(), pn.getDummyStr2());

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(renameMe);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RenameMeDTO(renameMe);
    }

    public RenameMeDTO getById(long id) {
        EntityManager em = emf.createEntityManager();
        return new RenameMeDTO(em.find(RenameMe.class, id));
    }

    public RenameMeDTO update(RenameMeDTO pn) {
        RenameMe renameMe = new RenameMe(pn.getId(), pn.getDummyStr1(), pn.getDummyStr2());
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            renameMe = em.merge(renameMe);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new RenameMeDTO(renameMe);
    }

    public long getRenameMeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long RenameMeCount = (long) em.createQuery("SELECT COUNT(p) FROM RenameMe p").getSingleResult();
            return RenameMeCount;
        } finally {
            em.close();
        }
    }


    public List<RenameMeDTO> getAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<RenameMe> query = em.createQuery("SELECT p FROM RenameMe p", RenameMe.class);
        List<RenameMe> rms = query.getResultList();
        return RenameMeDTO.getDtos(rms);
    }
}
